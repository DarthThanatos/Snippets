
class BFSVertex:
	def __init__(self,vertex_id):
		self.color = 0
		self.parent = None
		self.vertex_id = vertex_id

def BFS(self,verticesDict):
	Q = []
	Q.append("0")
	v = BFSVertex("0")
	v.color = 1
	BFSDict = {}
	BFSDict["0"] = v
	while not Q.__len__() == 0:
		u= Q[0]
		vertex = verticesDict[u]
		for neighbour in vertex.neighboursList:
			if not neighbour in BFSDict.keys():
				BFSneighbour = BFSVertex(neighbour)
			else:
				BFSneighbour = BFSDict[neighbour]
			if BFSneighbour.color == 0:
				BFSneighbour.color = 1
				BFSneighbour.parent = u
			Q.append(BFSneighbour.vertex_id)
			BFSDict[neighbour] = BFSneighbour
		Q = Q[1:]
	#building a tree
	for key in verticesDict.keys():
		BFSvertex = BFSDict[key]
		if(BFSvertex.parent!=None):
			print BFSvertex.vertex_id,BFSvertex.parent
			self.B[int(BFSvertex.vertex_id)].append(BFSvertex.parent)
			self.B[int(BFSvertex.parent)].append(BFSvertex.vertex_id)
	#self.A = self.B
	print 'BFS:'
	for i in range (self.Alen +1):
		print "Vertex" + str(i) + "-",
		for p in self.B[i]:
			print p,
		print ""