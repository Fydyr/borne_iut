-- // LIBS
local Vector2 = require("src/classes/Vector2")
local Color = require("src/classes/Color")
local Square = require("src/classes/Rect")
local Image = require("src/classes/Image")
local Quad = require("src/classes/Quad")
local Spritesheet = require("src/classes/Spritesheet")
local TextLabel = require("src/classes/TextLabel")
local ShakingText = require("src/classes/advanced/ShakingText")

local Renderer = require("src/libs/Rendering/Renderer")
local LogManager = require("src/libs/Debug/LogManager")

local TweenService = require("src/libs/Tween")
local DelayService = require("src/libs/Delay")

-- // MANDATORY LIB DECLARATION
local module = {}
module.__index = module

function module.new()
    self = setmetatable({}, module)

    return self
end

-- // MINIGAME SETTINGS (STATIC PUBLIC)
module.Name = "EMPTY_GAME" -- Name of the game
module.IsActive = false -- Can this game be rolled?
module.MultiplayerDisabled = false -- Can this game be played in versus mode?

-- // PRIVATE VARIABLES (STATIC)


-- // PRIVATE METHODS


-- // MINIGAME METHODS

--- Retourne la chaîne décrivant l'objectif du mini-jeu (à appeler après Setup).
--- @return string L'objectif du mini-jeu
function module:GetObjective()
    return "Sample text\nSample text"
end

--- Retourne le temps accordé au joueur pour terminer ce mini-jeu.
--- @return number Le temps en secondes
function module:GetTime()
    return 5/self.GameSpeed -- Divide by game speed to shrink the time remaining to complete the game at high speed
end

--- Compatibilité 2 joueurs : retourne l'objectif de ce mini-jeu pour le partager à l'autre instance.
--- @return any L'objectif courant
function module:getObjective()
    return self.myObjective
end

function module:setObjective(Obj)
    self.myObjective = Obj
end

-- // MINIGAME RUNNERS

--- Exécuté en premier, avant que le mini-jeu soit visible à l'écran. La musique peut être lancée ici.
function module:Setup()
    local GAME = self.GAME

    
end

--- Exécuté une fois que le joueur a le contrôle du mini-jeu. Tous les binds sont actifs à ce stade.
function module:Start()
    local GAME = self.GAME

    -- Play the music here | WARNING, The second player's minigame won't get the music object, so check if it exist before continuing.
    --[[
    local m = self.PlayMusic(self.Directory .. "/assets/music.mp3")
    if not m then return end

    m:setPitch(self.GameSpeed)
    ]]
end

--- Mise à jour du mini-jeu à chaque frame.
--- @param dt number Le delta-temps depuis la dernière frame
function module:Update(dt)
    local GAME = self.GAME
    dt = dt * self.GameSpeed -- Quick way to speed up the game if you're managing character velocity for example

    -- This is an example, If we have 1s left and he's the player1, we say he succeeded
    if self.PlayerID == 1 and self:GetTimeRemaining() < 1 then
        self:Success()
    end
end

--- Dernière frame du mini-jeu, Update ne s'exécutera plus. Permet d'afficher un résultat final.
function module:Stop()
    local GAME = self.GAME

    --self:Success() -- We can tell if he failed or win (Fail would be: self:Fail())
    -- The fail/success functions doesn't NEED to be called in Stop. Calling it during the game's time will result in it instantly stopping (freeze until time up)

    -- If no win condition is called, Engine assume it's a Fail by default
end

--- Nettoyage du mini-jeu. Permet de libérer les ressources pour éviter les fuites mémoire.
function module:Cleanup()
    local GAME = self.GAME


end

return module