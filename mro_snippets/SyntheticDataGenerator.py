import numpy as np
from matplotlib import pyplot as plt


from mpl_toolkits.mplot3d import Axes3D
from mpl_toolkits.mplot3d import proj3d

def generateUnitVectors(dim):
    if dim < 1: return []
    res = []
    for i in range(dim):
        res.append([0 for j in range(dim)])
        res[i][i] = 1
    return res

def fold(res, col):
    # appends col to res
    for v in col:
        res.append(list(v))

def generateSyntheticData(vectors_in_class, classes_amount, dim, axi_lim_neg = 0, axis_lim_pos = 50):
    if classes_amount < 1 or vectors_in_class < 1: return []
    covariance_matrix = generateUnitVectors(dim)
    result = []
    for i in range(classes_amount):
        coord = np.random.randint(axi_lim_neg, axis_lim_pos)
        class_center = [coord for i in range(dim)]
        partial_res = np.random.multivariate_normal(class_center, covariance_matrix, vectors_in_class)
        fold(result, partial_res)
    return result


def vizualize(vs):
    fig = plt.figure(figsize=(8, 8))
    ax = fig.add_subplot(111, projection='3d')
    plt.rcParams['legend.fontsize'] = 10
    X = [v[0] for v in vs]
    Y = [v[1] for v in vs]
    Z = [v[2] for v in vs]
    ax.plot(X, Y, Z, 'o', markersize=8, color='blue', alpha=0.5)
    plt.show()

uvs = generateSyntheticData(vectors_in_class=5, classes_amount=3, dim=3)

for uv in uvs:
    print uv

vizualize(uvs)
