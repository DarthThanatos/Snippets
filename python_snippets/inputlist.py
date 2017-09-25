import wx

class bucky(wx.Frame):
	def __init__(self,parent,id):
		wx.Frame.__init__(self,parent,id,"Title",pos=(0,0),size=(300,200))
		panel = wx.Panel(self)
		self.Bind(wx.EVT_CLOSE, self.closewindow)
		box=wx.SingleChoiceDialog(None,"Whats this?","Title",["Tuna","Beef","Bacon","Apples","Peaches"])
		if box.ShowModal()==wx.ID_OK:
			answer=box.GetStringSelection()
		
		
	def closewindow(self,event):
		self.Destroy()
		#self.Close(True)
	
if __name__=="__main__":
	app = wx.PySimpleApp()
	frame=bucky(None,-1)
	frame.Show()
	app.MainLoop()