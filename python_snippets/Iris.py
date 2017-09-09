# Load CSV (using python)
import csv
import numpy
filename = 'iris.data'
raw_data = open(filename, 'rb')
reader = csv.reader(raw_data, delimiter=',', quoting=csv.QUOTE_NONE)
x = list(reader)
data = numpy.array(x)#.astype('float')
change_dict = {'Iris-virginica':[1,0,0], 'Iris-versicolor':[0,1,0], 'Iris-setosa':[0,0,1]}
input = []
output = []
for i in range(data.__len__()):
    if data[i].__len__()!=0:
        data[i][4] = change_dict[data[i][4]]
        input.append([data[i][0],data[i][1],data[i][2],data[i][3]])
        output.append([data[i][4]])
print input, output