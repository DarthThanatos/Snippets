import wx

class bucky(wx.Frame):
		
	
	def __init__(self,parent,id):
		wx.Frame.__init__(self,parent,id,"Title",pos=(0,0),size=(766,300))
		panel = wx.Panel(self)
		
		pic = wx.Image("button.bmp",wx.BITMAP_TYPE_BMP).ConvertToBitmap()
		self.button=wx.BitmapButton(panel,-1,pic,pos=(10,10))
		self.Bind(wx.EVT_BUTTON, self.doMe,self.button)
		
	
	def doMe(self,event):
		self.Destroy()
		
		
	
if __name__=="__main__":
	app=wx.PySimpleApp()
	frame = bucky(None,-1)
	frame.Show()
	app.MainLoop()

	