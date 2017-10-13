import sys
import os

if sys.argv.__len__() != 2:
	print "usage: ./add_processor.py output"
	exit()

if not os.path.isfile("strace_columns"):
	print "no strace_columns file in directory"
	exit()

cmd = "awk -F \'[[:space:]]+\' \'{print $6}\' strace_columns > " + sys.argv[1]
os.system(cmd)

concatenator = lambda acc, x: acc + ("ADD({});\n".format(x) if x != "" else "")

def concatOutput(initializer = ""):
	with open(sys.argv[1], "r+") as input: 
		content = input.read()

		return\
			reduce(
				concatenator,
				content.split("\n"), 
				initializer
			)

result = concatOutput()

with open(sys.argv[1], "w+") as output:
	output.write(result)
