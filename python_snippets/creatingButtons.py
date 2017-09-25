import wx

class bucky(wx.Frame):
	def __init__(self, parent,id):
		wx.Frame.__init__(self,parent,id,"Title",pos=(0,0),size=(300,300))
		
		panel = wx.Panel(self)
		button=wx.Button(panel,label="exit",pos=(130,10),size=(60,60))
		self.Bind(wx.EVT_BUTTON,self.closebutton,button)
		self.Bind(wx.EVT_CLOSE,self.closewindow)
		
	def closebutton(self,event):
		self.Close(True)
		
	def closewindow(self,event):
		self.Destroy()
	
		
if __name__=="__main__":
	app=wx.App()
	frame=bucky(None,-1)
	frame.Show()
	app.MainLoop()