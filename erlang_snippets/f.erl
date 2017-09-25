-module(f).
-export([testRecord/0,catchme/1,increase/1]).

-record(grupa, {nazwa, licznosc, stan=aktywna}).
-record(nadgrupa,{nadnazwa,grupa}).

retlicznosc(X) ->
	X#grupa.licznosc.
retnazwe(X) ->
	X#grupa.nazwa.
retNadnazwa(X) ->
	X#nadgrupa.grupa#grupa.nazwa.

testPatterns(#grupa{nazwa = Nazwa, licznosc = 7}) ->
	Nazwa;
testPatterns(#grupa{licznosc = Licznosc}) when Licznosc > 1 ->
	Licznosc;
testPatterns(#nadgrupa{nadnazwa = NadNazwa, grupa = #grupa{nazwa = Nazwa}}) ->
	NadNazwa ++ Nazwa.

testRecord() ->
	Grupa1 = #grupa{nazwa="Gruppa 1", licznosc=12},
	Grupa2 = #grupa{nazwa="Gruppa 2", licznosc=7, stan=0},
	G1 = Grupa1,
	G2 = G1#grupa{nazwa = "Gruppa3"},
	Nad = #nadgrupa{
		nadnazwa = "Nad 3",
		grupa = #grupa{nazwa = "gruppa 4", licznosc = 2}
	},
	testPatterns(Grupa2).

catchme(N) ->
	try generate_exception(N) of
		Val -> {N,normal,Val}
	catch
		throw: X -> {N,thw,X};
		exit: X -> {N,ext,X};
		error : X -> {N,err,X}
	end.

generate_exception(1) -> a;
generate_exception(2) -> throw(a);
generate_exception(3) -> exit(a);
generate_exception(4) -> erlang:error(a);
generate_exception(5) -> {'EXIT', a};
generate_exception(6) -> 1/0;
generate_exception(7) -> list:seq(1,asd).

increase(X) when is_integer(X) or is_float(X) -> X + 1;
increase([H|T]) when is_integer(H) or is_float(H) -> [H+1 | increase(T)];
increase([H|T]) when is_list(H) -> [increase(H) | increase(T)];
increase([H|T]) -> [H | increase(T)];
increase(X) -> X.

% foldl/r, map, filter
% brak case'a, brak rozpatrzenia przypadku
% w ifie brakuje przypadku
% błąd dopasowania - błędny format zwracanych danych, proba zwiazania zwiazanej zmiennej
%błąd argumentu - BIF z wywołanymi błędnymi argumentami
%niezdefiniowana funkcja - brak w exporcie, brak nazwy, literówka
%błędny typ argumentu, dzielenie  przez zero
%klasy wyjątków: error, exit - pomiędzy procesami,throw - wewnątrz programu
% error - runtime rror lub jawne wywołanieerlang:error/1,2


