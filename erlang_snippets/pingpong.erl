-module(pingpong).
-export([start/0,stop/0,play/1]).

start() ->
	register(ping,spawn(fun() -> ping() end)),
	register(pong,spawn(fun() -> pong() end) ).


ping()->
	receive  
		M when M > 0-> 
			pong ! (M-1),
			io:format("ping received ~p~n",[M]);
		stop -> ok
	after 20000 ->
		ok
	end,
	ping().

pong()->
	receive 
		M when M > 0 -> 
			ping ! (M-1),
			io:format("pong received ~p~n",[M]);
		stop -> ok
	after 20000 ->
		ok
	end,
	pong().

play(N) ->
	ping ! N.

stop() ->
	ping ! stop,
	pong ! stop.