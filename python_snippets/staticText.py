import wx

class bucky(wx.Frame):
	def __init__(self,parent, id):
		wx.Frame.__init__(self,parent,id,size=(300,300),pos=(0,0))
		panel=wx.Panel(self)
		wx.StaticText(panel,-1,"This is static text",(10,10))
		custom=wx.StaticText(panel,-1,"This is custom",(10,30),(-1,-1),wx.ALIGN_CENTER)
		custom.SetForegroundColour("white")
		custom.SetBackgroundColour("blue")
		
if __name__=="__main__":
	app=wx.App()
	frame=bucky(None,-1)
	frame.Show()
	app.MainLoop()