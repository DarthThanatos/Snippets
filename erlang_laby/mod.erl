-module(mod).
-export([pass/1, pass/0]).
pass(Count) ->
	Pid = spawn(?MODULE, pass, []),
	Pid ! {self(),Count},
	receive
		_ -> ok
	end.
pass() ->
	io:format("Working"),
	receive
		{ParentPid, Count} when Count > 0 ->
			Pid = spawn(?MODULE, pass, []),
			Pid ! {self(),Count - 1},
			receive
				_ -> ParentPid ! ok
			end;
		{ParentPid,_} -> ParentPid ! ok
	after 5000 ->
		ok
	end. 