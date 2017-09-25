import wx
import ctypes

user32 = ctypes.windll.user32

class bucky(wx.Frame):
	def __init__(self,parent,id):
		x = user32.GetSystemMetrics(0)
		y = user32.GetSystemMetrics(1)
		wx.Frame.__init__(self,parent,id,"Title",pos = (x/2,0), size = (x/2,y))
		panel = wx.Panel(self)
		self.ordinacy_list = []
		democracy = wx.CheckBox(panel,-1,"Democracy",(20,20),(160,-1))
		oligarchy = wx.CheckBox(panel,-1,"Oligarchy",(20,40),(160,-1))
		totalitarianism = wx.CheckBox(panel,-1,"Totalitarianism",(20,60),(160,-1))
		display_button = wx.Button(panel,label = "show list",pos = (20,80))
		
		self.Bind(wx.EVT_CHECKBOX,self.democracy_selected,democracy)
		self.Bind(wx.EVT_CHECKBOX,self.oligarchy_selected,oligarchy)
		self.Bind(wx.EVT_CHECKBOX,self.totalitarianism_selected,totalitarianism)
		self.Bind(wx.EVT_BUTTON,self.display_list,display_button)
		
	def democracy_selected(self,event):
		if event.IsChecked():
			self.ordinacy_list.append("Democracy")
		else:	
			self.ordinacy_list.remove("Democracy")
			
	def totalitarianism_selected(self,event):
		if event.IsChecked():
			self.ordinacy_list.append("Totalitarianism")
		else:
			self.ordinacy_list.remove("Totalitarianism")
			
	def oligarchy_selected(self,event):
		if event.IsChecked():
			self.ordinacy_list.append("Oligarchy")
		else:	
			self.ordinacy_list.remove("Oligarchy")
	
	def display_list(self,event):
		print self.ordinacy_list
		self.Close(True)
	
if __name__ == "__main__":
	app = wx.App()
	frame = bucky(None,-1)
	frame.Show()
	app.MainLoop()