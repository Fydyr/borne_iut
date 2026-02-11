--- Classe representant un quad (sous-region d'une texture).
-- Herite de Rect et permet d'afficher une portion specifique d'une image.
-- Utile pour les tilesets et les atlas de textures.
-- @classmod Quad

-- LIBS
local Vector2 = require("src/classes/Vector2")
local Rect = require("src/classes/Rect")

-- CLASS
local class = Rect:extend()

--- Cree un nouveau quad a partir d'une image et de dimensions.
-- @param ImagePath chemin vers le fichier image
-- @param Size taille de la portion visible (Vector2)
-- @param TextureSize taille totale de la texture (Vector2, defaut = Size)
function class:new(ImagePath, Size, TextureSize)
    self.super.new(self)
    self.Size = Size or Vector2(0, 0)

    self.Texture = love.graphics.newImage(ImagePath)
    self.Texture:setFilter("nearest")

    self.TextureSize = TextureSize or self.Size
    self:updateQuad()
end

--- Met a jour le quad LOVE2D interne selon les dimensions actuelles.
function class:updateQuad()
    self.Quad = love.graphics.newQuad(0, 0, self.Size.X, self.Size.Y, self.TextureSize.X, self.TextureSize.Y)
end

--- Modifie la taille de la texture et met a jour le quad.
-- @param nSize nouvelle taille de texture (Vector2)
function class:setTextureSize(nSize)
    self.TextureSize = nSize
    self:updateQuad()
end

--- Modifie la taille du quad et met a jour le quad interne.
-- @param nSize nouvelle taille (Vector2)
function class:setSize(nSize)
    self.Size = nSize
    self:updateQuad()
end

--- Dessine le quad a l'ecran avec position, rotation et couleur.
function class:draw()
    local PosX, PosY, ScaleX, ScaleY = self:getDrawingCoordinates()

    self.Color:apply(1-self.Opacity)

    love.graphics.translate(PosX - ScaleX, PosY - ScaleY)
    love.graphics.rotate(self.Rotation)
    love.graphics.translate(-ScaleX, -ScaleY)
    love.graphics.draw(self.Texture, self.Quad, 0, 0)
    love.graphics.origin()
end

return class