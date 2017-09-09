-module (volumeCalc).
-export([volume/2,factorial/1,sumlist_tail/2]).

volume (qube,Edge) ->
	Edge * Edge * Edge;
volume (cuboid, {Edge1,Edge2,Edge3}) ->
	Edge1 * Edge2 * Edge3;
volume(_,_) ->
	io:format("Error in volume/2!"),
	{error,cannot_calculate}.

factorial(0) -> 1;
factorial(N) -> N * factorial (N-1).

sumlist_tail([],Sum) ->
	Sum;
sumlist_tail([H|T],Sum) ->
	sumlist_tail(T,Sum + H).