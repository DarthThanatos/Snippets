import wx

if __name__ == '__main__':
	app = wx.App()
	
	names = ['rob','mac','iw','king']
	modal = wx.SingleChoiceDialog(None,"Whats ur name?","Title",names)
	if modal.ShowModal() == wx.ID_OK:
		print "Your name is %s\n" % modal.GetStringSelection()
	modal.Destroy()