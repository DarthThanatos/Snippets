import wx

class bucky(wx.Frame):
	def __init__(self,parent,id):
		wx.Frame.__init__(self,parent,id,"Frame aka Window",pos=(0,0), size=(300,200))
		panel = wx.Panel(self)
		
		box=wx.TextEntryDialog(panel,"Whats ur name?","Title",'default text')
		if box.ShowModal()==wx.ID_OK:
			answer=box.GetValue()
		
if __name__ == '__main__':
			app=wx.PySimpleApp()
			frame=bucky(parent=None,id=-1)
			frame.Show()
			app.MainLoop()