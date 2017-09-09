def bus(city_stations):
	m = city_stations.__len__() - 1
	n = city_stations[m].__len__() - 1
	for i in range (1,city_stations.__len__()):
		for j in  range(1,city_stations[i].__len__()):
			city_stations[i][j] = city_stations[i][j] + max(city_stations[i-1][j],city_stations[i][j-1])
	return city_stations[m][n]

def print_tab(tab):
	m = len(tab)
	for i in range(m):
		n = len(tab[i])
		for j in range(n):
			print tab[i][j],
		print ""
	
def main():
	city_stations = []
	n = 8
	m = 7
	for i in range(m+1):
		city_stations.append([])
		for j in range (n+1):
			city_stations[i].append(0)
	stations = {(3,4):4,(2,6):4,(3,2):2,(6,5):1,(5,2):2,(5,1):5,(1,2):1,(1,3):1,(7,7):1,(4,7):2,(6,8):2}
	for x,y in stations.keys():
		city_stations[x][y] = stations[(x,y)]
	print_tab(city_stations)	
	print "\n",bus(city_stations)

main()