import wx

class bucky(wx.Frame):
	def __init__(self,parent,id):
		wx.Frame.__init__(self,parent,id,"Frame aka Window",pos=(0,0), size=(300,200))
		panel = wx.Panel(self)
		names = ['rob','mac','iw','king']
		modal = wx.SingleChoiceDialog(self,"Whats ur name?","Title",names,pos = (0,0))
		print modal.pos
		if modal.ShowModal() == wx.ID_OK:
			print "Your name is %s\n" % modal.GetStringSelection()
		modal.Destroy()
		
if __name__ == '__main__':
			app=wx.PySimpleApp()
			frame=bucky(parent=None,id=-1)
			frame.Show()
			app.MainLoop()
			


	
	