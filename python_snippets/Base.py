import pygame
import time
import random
import pygame.mixer

pygame.init()

white = (255,255,255)
black = (0,0,0)
red = (255,0,0)
green=(0,155,0)
def game_intro():
    intro = True
    while intro:

        for event in pygame.event.get():
            if event.type== pygame.QUIT:
                pygame.quit()
                quit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_c:
                    intro = False
                if event.key == pygame.K_q:
                    pygame.quit()
                    quit()


        gameDisplay.fill(white)
        message_to_screen("Welcome to Tanks",
                          green,
                          -100,
                          size = "large")
        message_to_screen("The objective of game is to shoot and destroy!",
                          black,
                          -30,
                          "small")
        message_to_screen("the tank enemy before they destroy you",
                          black,
                          10,
                          "small")
        message_to_screen("The more enemies you destroy th harder they get",
                          black,
                          50,
                          "small")
        message_to_screen("Press C to play or Q to quit or P to pause",
                          black,
                          180,
                          "small")
        pygame.display.update()
        clock.tick(15)

def score (score):
    text = smallfont.render("Score: " + str(score),True, black)
    gameDisplay.blit(text,[0,0])

def pause():
    paused = True
    message_to_screen("Paused",
                          black,
                          -100,
                          size="large")
    message_to_screen("Press C to continue or Q to quit",
                          black,
                          25,
                          size = "small")
    pygame.display.update()

    while paused:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                quit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_c:
                    paused = False
                elif event.key == pygame.K_q:
                    pygame.quit()
                    quit()
        # gameDisplay.fill(white)

        clock.tick(5)


display_width = 800
display_height = 600

gameDisplay = pygame.display.set_mode((display_width,display_height))
pygame.display.set_caption('Tanks')
#icon = pygame.image.load('apple_icon.png')
#pygame.display.set_icon(icon)

clock = pygame.time.Clock()
#img = pygame.image.load("snakeHead.png")
#apple = pygame.image.load("apple.png")

FPS = 30

smallfont = pygame.font.SysFont("comicsansms",25)
medfont = pygame.font.SysFont("comicsansms",50)
largefont = pygame.font.SysFont("comicsansms",75)


def text_objects(msg,color,size):
    if size == "small":
        textSurface = smallfont.render(msg,True,color)
    elif size == "medium":
        textSurface = medfont.render(msg,True,color)
    elif size == "large":
        textSurface = largefont.render(msg,True,color)
    return textSurface,textSurface.get_rect()

def message_to_screen(msg,color,y_displays = 0,size = "small"):
    textSurface,textRect = text_objects(msg,color,size)
    textRect.center = (display_width/2,display_height/2 + y_displays)
    gameDisplay.blit(textSurface,textRect)

def gameLoop():


    gameOver = False
    gameExit = False


    while not gameExit:
        if gameOver:
            message_to_screen("Game over",
                              red,-50,
                              size = "large")
            message_to_screen(" Press C to play again or Q to quit the game or P to pause the game",
                              black,
                              50,
                              size = "small")
            pygame.display.update()

        while gameOver ==  True:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    gameOver = False
                    gameExit = True
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_q:
                        gameExit = True
                        gameOver = False
                    if event.key==pygame.K_c:
                        gameLoop()

        for event in pygame.event.get():
            if event.type==pygame.QUIT:
                gameExit = True
            if event.type == pygame.KEYDOWN:
                if event.key==pygame.K_LEFT:
                    pass
                elif event.key==pygame.K_RIGHT:
                    pass
                elif event.key==pygame.K_UP:
                    pass
                elif event.key==pygame.K_DOWN:
                    pass
                elif event.key==pygame.K_p:
                    pause()

        gameDisplay.fill(white)
        pygame.display.update()
        clock.tick(FPS)

    pygame.quit()
    quit()

game_intro()
gameLoop()
