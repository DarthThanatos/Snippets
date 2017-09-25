-module(stack).
-version('1.0').
-behaviour(gen_server).

-export([start/1,init/1, push/1, pop/0, handle_call/3, handle_cast/2]).

start(InitialValue)->
	gen_server:start_link({local,server}, stack, InitialValue,[]).

init(InitialValue) ->
	{ok,InitialValue}.

push(A)->
	gen_server:cast(server,{push,A}).

pop()->
	gen_server:call(server,{pop}).

handle_call({pop},_From,InitialValue) ->
	[H|T] = InitialValue,
	{reply,H,T}.

handle_cast({push,A}, InitialValue)->
	{noreply,[A] ++ InitialValue}.