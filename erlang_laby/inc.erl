-module(inc).
%% API
-export([start_link/0, increment/1,get/1,close/1, loop/1]).
 
%% START %%
start_link()   -> {ok, spawn_link(?MODULE,loop,[0])}.
 
%% INTERFEJS KLIENT -> SERWER %%
increment(PID) -> PID ! inc.
get(PID)       -> PID ! {get,PID}.
close(PID)     -> PID ! terminate.
 
%% OBSŁUGA WIADOMOŚCI %%
loop(N) -> receive
    inc -> ("inc~n"),loop(N+1);
    {get, PID} when is_pid(PID) -> ("get~n"), PID ! N, loop(N);
    terminate  -> io:format("The number is: ~B~nBye.~n",[N]), ok
  end.