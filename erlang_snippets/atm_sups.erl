-module(atm_sups).
-version('1.0').
behaviour(supervisor).

-export([start_link/1,init/1,stop/1]).

supervisor:start_link( {local, Name}, module, arguments).

init(InitValue) ->
	{ok, {
		{one_for_all, 2, 2000},
			[ {var_server, {var_server, start_link, [InitValue]},
			permanent, brutal_kill, worker, [var_server]}		]
		}
	}.