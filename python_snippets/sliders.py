import wx

class bucky(wx.Frame):
	def __init__(self,parent,id):
		wx.Frame.__init__(self,parent,id,"Title",size=(300,300),pos=(0,0))
		panel = wx.Panel(self)
		
		slider = wx.Slider(panel,-1,50,1,100,pos =(10,10), size = (250,-1),style = wx.SL_AUTOTICKS | wx.SL_LABELS) 
		slider.SetTickFreq(1,1)
		
		
if __name__=="__main__":
	app = wx.App()
	frame = bucky(None,-1)
	frame.Show()
	app.MainLoop()