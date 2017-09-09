

from random import randint

def selectionSort(List,n):
	print List
	for i in range(0,n):
		min = i
		for j in range(i+1,n):
			if List[j]>List[min] :
				min = j
		tuple = swap(List[i],List[min])
		List[i] = tuple[0]
		List[min] = tuple[1]
	print List
		
		
def swap(var1,var2):
	return var2,var1

	
def insertSort(List,n):
	print List
	for i in range(1,n):
		j = i
		val = List[i]
		while j > 0 and List[j]<List[j-1]:
			List[j] = List[j-1]
			List[j-1]= val
			j-=1
	print List	
		
def fillList(List,n):
	List = list()
	for i in range(0,n):
		List.append(randint(0,9))
	return List
	
def main():

	N = 11
	
	List = list()
	List = fillList (List,N)
	selectionSort(List,N)
	List = fillList(List,N)
	insertSort(List,N)
	
main()