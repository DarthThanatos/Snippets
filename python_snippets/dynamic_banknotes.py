import sys

def print_tab(tab):
	for i in range(len(tab)):
		print tab[i]
	print ""

def check_sum(W,b,S,i,j):
	sum = 0
	for q in range (1,i):
		sum += W[q][j] * b[q]
	sum += b[i]
	if j - b[i] >= 0:
		sum += W[i][j-b[i]] * b[i]
	S[i][j] = sum
	if sum is j:
		return True
	return False

def banknotes(n,k,b,c):
	R = []
	for i in range(n+1):
		R.append([])
		for j in range(k+1):
			R[i].append(0)
			if (i,j) != (0,0):
				R[i][j] = sys.maxint
	W = []
	S = []
	for i in range (n+1):
		W.append([])
		S.append([])
		for j in range(k+1):
			W[i].append(0)
			S[i].append(0)

	for i in range (1,n+1):
		for j in range(k+1):
			sum,R[i][j] = Min(R,W,b,i,j)
			if j-1 >= 0 :
				W[i][j] = W[i][j-1]
			print W[i][j],W[i][j]*b[i] + b[i],j,c[i]
			if c[i]>0 and (sum + b[i] == j or W[i][j]*b[i] + b[i] is j):
				W[i][j] += 1
				R[i][j] += 1
				c[i] -= 1
				
				
	print_tab(R)
	print_tab(W)
	return Res(W,b,n,k)

def Res(W,b,n,k):
	res = 0
	tab = []
	for i in range(n+1):
		tab.append(0)
	i = n
	j = k
	while j is not 0:
		tab[i] = W[i][j]
		res += W[i][j]
		j = j - W[i][j] * b[i]
		i -= 1
		print j, i
	return res,tab

def Min(R,W,b,i,j):
	if i is 0:
		return 0,R[i][j]
	if j - W[i][j]*b[i] > 0:
		return 0,R[i][j]
	sum,res = Min (R,W,b,i-1,j - W[i][j]*b[i])
	return sum + W[i][j] * b[i], min (res,R[i][j])
	
def main():
	n = 3
	k = 10
	b = [0,2,3,5]
	c = [0,2,2,1]
	res,tab = banknotes(n,k,b,c)
	print res, tab[1:]

main()