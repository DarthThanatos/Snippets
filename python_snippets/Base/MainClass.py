import pygame
import time
import random
import pygame.mixer
import CustomText
import CustomMainLoop


class Main:
    def __init__(self):
        pygame.init()

        self.white = (255,255,255)
        self.black = (0,0,0)
        self.red = (255,0,0)
        self.green=(0,155,0)

        self.display_width = 800
        self.display_height = 600

        self.gameDisplay = pygame.display.set_mode((self.display_width,self.display_height))
        pygame.display.set_caption('Tanks')
        self.clock = pygame.time.Clock()

        self.cText = CustomText.Text(self)
        main_loop = CustomMainLoop.MainLoop(self)
        main_loop.display()
        #icon = pygame.image.load('apple_icon.png')
        #pygame.display.set_icon(icon)
        #img = pygame.image.load("snakeHead.png")
        #apple = pygame.image.load("apple.png")

        pygame.quit()
        quit()

Start = Main()
