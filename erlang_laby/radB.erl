%w init tworzymy addressbook nie tworzymy w start, bo to jest po stronie klienta, w loop receive, 
-module(radB).
-import(adB,[addEmail/4,addContact/3,findByEmail/2]).
-export([start/0,stop/0,addContact/2,addEmail/3,findByEmail/1,get/0]).

start()->
	register(ser,spawn(fun() -> loop([]) end)),
	init().

init()->
	ser ! {in, []}.

stop()->
	ser ! {stop,self()},
	receive
		M -> M
	end.

addContact(Name,Lastname)->
	ser ! {addC,Name,Lastname,self()},
	receive 
		 M -> M
	end.

addEmail(Name,Lastname,Email) ->
	ser ! {addE,Name,Lastname,Email,self()},
	receive
		M -> M
	end.

findByEmail(Email)->
	ser ! {find,Email,self()},
	receive 
		M -> M
	end.

get ()->
	ser ! {get,self()},
	receive
		M -> M
	end.
loop(Adr)->
	receive
		{stop,Pid} -> io:format("Finishing activity~n"), Pid ! Adr;
		{in,InitialValue} -> io:format("Activating~n"), loop(InitialValue);
		{get,Pid} -> io:format("getting ~p~n",[Pid]),Pid ! Adr, loop(Adr);
		{addC,Name,Lastname,Pid} -> io:format("Receiving ~p ~p~n",[Name,Lastname]),Res = adB:addContact(Name, Lastname, Adr), Pid ! Res,loop(Res);
		{addE,Name,Lastname,Email,Pid} -> io:format("Receiving ~p ~p ~p~n",[Name,Lastname,Email]),Res = adB:addEmail(Name,Lastname,Email,Adr), Pid ! Res,loop(Res); 
		{find,Email,Pid} -> Res =  adB:findByEmail(Email,Adr), Pid ! Res, loop(Adr) 
	end.
