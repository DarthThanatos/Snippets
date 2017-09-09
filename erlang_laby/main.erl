-module(main).
-export([finite/1,messageRec/0,file_operations/0,binary_encryption/0, binary_decipherement/0,add/2,factorial/1,factorial/2,area/3,area/1,speak/1,say_sth/2,start_concurrency/2,factorialRecorder/3]).

finite(shape) ->
	case shape of 
		Point -> true;
		Segment -> true;
		Line -> false;
		HalfLineLeft -> true;
		HalfLineRight -> true
	end.

file_operations() ->
	{ok,S} = file:open("C://Users//Robert//Desktop//erlang//text.txt",write),
	io:format(S,"~s ~n",["Input/output operations"]),
	io:format(S,"~s ~n",["performed successfully"]),
	file:close(S),
	{ok,F} = file:open("C://Users//Robert//Desktop//erlang//text.txt",read),
	io:format(io:get_line(F,"")),
	io:format(io:get_line(F,"")),
	file:close(F).

binary_encryption() ->
	%Term  = [hello,"world",{from,ireland},2013],
	%Term = [io:get_line("Type the input: ")],
	Term = "2",
	Binary = term_to_binary(Term),
	{ok,IO} = file:open("C://Users//Robert//Desktop//erlang//binary.txt",[raw,write,binary]),
	file:pwrite(IO,0,integer_to_binary(20)),
	file:pwrite(IO,4,integer_to_binary(size(Binary))),
	file:pwrite(IO,20,Binary),
	file:close(IO).

binary_decipherement() ->
	{ok,IN} = file:open("C://Users//Robert//Desktop//erlang//binary.txt",[raw,read,binary]),
	{ok,File} = file:pread(IN,20,47),
	binary_to_term(File).

add(X, Y)->
	X + Y.

factorial(N) when N > 0 ->
	N * factorial(N - 1);
factorial(0) ->
	1.

area(Type,N,M) ->
	case Type of
		square -> N * N;
		circle -> math:pi()*N*N;
		triangle -> 0.5 * N * M
	end.

area({square,N}) ->
	N * N;
area({circle,R}) ->
	math:pi() * R * R;
area({triangle,B,H}) ->
	0.5*B*H;
area(_) ->
	{error,invalidObject}.

speak(Animal) ->
	Talk = 
		if 
			(Animal == cat) -> miaow;
			(Animal == dog) -> roofroof;
			true -> no_animal
		end,
	io:format("~w says ~w ~n",[Animal,Talk]).

factorial(N,TotalFactorial) when N > 0 ->
	factorial(N-1, N*TotalFactorial);
factorial(0,TotalFactorial) ->
	TotalFactorial.

factorialRecorder(Int,Acc,IoDevice) when Int > 0 ->
	io:format(IoDevice,"Current Factorial Log: ~p ~n", [Acc]),
	factorialRecorder(Int-1, Acc * Int, IoDevice);
factorialRecorder(0,Acc,IoDevice) ->
	io:format(IoDevice,"Factorial Results: ~p~n",[Acc]).

messageRec() ->
	receive
		{factorial,Int}->
			io:format("Factorial for ~p is ~p ~n",[Int,factorial(Int,1)]),
			messageRec();
		{factorialRecorder,Int}->
			{ok,IoDevice} = file:open("C://Users//Robert//Desktop//erlang//ConC.txt",write),
			factorialRecorder(Int,1,IoDevice),
			io:format("Factorial Recorder Done. ~n"),
			file:close(IoDevice),
			messageRec();
		Other->
			io:format("Invalid Match for ~p~n",[Other]),
			messageRec()
	end.

say_sth(_,0) ->
	io:format("Done ~n");
say_sth(Value,Times) ->
	io:format("~s ~n",[Value]),
	say_sth(Value,Times - 1).
start_concurrency(Value1,Value2) ->
	spawn(main,say_sth,[Value1,3]),
	spawn(main,say_sth,[Value2,3]).

% cd("C://Users//Robert//Desktop//erlang"). 