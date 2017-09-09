import sys

class Queue:
	def __init__(self):
		self.queue = []
	
	def pop_front(self):
		if self.queue !=[]:
			res = self.queue[0]
			self.queue.__delitem__(0)
			return res
	
	def push_front(self,value):
		self.queue = [value] + self.queue
	
	def front(self):
		if self.queue != []:
			return self.queue[0]
	
	def back(self):
		if self.queue != []:
			return self.queue[len(self.queue) - 1]
	
	def pop_back(self):
		if self.queue !=[]:
			l = len(self.queue) - 1
			res = self.queue[l]
			self.queue.__delitem__(l)
			return res
	
	def push_back(self,value):
		self.queue = self.queue + [value]
	
	def not_empty(self):
		if self.queue != []:
			return True
		return False

def print_d_list(L):
	for i in range (len(L)):
		print L[i]

def banknotes(W,n,k,b,c):
	R = []
	M = []
	for i in range (k + 1):
		R.append(sys.maxint)
	R[0] = 0
	for i in range (1, n+1):
		for r in range(b[i]):
			Q = Queue()
			M = []
			l = 0
			while l * b[i] + r <= k:
				M.append(R[l*b[i] + r] - l)
				while Q.not_empty() and (M[Q.back()] >= M[l]):
					M[Q.pop_back()] = None
				Q.push_back(l)
				R[l*b[i] + r] = M[Q.front()] + l
				W [i][l*b[i] + r] = l - Q.front()
				if c[i] == l - Q.front():
					M[Q.pop_front()] = None
				l += 1
	return Res(W,b,n,k)

def Res(W,b,n,k):
	index = n
	sum = k
	while sum != 0:
		sum = k
		res = 0
		tab = []
		for i in range(n+1):
			tab.append(0)
		j = k
		for i in range(index,0,-1):
			tab[i] = W[i][j]
			res += W[i][j]
			sum -= W[i][j]*b[i]
			j = j - W[i][j] * b[i]
		index -= 1
		if index is 0:
			print "No way"
			return -1,None
	return res,tab

def main():
	n = 3
	k = 15
	b = [0,2,3,5]
	c = [0,3,2,2]
	W = []
	for i in range (n+1):
		W.append([])
		for j in range(k+1):
			W[i].append(0)
	res,tab = banknotes(W,n,k,b,c)
	if tab != None:
		print "\n",res, tab[1:]

main()