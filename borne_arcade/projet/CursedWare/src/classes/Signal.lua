--- @file Signal.lua
--- Batched Yield-Safe Signal Implementation.
--- Cette classe Signal a un comportement identique à un RBXScriptSignal normal,
--- à la différence de quelques frames supplémentaires en bas de la pile lors d'une erreur.
--- Cette implémentation met en cache les coroutines runner, permettant le yield dans les
--- handlers avec un surcoût minimal.
---
--- API:
---   local Signal = require(THIS MODULE)
---   local sig = Signal.new()
---   local connection = sig:Connect(function(arg1, arg2, ...) ... end)
---   sig:Fire(arg1, arg2, ...)
---   connection:Disconnect()
---   sig:DisconnectAll()
---   local arg1, arg2, ... = sig:Wait()
---
--- @license MIT
--- @author stravant - July 31st, 2021

--- Le thread inactif courant sur lequel exécuter le prochain handler.
local freeRunnerThread = nil

--- Acquiert le thread runner inactif courant, exécute la fonction fn dessus,
--- puis libère le thread pour qu'il redevienne le thread inactif courant.
--- Si un thread runner inactif existait déjà, il sera abandonné et collecté par le GC.
--- @param fn function La fonction à exécuter
local function acquireRunnerThreadAndCallEventHandler(fn, ...)
	local acquiredRunnerThread = freeRunnerThread
	freeRunnerThread = nil
	fn(...)
	-- The handler finished running, this runner thread is free again.
	freeRunnerThread = acquiredRunnerThread
end

--- Runner de coroutine dont on crée des coroutines. La coroutine peut être
--- reprise plusieurs fois avec les fonctions à exécuter suivies de leurs arguments.
local function runEventHandlerInFreeThread(...)
	acquireRunnerThreadAndCallEventHandler(...)
	while true do
		acquireRunnerThreadAndCallEventHandler(coroutine.yield())
	end
end

-- Connection class
local Connection = {}
Connection.__index = Connection

function Connection.new(signal, fn)
	return setmetatable({
		_connected = true,
		_signal = signal,
		_fn = fn,
		_next = false,
	}, Connection)
end

function Connection:Disconnect()
	assert(self._connected, "Can't disconnect a connection twice.", 2)
	self._connected = false

	-- Unhook the node, but DON'T clear it. That way any fire calls that are
	-- currently sitting on this node will be able to iterate forwards off of
	-- it, but any subsequent fire calls will not hit it, and it will be GCed
	-- when no more fire calls are sitting on it.
	if self._signal._handlerListHead == self then
		self._signal._handlerListHead = self._next
	else
		local prev = self._signal._handlerListHead
		while prev and prev._next ~= self do
			prev = prev._next
		end
		if prev then
			prev._next = self._next
		end
	end
end

-- Make Connection strict
setmetatable(Connection, {
	__index = function(tb, key)
		error(("Attempt to get Connection::%s (not a valid member)"):format(tostring(key)), 2)
	end,
	__newindex = function(tb, key, value)
		error(("Attempt to set Connection::%s (not a valid member)"):format(tostring(key)), 2)
	end
})

-- Signal class
local Signal = {}
Signal.__index = Signal

function Signal.new()
	return setmetatable({
		_handlerListHead = false,	
	}, Signal)
end

function Signal:Connect(fn)
	local connection = Connection.new(self, fn)
	if self._handlerListHead then
		connection._next = self._handlerListHead
		self._handlerListHead = connection
	else
		self._handlerListHead = connection
	end
	return connection
end

--- Déconnecte tous les handlers. Comme une liste chaînée est utilisée,
--- il suffit de supprimer la référence au handler de tête.
function Signal:DisconnectAll()
	self._handlerListHead = false
end

--- Déclenche le signal en exécutant les fonctions handler sur le coRunnerThread.
--- Si le thread résultant yield sans revenir, cela signifie qu'il a cédé la main
--- au scheduler et qu'il faut créer un nouveau runner de coroutine.
--- @param ... any Les arguments à passer aux handlers
function Signal:Fire(...)
	local item = self._handlerListHead
	while item do
		if item._connected then
			if not freeRunnerThread then
				freeRunnerThread = coroutine.create(runEventHandlerInFreeThread)
			end
			task.spawn(freeRunnerThread, item._fn, ...)
		end
		item = item._next
	end
end

-- Implement Signal:Wait() in terms of a temporary connection using
-- a Signal:Connect() which disconnects itself.
function Signal:Wait()
	local waitingCoroutine = coroutine.running()
	local cn;
	cn = self:Connect(function(...)
		cn:Disconnect()
		task.spawn(waitingCoroutine, ...)
	end)
	return coroutine.yield()
end

-- Make signal strict
setmetatable(Signal, {
	__index = function(tb, key)
		error(("Attempt to get Signal::%s (not a valid member)"):format(tostring(key)), 2)
	end,
	__newindex = function(tb, key, value)
		error(("Attempt to set Signal::%s (not a valid member)"):format(tostring(key)), 2)
	end
})

return Signal