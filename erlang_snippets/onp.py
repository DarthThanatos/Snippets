import math

def power(Stack):
	return [math.pow(Stack[1],Stack[0])] + Stack[2 : ]
def mul(Stack):
	return ([Stack[1] * Stack[0]]) + Stack[2 : ]
def add(Stack):
	return ([Stack[1] + Stack[0]]) + Stack[2 : ]
def pierwiastek(Stack):
	return [math.sqrt(Stack[0])] + Stack[1 : ]
def sinus(Stack):
	return [math.sin(Stack[0])] + Stack[1 : ]
def cosinus(Stack):
	return [math.cos(Stack[0])] + Stack[1 : ]
def tangens(Stack):
	return [math.tan(Stack[0])] + Stack[1 : ]
def sub(Stack):
	return ([Stack[1] - Stack[0]]) + Stack[2 : ]
def divide(Stack):
	return ([Stack[1] / Stack[0]]) + Stack[2 : ]

def onp(arg):
	what_to_do = {"*": "mul", "+": "add", "-":"sub","/":"divide",
				  "sinus":"sinus","cosinus":"cosinus","tangens":"tangens",
				  "pierwiastek":"pierwiastek","power":"power"}
	list = arg.split()
	i = 0
	Stack = []
	while i < len(list):
		try:
			Stack = [float(list[i])] + Stack
		except ValueError:
			Stack = eval(what_to_do[list[i]])(Stack)
		i += 1
	return Stack[0]

def main():
	arg = "0.5 pierwiastek 2 pierwiastek 4 / 3 2 / + / 3.14 4 / *   0.5 pierwiastek 2 pierwiastek -4 / 3 2 / + / 3.14 4 / * + " 
	#arg = "1 3 + 4 * 10 /"
	#arg = "2 pierwiastek 3 2 power * 1 + 4 5 / - 6 +"
	#arg = "1 2 + sinus 2 power"
	print onp(arg)

main()