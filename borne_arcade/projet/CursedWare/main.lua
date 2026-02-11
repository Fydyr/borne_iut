--- Point d'entree principal du jeu CursedWare.
-- Ce fichier initialise le moteur LOVE2D, configure la fenetre,
-- charge l'ecran de titre et gere la boucle principale (update/draw).
-- @module main

-- ⚠️⚠️⚠️ - L'éxecution de Love2D à une importance pour la sauvegarde des highscores. Si le cd de la console ne point pas vers le fichier du jeu. Le jeu crashera à la sauvegarde.
-- Conseil: Créer un fichier .bat comme ceci -> cd [CHEMIN VERS LE DOSSIER DU JEU] && "[CHEMIN VERS LOVE2D.exe]" .
-- et executez le fichier pour lancer le jeu.

-- Libs
local Vector2 = require("src/classes/Vector2")
local Color = require("src/classes/Color")
local Renderer = require("src/libs/Rendering/Renderer")
local LogManager = require("src/libs/Debug/LogManager")
local Screen = require("src/libs/Rendering/Screen")

local TweenService = require("src/libs/Tween")
local DelayService = require("src/libs/Delay")

-- Settings
Renderer.ScreenSize = Vector2(1280, 1024)
Renderer.BackgroundColor = Color(.075, .075, .075)
Renderer.CurrentScreen = nil

--- Restreint une valeur entre un minimum et un maximum.
-- Surcharge de la bibliotheque math standard.
-- @param Origin nombre a restreindre
-- @param Min valeur minimale autorisee
-- @param Max valeur maximale autorisee
-- @return nombre restreint entre Min et Max
function math.clamp(Origin, Min, Max)
    return math.min(math.max(Origin, Min), Max)
end

--- Fonction d'initialisation appelee au demarrage de LOVE2D.
-- Configure le generateur aleatoire, la fenetre et charge l'ecran de titre.
function love.load()
    math.randomseed(love.timer.getTime())
    love.window.setMode(Renderer.ScreenSize.X, Renderer.ScreenSize.Y, {resizable=false, vsync=false, borderless=true})

    Renderer.changeScreen(Screen.get("Title")) -- Here you can input a screen's name in [src/screens/...], for example "Title", "Test" or "GAME"
end

--- Fonction de mise a jour appelee a chaque frame.
-- Met a jour le renderer, l'ecran courant, les tweens, les delais et les logs.
-- @param dt nombre de secondes ecoulees depuis la derniere frame
function love.update(dt)
    Renderer.update(dt)
    Renderer.CurrentScreen.update(dt)
    TweenService.StaticUpdate(dt)
    DelayService.StaticUpdate(dt)

    -- LOGS

    LogManager.cleanup()
    LogManager.updateLog(love.timer.getFPS() .. " FPS", Color.Green)
end