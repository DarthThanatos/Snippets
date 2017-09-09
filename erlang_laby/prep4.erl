-module (prep4).
-export([start_and_link/0,start/0]).
-import(pFactorial,[fac1/1]).

start_and_link()->
    %process_flag(trap_exit, true),
    spawn(?MODULE, inverse, [2]),
    loop().
	
loop() ->
	io:format("working ~p~n",[self()]),
    receive
        Msg -> io:format("~p~n", [Msg])
    end,
    loop().

inverse(N) -> 1/N.


start() ->
	%process_flag(trap_exit,true),
	Pid = spawn_link(?MODULE,crash,[]),
	%Pid = spawn(crash/0),
	%Pid = spawn(fun () -> crash() end), % bez linkowania
	%Pid = spawn(?MODULE, crash,[]),
	do_sth_long(),
	link (Pid),
	1.

help ()->
	Pid = spawn(fun() -> crash() end),
	process_flag(trap_exit,true),
	do_sth_long(),
	link(Pid).
	
do_sth_long()->
	pFactorial:fac1(1000).

crash() ->
	1/0.
	
%internet protocol Transmission Control protocol udp - user datagram protocol(warstwa osi)
% fsm - finitestatemachine, gen_server,application, supervisor,gen_event
%process_flag(trap_exit,true).
%one for one, one for all rest for one
% Open Telecom Platform
% {'EXIT', Pid, Reason}