--- Classe representant une image affichable.
-- Herite de Rect et ajoute le chargement et le dessin d'une texture.
-- Le filtre de texture est configure en "nearest" pour un rendu pixel-art net.
-- @classmod Image

-- LIBS
local Vector2 = require("src/classes/Vector2")
local Rect = require("src/classes/Rect")

-- CLASS
local class = Rect:extend()

--- Cree une nouvelle image a partir d'un chemin de fichier.
-- @param ImagePath chemin vers le fichier image
function class:new(ImagePath)
    self.super.new(self)

    self.Texture = love.graphics.newImage(ImagePath)
    self.Texture:setFilter("nearest")
end

--- Dessine l'image a l'ecran avec position, rotation et echelle.
function class:draw()
    local PosX, PosY, ScaleX, ScaleY = self:getDrawingCoordinates()
    local TextureWidth, TextureHeight = self.Texture:getDimensions()

    self.Color:apply(1-self.Opacity)

    love.graphics.translate(PosX - ScaleX, PosY - ScaleY)
    love.graphics.rotate(self.Rotation)
    love.graphics.translate(-ScaleX, -ScaleY)
    love.graphics.draw(self.Texture, 0, 0, 0, self.Size.X/TextureWidth, self.Size.Y/TextureHeight)
    love.graphics.origin()
end

return class