-module(var_server).
-behaviour(gen_server).
-version('1.0').

-export([start_link/1,  init/1,handle_call/3,handle_cast/2,terminate/2,getValue/0]).
-export([stop/0,incValue/0]).

start_link(InitialValue) ->
	gen_server:start_link(
		{local,var_server},
		var_server,
		InitialValue,[]).
		
init(InitialValue) ->
	{ok, InitialValue}.
	
getValue() ->
	gen_server:call(var_server,{getValue}).
	
handle_call({getValue},_From,Value) ->
		{reply,Value,Value}.

incValue() ->
	gen_server:cast(var_server,{incValue}).
	
handle_cast({incValue}, Value) ->
	{noreply,Value+1};

handle_cast(stop,Value) ->
	{stop,normal,Value}.

terminate(Reason,Value)->
	io:format("exit with value ~p~n", [Value]),
	Reason.

stop() ->
	gen_server: cast(var_server,stop).