-module(myFunctions).
-export([power/2,divisibleBy/2,contains/2,duplicateElements/1, toBinary/1]).

myPower(_A,0,Akum) ->
	Akum;
myPower(A,N,Akum) ->
	A * myPower(A,N-1,Akum).

power (A,N) ->
	myPower(A,N,1).

myDB ([],_D,Res) ->
	Res;
myDB([H|T],D,Res) ->
	if
		(H rem D) == 0  -> myDB(T,D,[H] ++ Res);
		(H rem D /= 0) -> myDB(T,D,Res)
	end.

divisibleBy(Lista,Dzielnik) ->
	myDB(Lista,Dzielnik,[]).

contains([],_A) ->
	false;
contains ([H|T], A) ->
	if 
		H == A -> true;
		H /= A -> contains(T,A)
	end. 

duplicateElements([]) ->
	[];
duplicateElements([H|T]) ->
	[H,H] ++ duplicateElements(T).

toBinary(X) when X =< 0 -> [0];
toBinary(X) -> toBinary(X div 2) ++ [ X rem 2 ] .

