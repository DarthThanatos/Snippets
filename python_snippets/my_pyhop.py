import pyhop

#minigierka, ktora sprawdza, czy mozna znalezc wyjscie z sytuacji:
#stoimy na srodku kuchni, mamy okreslone zasoby(cash, food_in_possesion itp.)
#i mamy wykonac taki ruch, zeby nie umrzec z glodu i rozpaczy
#3 metody, 6 operatorow
#ponizej: 
#order zwraca False, bo przy pay_remotely nasz stan cash jest mniejszy niz to co mamy zaplacic
#prepare_food_and_eat zwraca False, bo wczesniej umrzemy z glodu, niz cokolwiek przygotujemy
#"na styk" przechodzi eat_sth_fast, gdy podejmujemy jedzenie, hunger_lvl = 99; przy 100 umieramy
#oczywiscie mozna sie tym dalej bawic: mozna manipulowac suwakami cena pizzy, hunger_lvl i want_to_live
#Zycze wesolych swiat i sylwestra(data wykonania: 18.12.16)

def goTo(state,a,x,y):
	if state.loc[a] == x:
		state.loc[a] = y
		state.hunger_lvl[a] += 2 * state.dist[x][y]
		state.want_to_live -= state.dist[x][y]
		return state
	return False

def order_pizza(state,a):
	state.owe[a] += state.pizza_cost #cost of pizza
	state.hunger_lvl[a] += 5
	return state 

def pay_remotely(state,a):
	if state.cash[a] >= state.owe[a]:
		state.hunger_lvl[a] += 5
		state.cash[a] -= state.owe[a]
		state.food_in_possesion = "pizza"
		return state 
	return False 

def eat_what_u_got(state,a):
	if state.hunger_lvl[a] < 100: #if you still have stength and didnt die already
		state.want_to_live += 20
		state.hunger_lvl[a] -= state.food_value[state.food_in_possesion]
		return state 
	return False

def get_sth_from_fridge(state,a):
	state.food_in_possesion = "from_fridge"
	state.hunger_lvl[a] += 5
	return state 

def prepare(state,a):
	if state.food_in_possesion == "from_fridge":
		state.hunger_lvl[a] += 10
		state.food_in_possesion = "prepared"
		return state 
	return False

pyhop.declare_operators(goTo, order_pizza, pay_remotely, eat_what_u_got ,get_sth_from_fridge,prepare)
print 
pyhop.print_operators()

def order(state, a, x):
	y = "phone"
	#print "Do I want to go from {} to {}?".format(x,y)
	if state.dist[x][y] <= state.want_to_live:
		#print "yep, ",
		return [("goTo",a,x,y), ("order_pizza",a),("pay_remotely",a), ("eat_what_u_got",a)]
	return False  

def prepare_food_and_eat(state, a,x):
	y1 = "fridge"
	y2 = "cooker"
	#print "Do I want to go from {} to {} and then to {}?".format(x,y1,y2)
	if state.dist[x][y1] + state.dist[x][y2]<= state.want_to_live:
		#print "yep, ",
		return [("goTo",a,x,y1), ("get_sth_from_fridge",a),("goTo",a,y1,y2),("prepare",a),("eat_what_u_got",a)]
	return False 

def eat_sth_fast(state, a,x):
	y = "fridge"
	if state.dist[x][y] <= state.want_to_live:
		return[("goTo",a,x,y),("get_sth_from_fridge",a),("eat_what_u_got",a)]
	return False 

pyhop.declare_methods("eat",order,prepare_food_and_eat,eat_sth_fast)
print 
pyhop.print_methods()

state = pyhop.State("my_state")
state.pizza_cost = 11
state.loc = {"I":"middle"}
state.owe = {"I" : 0}
state.cash = {"I":10}
state.hunger_lvl = {"I":86} #100 - I die
state.food_in_possesion = "None"
state.food_value = {"pizza":20,"from_fridge":15, "prepared":30, "None":0}
state.want_to_live = 20 # if want_to_live = 0, I just stand and wait to die - can't do any move
state.dist = { #everything lies on the same line Fridge --4-- Middle --4-- Phone --4-- Cooker
	"fridge":{"middle":4,"phone":8,"cooker":12}, 
	"phone":{"middle":4,"fridge":8,"cooker":4}, 
	"cooker":{"middle":8,"fridge":12,"phone":4},
	"middle":{"phone":4,"fridge":4,"cooker":8}
} #middle - center of the kitchen

pyhop.pyhop(state,[('eat',"I","middle")],verbose=3)