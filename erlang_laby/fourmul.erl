-module(fourmul).
-version('1.0').
-behaviour(gen_server).

-export([start/1,init/1,get/0,handle_cast/2,handle_call/3,mul/0]).

start(InitialValue)->
	gen_server:start_link({local,server},?MODULE,InitialValue,[]).

init(InitialValue)->
	{ok,InitialValue}.

get()->
	gen_server:call(server,{get}).
handle_call({get},_From,Value)->
	{reply,Value,Value}.

mul()->
	gen_server:cast(server,{mul}).
handle_cast({mul},Value) ->
	{noreply,Value*4}.
