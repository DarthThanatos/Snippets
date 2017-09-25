-module(onp).
-export([onp/1,printl/1]).

mul([H1,H2|T]) ->
	[H1 * H2] ++ T.
add([H1,H2|T]) ->
	[H1 + H2] ++ T.
sub([H1,H2|T]) ->
	[H2 - H1] ++ T.
divide([H1,H2|T]) ->
	[H2 / H1] ++ T.
sinus([H|T]) ->
	[math:sin(H)] ++ T.
cosinus([H|T])->
	[math:cos(H)] ++ T.
tangens([H|T]) ->
	[math:tan(H)] ++ T.
pierwiastek([H|T]) ->
	[math:sqrt(H)] ++ T.
power([H1,H2|T]) ->
	[math:pow(H2,H1)] ++ T.

poInicjacji([],[Stack]) ->
	Stack;
poInicjacji([H|T],Stack) ->
	case H of 
		"*" ->poInicjacji(T, mul(Stack));
		"+" -> poInicjacji(T,add(Stack));
		"-" -> poInicjacji(T,sub(Stack));
		"/" -> poInicjacji(T,divide(Stack));
		"sinus" ->poInicjacji(T,sinus(Stack));
		"cosinus" -> poInicjacji(T,cosinus(Stack));
		"tangens" -> poInicjacji(T,tangens(Stack));
		"pierwiastek" -> poInicjacji(T,pierwiastek(Stack));
		"power" -> poInicjacji(T,power(Stack));
		_int -> poInicjacji(T,[list_to_number(H)] ++ Stack)
	end.

onp(Arg) ->
	poInicjacji(string:tokens(Arg," "),[]).

list_to_number(L) ->
    try list_to_float(L)
    catch
        error:badarg ->
            list_to_integer(L)
    end.

%onp:onp("2 pierwiastek 3 2 power * 1 + 4 5 / - 6 +").
 