-module(qsort).
-export([lessThan/2,grtEqThan/2,qs/1,randomElems/3,compareSpeeds/3,map/2,filter/2]).

lessThan(List, Arg) -> [X || X <- List, X < Arg].
grtEqThan(List,Arg) -> [X || X <- List, X >= Arg].
qs ([]) -> [];
qs([Pivot|Tail]) -> 
	qs( lessThan(Tail,Pivot) ) ++ [Pivot] ++ qs( grtEqThan(Tail,Pivot) ). 
randomElems(N,Min,Max) -> [random:uniform( erlang:max(1,X rem (Max - Min) ) )+ Min  || X <- lists:seq(1,N)].
compareSpeeds(Fun1,Fun2,List) ->  {element(1,timer:tc(Fun1,[List])), element(1,timer:tc(Fun2,[List]))}.
% Dodaj do modułu addressBook możliwość wyświetlania wpisów w przyjaznej formie.

Foo = fun(X) ->
	Fun = 
		fun(F, X) when X == 0 -> [];
			(F, X)  when X > 0 -> [X rem 10 | F(F, X div 10)]
		end,
	Fun(Fun, X)
end.
lists:foldl(fun(X,Y) -> X + Y end, 0, Foo(191)). %suma cyfr jednej liczby
Div3 = fun(X) when X rem 3 == 0 -> true; (X) when X rem 3 /= 0 -> false end. 
%lists:filter(Div3, [lists:foldl(fun(X,Y)-> X + Y end, 0, Foo(X)) || X <- qsort:randomElems(100,1,100) ]).
[X || X <- qsort:randomElems(100,25,120), lists:foldl(fun(X,Y) -> X + Y end, 0,Foo(X)) rem 3 == 0].
%eliminacja tych liczb spośród miliona randomowo wygenerowanych, których suma cyfr nie jest odzielna przez 3 

Factorial = fun (X)-> 
	Fun = 
		fun(F,X) when (X =< 0) -> 1;
			(F,X) when X > 0 -> X * F(F,X-1)
		end,
	Fun(Fun,X)
end.

Div = fun(X) -> 
	case ( lists:foldl(fun (X,Y) -> X + Y end,0,Foo(X)) rem 3 == 0) of
		true -> true; 
		false -> false 
	end 
end.
lists:filter(Div, qsort:randomElems(100,1,100) ).

map(F,[]) -> [];
map(F,[H|T]) ->
	[F(H)] ++ map(F,T).
filter(F,[])->[];
filter(F,[H|T]) ->
	case F(H) of
		true -> [H]++filter(F,T);
		false -> filter(F,T)
	end.