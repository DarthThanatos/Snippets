# -*- coding: utf-8 -*-
import neurolab as nl

and_input = [[0,0],[0,1],[1,0],[1,1]]
and_target = [[0], [0], [0], [1]]
or_input = and_input
or_bad_input = []
or_target = [[0], [1], [1], [1]]

xor_input = and_input
xor_target = [[0], [1], [1], [0]]
# Create net with 2 inputs and 1 neuron
and_net = nl.net.newp([[0, 1],[0, 1]], 1)
or_net = nl.net.newp([[0, 1],[0, 1]], 1)

xor_net = nl.net.newff([[0,1],[0,1]],[7,1])
# train with delta rule
# see net.trainf
and_error = and_net.train(and_input, and_target, epochs=100, show=10, lr=0.1)
or_error = or_net.train(or_input, or_target, epochs=100, show=10, lr=0.1)

xor_net.trainf = nl.train.train_gdx
xor_error = xor_net.train(xor_input, xor_target, epochs=100, show=10, goal=0.01, lr=0.1)


#for i in range(xor_input.__len__()):
#    xor_error = xor_net.train([xor_input[i]], [xor_target[i]], epochs=50, show=10, goal=0.01)

#print 'and', and_net.sim([[0,1]])
print 'or', or_net.sim(or_input)
print 'or_generalized', or_net.sim([[0.2,0.8]])
print 'and',and_net.sim(and_input)
print 'xor', xor_net.sim(xor_input)
#print and_net.ci, and_net.co,len(and_net.layers)
#help(and_net)

# Plot results
import pylab as pl
pl.plot(or_error)
pl.xlabel('Epoch number')
pl.ylabel('Train error')
pl.grid()
pl.show()