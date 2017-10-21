from TfIdfConverter import TfIdf
from sklearn.decomposition import PCA, KernelPCA
from matplotlib import pyplot as plt
from mpl_toolkits.mplot3d import proj3d


def vizualize2D(vs):
    fig = plt.figure(figsize=(8, 8))
    ax = fig.add_subplot(111)
    plt.rcParams['legend.fontsize'] = 10
    X = [v[0] for v in vs]
    Y = [v[1] for v in vs]
    ax.plot(X, Y, 'o', markersize=8, color='blue', alpha=0.5)
    plt.show()

def vizualize3D(vs):
    fig = plt.figure(figsize=(8, 8))
    ax = fig.add_subplot(111, projection='3d')
    plt.rcParams['legend.fontsize'] = 10
    X = [v[0] for v in vs]
    Y = [v[1] for v in vs]
    Z = [v[2] for v in vs]
    ax.plot(X, Y, Z, 'o', markersize=8, color='blue', alpha=0.5)
    plt.show()

def reduce2D(tfidf_arr):
    pca2D = PCA(n_components=2)
    tfidf_pca2D = pca2D.fit_transform(tfidf_arr[: tfidf_arr.__len__()/2])
    # print tfidf_pca2D
    print "\neigenvectors 2D\n"
    print pca2D.components_
    print "\neigenvalues 2D\n"
    print pca2D.singular_values_
    vizualize2D(tfidf_pca2D)


def reduce3D(tfidf_arr):
    pca3D = PCA(n_components=3)
    tfidf_pca3D = pca3D.fit_transform(tfidf_arr[: tfidf_arr.__len__()/2])
    # print tfidf_pca3D
    print "\neigenvectors 3D\n"
    print pca3D.components_
    print "\neigenvalues 3D\n"
    print pca3D.singular_values_
    vizualize3D(tfidf_pca3D)

def kernelReduce2D(tfidf_arr):
    kpca2D = KernelPCA(n_components=2)
    tfidf_kpca2D = kpca2D.fit_transform(tfidf_arr[: tfidf_arr.__len__()/2])
    # print tfidf_pca2D
    print "\nkpca eigenvectors 2D\n"
    print kpca2D.alphas_
    print "\nkpca eigenvalues 2D\n"
    print kpca2D.lambdas_
    vizualize2D(tfidf_kpca2D)

def kernelReduce3D(tfidf_arr):
    kpca3D = KernelPCA(n_components=3)
    tfidf_kpca3D = kpca3D.fit_transform(tfidf_arr[: tfidf_arr.__len__()/2])
    # print tfidf_pca2D
    print "\nkpca eigenvectors 3D\n"
    print kpca3D.alphas_
    print "\nkpca eigenvalues 3D\n"
    print kpca3D.lambdas_
    vizualize3D(tfidf_kpca3D)

tfidf= TfIdf("wikipedia_articles")
tfidf_array = tfidf.getTfIdfFromFile("wikipedia_tfidf")

reduce2D(tfidf_array)
print 40 * "="
reduce3D(tfidf_array)

print 40 * "="
print 40 * "="

kernelReduce2D(tfidf_array)
print 40 * "="
kernelReduce3D(tfidf_array)