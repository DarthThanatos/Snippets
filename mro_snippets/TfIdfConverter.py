import os
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer, TfidfVectorizer
from numpy import save, load

class TfIdf(object):
    def __init__(self, working_dir):
        self.working_dir = working_dir
        self.features_names = []

    def getTfIdf(self, toarray = True):
        corpus = [self.working_dir + "//" + f for f in os.listdir(self.working_dir)]
        transformer = TfidfVectorizer(input=corpus, max_features=100, smooth_idf=False)
        res = transformer.fit_transform(corpus)
        self.features_names = transformer.get_feature_names()
        return res.toarray() if toarray else res

    def geTfIdfSavingToFile(self, filename, toarray = True):
        tfidf = self.getTfIdf(False)
        with open(filename + "_feature_names", "w") as fn:
            to_save = ", ". join(self.features_names).encode('utf-8')
            print "to_save", to_save
            fn.writelines(to_save)
        save(filename, tfidf.toarray())
        return tfidf.toarray() if toarray else tfidf

    def getTfIdfFromFile(self, filename):
        with open(filename + "_feature_names", "r") as fn:
            self.features_names = fn.read().split(",")
        return load(filename.replace(".npy", "") + ".npy")

if __name__ == "__main__":
    tfidf= TfIdf("wikipedia_articles_subset")
    tfidf_obj= tfidf.geTfIdfSavingToFile("subset_tfidf", False)
    print tfidf_obj.toarray()
    print 140 * "="
    print tfidf.features_names.__len__()
    print 140 * "="
    print tfidf.getTfIdfFromFile("subset_tfidf")
    print tfidf.features_names.__len__(), tfidf.features_names

