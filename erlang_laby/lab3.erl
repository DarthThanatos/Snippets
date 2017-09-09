Fun = fun F() ->
	receive 
		print -> io:format("hejka"), F();
		stop -> io:format("End"), ok
	end 
end.

IncLoop = fun Loop(N) ->
   receive
     inc   -> io:format("Working"),Loop(N+1);
     print -> io:format("~B~n",[N]), Loop(N);
     stop  -> ok
	end
end.

