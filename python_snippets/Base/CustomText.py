import pygame

class Text:

    def __init__(self,resources):
        self.resources = resources
        self.smallfont = pygame.font.SysFont("comicsansms",25)
        self.medfont = pygame.font.SysFont("comicsansms",50)
        self.largefont = pygame.font.SysFont("comicsansms",75)

    def message_to_screen(self,msg,color,y_displays = 0,size = "small"):
        display_width = self.resources.display_width
        display_height = self.resources.display_height
        gameDisplay = self.resources.gameDisplay
        textSurface,textRect = self.text_objects(msg,color,size)
        textRect.center = (display_width/2,display_height/2 + y_displays)
        gameDisplay.blit(textSurface,textRect)

    def text_objects(self,msg,color,size):
        if size == "small":
            textSurface = self.smallfont.render(msg,True,color)
        elif size == "medium":
            textSurface = self.medfont.render(msg,True,color)
        elif size == "large":
            textSurface = self.largefont.render(msg,True,color)
        return textSurface,textSurface.get_rect()

    def score (self,score):
        black = self.resources.black
        gameDisplay = self.resources.gameDisplay
        text = self.smallfont.render("Score: " + str(score),True, black)
        gameDisplay.blit(text,[0,0])