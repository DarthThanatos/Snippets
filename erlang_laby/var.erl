-module(var).
-export([start/0, stop/0, inc/0, dec/0, get/0]).
-export([init/0]).

start() ->
	register (varServer, spawn(var, init, [])).
init() ->
	loop(0).

loop(Value) ->
	receive 
		{request, Pid, inc} ->
			Pid ! {reply, ok},
			loop(Value + 1);
		{request, Pid, get} ->
			Pid ! {reply, Value},
			loop(Value);
		{request, Pid, dec} ->
			Pid ! {reply, ok},
			loop(Value - 1);
		{request, Pid, stop} ->
			Pid ! {reply, ok}
	end.
	
 
 %% client
call(Message) ->
	 varServer ! {request, self(), Message},
	 receive
	 {reply, Reply} -> Reply
	 end.
	 
inc() -> call(inc).
dec() -> call(dec).
get() -> call(get).
stop() -> call(stop).
