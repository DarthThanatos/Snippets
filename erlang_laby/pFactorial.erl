-module(pFactorial).
-export([fac1/1,fac2/1,fac3/1]).


printList(_,[]) -> io:format("~n");
printList(N,[H|T]) ->
	io:format("~p. ~p~n",[N,H]),
	printList(N+1,T).

f1(N) when N =< 0 -> 1;
f1(N) -> f1(N-1) * N.

loop1(List,N) when N =< 0-> List ;
loop1(List,N)-> loop1([f1(N)] ++ List,N-1).

fac1(N) ->
	B = now(),
	A = loop1([],N),
	printList(1,lists:sort(A)),
	timer:now_diff(now(),B).


gather (P,0,Res) -> P ! Res;
gather(P,N,Res)-> 
	receive 
		M ->M
	end,
	gather(P,N-1,Res ++[M]).

thread(0,Acc,P) ->P ! Acc;
thread(N,Acc,P) -> thread(N-1,Acc * N, P).

loop2(0,_P)->ok;
loop2(N,P) ->
	spawn(fun() -> thread(N,1,P) end),
	loop2(N-1,P).

fac2(N)->
	B = now(),
	S = self(),
	P = spawn(fun() -> gather(S,N,[]) end),
	loop2(N,P),
	receive
		M -> M
	end,
	printList(1,lists:sort(M)),
	timer:now_diff(now(),B).

%erlang:system_info(schedulers).
t1(N) when N =< 0 -> 1;
t1(N) -> N * t1(N-1).

t2(N) when N =< 0 -> 1;
t2(N) -> N * t2(N-1).

wise_gather(P,N,Res) when N =< 0-> 
	P ! Res;
wise_gather(P,N,Res)-> 
	receive
		M -> M
	end,
	wise_gather(P,N-1,Res ++ [M]).

wise_thread2(P) ->
	receive
		N when N > 0 -> 
			P ! t2(N),
			wise_thread2(P);
		N when N == 0 -> 
			P ! 1, 
			ok
	end.
	
wise_thread1(P)->
	receive
		N when N > 1 -> 
			P ! t1(N),
			wise_thread1(P);
		N when N == 1 -> 
			P ! 1,
			ok
	end.

loop3(N,T1,T2,G) when N =< -1 -> ok;
loop3(N,T1,T2,G)->
	case N rem 2 == 1 of
		true -> T1 ! N;
		false -> T2 ! N
	end,
	loop3(N-1,T1,T2,G).

fac3(N)->
	S = self(),
	G = spawn(fun() -> wise_gather(S,N,[]) end),
	T1 = spawn(fun() -> wise_thread1(G) end),
	T2 = spawn(fun() -> wise_thread2(G) end),
	Start = now(),
	loop3(N,T1,T2,G),
	receive 
		M -> M
	end,
	printList(0,lists:sort(M)),
	timer:now_diff(now(),Start).
