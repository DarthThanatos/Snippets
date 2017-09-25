import wx

class bucky(wx.Frame):
	def __init__(self,parent,id):
		wx.Frame.__init__(self,parent,id,"Title",pos=(0,0),size=(300,300))
		panel = wx.Panel(self)
		test=wx.TextEntryDialog(None,"Whats ur name?","Title","Enter name",pos = (50,50))
		if test.ShowModal()==wx.ID_OK:
			apples=test.GetValue()
		wx.StaticText(panel,-1,apples,(10,10))
		
if __name__=="__main__":
	app=wx.App()
	frame=bucky(None,-1)
	frame.Show()
	app.MainLoop()