-module(myLists).
-export([sumFloats/1,toBinary/1]).

mySF([],Res)->
	Res;
mySF([H|T],Res) ->
	if 
		is_float(H) -> mySF(T,Res + H);
		true -> mySF(T,Res)
	end.

sumFloats(List)->
	mySF(List,0.0).

toBinary(N) ->
	if 
		N > 0 -> [N rem 2] ++ toBinary(N div 2);
		true  -> []
	end.