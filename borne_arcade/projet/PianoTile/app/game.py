"""Module principal du jeu PianoTile gerant la boucle de jeu et la navigation entre les pages."""

import pygame, sys, os
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from core.pageState import PageState
from ui.interface import Interface
from core.logic import Logic
from data.database import Database

class Game:
    """Classe principale orchestrant le jeu PianoTile : base de donnees, interface et logique."""

    def __init__(self):
        self.__database: Database = Database()
        self.__interface: Interface = Interface(self)
        self.__logic: Logic = Logic(self)

# ----------------------------------- Getter ----------------------------------- #

    def getDatabase(self):
        """Getter de la base de donnees."""
        return self.__database

    def getInterface(self): 
        """Getter de l'interface."""
        return self.__interface
    
    def getLogic(self):
        """Getter de la logique."""
        return self.__logic

# ----------------------------------- Setter ----------------------------------- #

    def PageProfil(self):
        """Affiche et gere les actions de la page profil."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageProfil()
        self.getLogic().actionPageProfil()

    def PageConnexion(self):
        """Affiche et gere les actions de la page de connexion."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageConnexion()
        self.getLogic().actionPageConnexion()

    def PageInscription(self):
        """Affiche et gere les actions de la page d'inscription."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageInscription()
        self.getLogic().actionPageInscription()

    def PageFiltrer(self):
        """Affiche et gere les actions de la page de filtrage des musiques."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageFiltrer()
        self.getLogic().actionPageFiltrer()
    
    def PageAide(self):
        """Affiche et gere les actions de la page d'aide."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageAide()
        self.getLogic().actionPageAide()
    
    def PageDetail(self):
        """Affiche et gere les actions de la page de detail d'une musique."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageDetail()
        self.getLogic().actionPageDetail()
    
    def PagePlay(self):
        """Affiche et gere les actions de la page de jeu."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePagePlay()
        self.getLogic().actionPagePlay()
    
    def PageAccueil(self):
        """Affiche et gere les actions de la page d'accueil."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageAccueil()
        self.getLogic().actionPageAccueil()

    def PageMultijoueur(self):
        """Affiche et gere les actions de la page multijoueur."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageMultijoueur()
        self.getLogic().actionPageMultijoueur()

    def PageStatistique(self):
        """Affiche et gere les actions de la page des statistiques."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageStatistique()
        self.getLogic().actionPageStatistique()

    def PageQuitter(self):
        """Affiche et gere les actions de la page de confirmation de sortie."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageQuitter()
        self.getLogic().actionPageQuitter()

    def PageFinGagne(self):
        """Affiche et gere les actions de la page de victoire."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageFinGagne()
        self.getLogic().actionPageFinGagne()

    def PageFinPerdu(self):
        """Affiche et gere les actions de la page de defaite."""
        if (self.getInterface().getUpdate()):
            self.getInterface().affichagePageFinPerdu()
        self.getLogic().actionPageFinPerdu()

if __name__ == "__main__":
    pygame.init()
    
    Game = Game()

    while Game.getInterface().getPage() not in [PageState.FERMER]:
        if Game.getInterface().getPage() is PageState.PROFIL:
            Game.PageProfil()

        elif Game.getInterface().getPage() is PageState.CONNEXION:
            Game.PageConnexion()

        elif Game.getInterface().getPage() is PageState.INSCRIPTION:
            Game.PageInscription()

        elif Game.getInterface().getPage() is PageState.FILTRER:
            Game.PageFiltrer()

        elif Game.getInterface().getPage() is PageState.AIDE:
            Game.PageAide()

        elif Game.getInterface().getPage() is PageState.DETAIL:
            Game.PageDetail()

        elif Game.getInterface().getPage() is PageState.PLAY:
            Game.PagePlay()

        elif Game.getInterface().getPage() is PageState.ACCUEIL:
            Game.PageAccueil()

        elif Game.getInterface().getPage() is PageState.MULTIJOUEUR:
            Game.PageMultijoueur()
        
        elif Game.getInterface().getPage() is PageState.STATISTIQUE:
            Game.PageStatistique()

        elif Game.getInterface().getPage() is PageState.QUITTER:
            Game.PageQuitter()
        
        elif Game.getInterface().getPage() is PageState.FINGAGNE:
            Game.PageFinGagne()

        elif Game.getInterface().getPage() is PageState.FINPERDU:
            Game.PageFinPerdu() 

        # Mettre a jour l'affichage
        pygame.display.update()

