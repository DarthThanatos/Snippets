import pygame

class MainLoop:
    def __init__(self,resources):
        self.resources = resources

    def display(self):

        gameExit = False
        gameDisplay = self.resources.gameDisplay
        white = self.resources.white
        black = self.resources.black
        FPS =  30
        clock = self.resources.clock
        cText = self.resources.cText

        while not gameExit:

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

            gameDisplay.fill(white)
            cText.message_to_screen("Hello World!",black,0,"large")
            pygame.display.update()
            clock.tick(FPS)
