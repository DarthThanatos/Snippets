a = ['t', 'h', 'q', 'd', 'a', 'k', 'p', 'g']
for i in range(ord('a'),ord('z')):
	#if chr(i) in a: 
	print chr(i), i - ord('a')
	
print ord('f') - ord('c') #3
print ord('i') - ord('e') #4
print ord('e') - ord('b') #3
print ord('g') - ord('g') #3
print ord('h') - ord('b') #3
print ord('d') - ord('a') #3
print ord('i') - ord('c') #6


print ord('k') - ord('d') #3
print ord('f') - ord('d') #3
print ord('r') - ord('d') #3
print ord('d') - ord('a') #3