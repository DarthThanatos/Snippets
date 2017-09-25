from os import walk

mypath = "../All"
f = []
for (dirpath, dirnames, filenames) in walk(mypath):
    f.extend(filenames)

print f