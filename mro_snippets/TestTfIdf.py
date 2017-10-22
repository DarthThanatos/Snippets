from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer, TfidfVectorizer
from sklearn.neighbors import DistanceMetric
from numpy import save, load

vectorizer = CountVectorizer()


corpus = [
    'This is the first document.',
    'This is the second second document.',
    'And the third one.',
    'Is this the first document?',
]

X = vectorizer.fit_transform(corpus)

print vectorizer.get_feature_names()
print X.toarray()
print "first index:",vectorizer.vocabulary_.get("first")

transformer = TfidfTransformer(smooth_idf=False)
tfidf = transformer.fit_transform(X.toarray())

print tfidf.toarray()
print "idf vector:", transformer.idf_

transformer2 = TfidfVectorizer(smooth_idf=False)
tfidf2 = transformer2.fit_transform(corpus)
print "\n", tfidf2.toarray()

mink_metric = DistanceMetric.get_metric("minkowski")
eucl_metric = DistanceMetric.get_metric("euclidean")

X = [[0, 1, 2], [3, 4, 5]]
eucl_pairs = eucl_metric.pairwise(X)

filename = "eucl_pairs"
print "before\n" + str(eucl_pairs)
save(filename, eucl_pairs)
print "after"
loaded_eucl_pairs = load(filename + ".npy")
print loaded_eucl_pairs