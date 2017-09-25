import pygame

pygame.init()
pygame.display.set_caption('Tanks')
gameDisplay = pygame.display.set_mode((800,600))
gameDisplay.fill((255,255,255))
pygame.display.update()
index = [27,97,99,100]
keyMap ={index[0]:"27", index[1]:"97", index[2]:"99",index[3]:"100"}

class keyboard_2: #pressed key
	def Key_27(self):
		pygame.quit()
		quit()
	def Key_97(self):
		print "pressed a"
	def Key_100(self):
		print "pressed d"
	def Key_99(self):
		end = index.__len__()
		print end
		for i in range(0,end):
			print "index: ",index[i],"value: ",keyMap[index[i]] 
		j = input("Type index which is to be changed: ")
		try:
			copy = input ("Now, which key u want to type? :")
			#Needed another function checking if index was already chose!
			value = keyMap[index[j]]
			keyMap.__delitem__(index[j])
			index[j] = copy
			keyMap.update({index[j]:value})
		except IndexError:
			pass
			
class keyboard_3: #released key		
	def Key_97(self):
		print "released a"
	def Key_100(self):
		print "released d"
	
while(True):
	for event in pygame.event.get():
		print event
		try:
			keyboard = eval("keyboard_" + str(event.type))
		except NameError:
			continue
			
		try:
			print str(event.key),pygame.key.name(event.key), pygame.QUIT
			#,unichr(event.key).encode('utf-8')
			key = keyMap[event.key]
			getattr(keyboard(),"Key_" + str(key))()
		except AttributeError:
			pass
		except KeyError:
			pass