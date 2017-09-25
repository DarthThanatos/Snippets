import wx
import ctypes

user32 = ctypes.windll.user32

class Window(wx.Frame):
	def __init__ (self,parent,id):
		x = user32.GetSystemMetrics(0)
		y = user32.GetSystemMetrics(1)
		wx.Frame.__init__(self,parent,id,"Title",pos = (x/2,0), size = (x/2,y))
		panel = wx.Panel(self)
		
		myList = ['beef','tuna','apples','peach','cereal','beef']
		container = wx.ListBox(panel,-1,(20,20),(80,60),myList)
		container.SetSelection(3)
		
		
if __name__ == "__main__":
	app = wx.App()
	frame = Window(None,-1)
	frame.Show()
	app.MainLoop()