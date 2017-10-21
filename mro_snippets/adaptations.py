from sklearn.datasets import fetch_mldata
import cPickle
import numpy as np

def subsampleDim(instance, targetDim = 100):
    if instance.__len__() < targetDim or targetDim == 0: return instance
    samplingStep = instance.__len__() / targetDim
    index = 0; samples = 0
    res = [0 for i in range(targetDim)]
    while index < instance.__len__() and samples < targetDim:
        res[samples] = instance[index]
        index += samplingStep
        samples += 1
    return res

def processVectors(X):
    res = []
    for v in X:
        res.append(subsampleDim(v))
    return res

def adapt_mnist():
    mnist = fetch_mldata('MNIST original')
    print mnist['data'][0]

    dim_reduced_mnist = processVectors(mnist['data'][:10])
    print "adapted:\n"
    for v in dim_reduced_mnist:
        print v.__len__(), v

def unpickle(file):
    import cPickle
    with open(file, 'rb') as fo:
        dict = cPickle.load(fo)
    return dict

def adapt_cifar():
    cifar = unpickle("cifar/cifar-10-batches-py/data_batch_1")
    print cifar['data'][0]
    dim_reduced_mnist = processVectors(cifar['data'][:10])
    print "adapted:\n"
    for v in dim_reduced_mnist:
        print v.__len__(), v

adapt_mnist()
adapt_cifar()