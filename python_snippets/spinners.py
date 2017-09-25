import wx
import ctypes
user32 = ctypes.windll.user32

class bucky(wx.Frame):
	def __init__(self, parent,id):
		wx.Frame.__init__(self,parent,id,"Title",size=(user32.GetSystemMetrics(0)/2,user32.GetSystemMetrics(1)),pos =(user32.GetSystemMetrics(0)/2,0) )
		panel = wx.Panel(self)
		
		spinner = wx.SpinCtrl(panel,-1,"", (40,40), (90,-1),style=wx.SP_WRAP)
		spinner.SetRange(1,100)
		spinner.SetValue(10)
		
if __name__ == "__main__":
	app = wx.App()
	frame = bucky(None,-1)
	frame.Show()
	app.MainLoop()