--- Configuration de LOVE2D.
-- Ce fichier est lu avant le demarrage du jeu pour configurer
-- les modules actifs et les options de la fenetre.
-- Desactive les modules inutilises (joystick, souris, video, tactile, physique, threads).
-- @module conf

--- Configure les parametres de LOVE2D.
-- @param t table de configuration LOVE2D
function love.conf(t)
	t.console = true

	t.modules.joystick = false
	t.modules.mouse = false
	t.modules.video = false
	t.modules.touch = false
	t.modules.physic = false
	t.modules.thread = false
end