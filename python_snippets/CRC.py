def CRC_algorithm (input_1,CRC,rest):

	input_1 += rest
	length = input_1.__len__()
	input = []
	for i in range(length):
		input.append(input_1[i])
		
	i = 0 
	while True:
		while (i<=length-1 and input[i] == "0"):
			i+=1
		if i>length-4: break		
		for j in range(4):
			if CRC[j] == input[i+j] :
				input[i+j] = '0'
			else:
				input[i+j] = '1'
		i = 0
	
	input_1 = input[length-3:length]
	res = ""
	for i in range (input_1.__len__()):
		res +=input_1[i]
	return res

st = "hello world"
binary = ' '.join(format(ord(x), 'b') for x in st).replace(" ","")
res = CRC_algorithm(binary,"1011","000")
print res

st = "helloa world"
binary = ' '.join(format(ord(x), 'b') for x in st).replace(" ","")
res = CRC_algorithm(binary,"1011",res)
print res