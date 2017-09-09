def makea(num):
	a = ["0","0","0","0"]
	n = len(a)
	for i in range (n-1,-1,-1):
		a[i] = str(num % 10)
		num/=10
	res = ""
	for i in a:
		res += i
	return res

def printB(B):
	m = 3
	for i in range(m):
		for j in range(10):
			if B[i][j] is not -1:
				print"", # for aesthetics reasons
			print B[i][j],
		print ""
	print ""
	
def createB(b):
	m = len(b)
	B = []
	for i in range(m):
		B.append([])
		for j in range(10):
			B[i].append(-2)
	for x in range(m-1,-1,-1):
		for y in range (10):
			if y is int(b[x]) and x <= m - 1:
				B[x][y] = x
			if y is not int(b[x]) and x is m - 1:
				B[x][y] = -1
			if y is not int(b[x]) and x <= m - 2:
				B[x][y] = B[x + 1][y]
	return B
	
def wB(B,a):
	pos = 0
	for k in range(4):
		if pos is -1: break
		pos = B[pos][int(a[k])]
	return pos is not -1

def main():				

	B1 = createB("123")
	B2 = createB("234")
	print "\n123"
	printB(B1)
	print "234"
	printB(B2)
	
	counter = 0
	res = []
	for i in range (10000):
		a = makea(i)
		isB1 = wB(B1,a)
		isB2 = wB(B2,a)
		if isB1 or isB2:
			print a
		if isB1 and isB2:
			res.append(a)
			counter += 1
	print ""
	for i in res:
		print i
	
	print "\n",counter

main()
	