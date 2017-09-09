import matplotlib.pyplot as plt

standardLineY = [0.206,0.338,0.507,0.643,0.899]
standardLineX = [0.1,0.2,0.3,0.4,0.5]

Y = [0.225,0.363, 0.426,0.533,0.593,0.535]
x = lambda y: (y-0.0113)/1.691
X = [x(y) for y in Y]

for x,y in zip(X,Y): print "({0:.3f}, {1:.3f})".format(x,y)
	
plt.scatter(X,Y,c="r")
plt.scatter(standardLineX,standardLineY,c="b")

#lineX = [x * (0.5/100) for x in range(100) ]
lineX=[0,0.5] #ends of the range
y = lambda x: 1.691 * x + 0.0113
plt.plot(lineX,[y(x) for x in  lineX], c = "y")
plt.show()