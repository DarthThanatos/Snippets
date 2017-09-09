-module(incdec).
-version('1.0').
-behaviour(gen_server).

-export([start/1, init/1,get/0,handle_call/3,handle_cast/2,inc/0,dec/0]).

start(InitialValue)->
	gen_server:start_link({local,server}, ?MODULE, InitialValue,[]).

init(InitialValue) ->
	{ok, InitialValue}.

get() ->
	gen_server:call(server,{get}).

handle_call({get},_From,Value) ->
	{reply,Value,Value}.

inc() ->
	gen_server:cast(server,{inc}).
dec() ->
	gen_server:cast(server,{dec}).

handle_cast({inc},Value) ->
	{noreply, Value + 1};
handle_cast({dec},Value) ->
	{noreply,Value - 1}.