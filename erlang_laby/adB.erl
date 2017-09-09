-module(adB).
-export([friendly/1,createAddressBook/0,addContact/3,addEmail/4,addPhone/4,removeContact/3,removeEmail/2,removePhone/2,getEmails/3,getPhones/3,findByEmail/2,findByPhone/2]).

-record(ebook,{name,lastname,phone_number,email_address}).

createAddressBook() -> [].

compareNL(Name,Lastname, #ebook{name = Name1,lastname = Lastname1}) ->
	case {Name,Lastname} of
		{Name1,Lastname1} -> true;
		{_,_} -> false
	end.

transform(H) ->
	#ebook{name = element(2,element(1,H)), lastname = element(2,element(2,H)),phone_number = element(2,element(3,H)), email_address = element(2,element(4,H))}.

addContact(Name,Lastname,[]) -> 
	[{
		{name,Name},
		{lastname,Lastname},
		{phone_number, []}, 
		{email_address,[]}
	}];
addContact(Name,Lastname,[H|T]) -> 
	A = transform(H),
	case compareNL(Name, Lastname, A) of
		true -> [H|T];
		false -> [H] ++ addContact(Name,Lastname,T)
	end.

theSameNL_P(Name,Lastname,Phone,[]) -> 
	[{
		{name,Name},
		{lastname,Lastname},
		{phone_number, [Phone]}, 
		{email_address,[]}
	}];
theSameNL_P(Name,Lastname,Phone,[H|T]) ->
	A = transform(H),
	case compareNL(Name,Lastname,A) of
		true -> 
			{H1,H2,{H3,PhoneList},H4} = H,
			[{H1,H2,{H3,[Phone]++PhoneList},H4}] ++ T;
		false ->
			[H] ++ theSameNL_P(Name,Lastname,Phone,T)
	end.

theSameP(_Phone,[]) -> false; 
theSameP(Phone,[{_A,_B,{_C,PhoneList},_D}|T]) ->
	case lists:any(fun(X) -> X == Phone end, PhoneList) of
		true -> true;
		false -> theSameP(Phone,T)
	end.

addPhone(Name,Lastname,Phone,[H|T])->
	case theSameP(Phone,[H|T]) of 
		true -> [H|T];
		false -> theSameNL_P(Name, Lastname, Phone,[H|T])
	end.

theSameNL_E(Name,Lastname,Email,[]) -> 
[{
	{name,Name},
	{lastname,Lastname},
	{phone_number, []}, 
	{email_address,[Email]}
}];
theSameNL_E(Name,Lastname,Email,[H|T]) ->
	A = transform(H),
	case compareNL(Name,Lastname,A) of
		true -> 
			{H1,H2,H3,{H4,EmailList}} = H,
			[{H1,H2,H3,{H4,EmailList ++ [Email]}}] ++ T;
		false ->
			[H] ++ theSameNL_P(Name,Lastname,Email,T)
	end.
theSameE(_Email,[]) -> false; 
theSameE(Email,[{_A,_B,_C,{_D,EmailList}}|T]) ->
	case lists:any(fun(X) -> X == Email end, EmailList) of
		true -> true;
		false -> theSameE(Email,T)
	end.

addEmail(Name,Lastname,Email,[H|T])->
	case theSameE(Email,[H|T]) of 
		true -> [H|T];
		false -> theSameNL_E(Name, Lastname, Email,[H|T])
	end.

removeContact(_Name,_Lastname,[]) -> [];
removeContact(Name,Lastname,[{H1,H2,H3,H4}|T]) -> 
	case {element(2,H1), element(2,H2)} of
		{Name,Lastname} -> removeContact(Name,Lastname,T);
		{_,_} -> [{H1,H2,H3,H4}] ++ removeContact(Name,Lastname,T)
	end.

remove_oneE(_Email,[])->[];
remove_oneE(Email,[H|T]) ->
	case H of
		Email -> T;
		_ -> [H] ++ remove_oneE(Email,T)
	end.

removeEmail(_Email,[]) -> [];
removeEmail(Email,[{H1,H2,H3,H4}|T]) ->
	case lists:any(fun (X) -> X == Email end, element(2,H4)) of
		true -> [{H1,H2,H3, {email_address,remove_oneE(Email,element(2,H4))}}] ++ T;
		false -> [{H1,H2,H3,H4}] ++ removeEmail(Email,T)
	end.

remove_oneP(_Phone,[])->[];
remove_oneP(Phone,[H|T]) ->
	case H of
		Phone -> T;
		_ -> [H] ++ remove_oneE(Phone,T)
	end.

removePhone(_Phone,[]) -> [];
removePhone(Phone,[{H1,H2,H3,H4}|T]) ->
	case lists:any(fun (X) -> X == Phone end, element(2,H3)) of
		true -> [{H1,H2,{phone_number,remove_oneP(Phone,element(2,H3))}, H4}] ++ T;
		false -> [{H1,H2,H3,H4}] ++ removePhone(Phone,T)
	end.
getEmails(_Name,_Lastname,[]) -> "null";
getEmails(Name,Lastname,[{H1,H2,_H3,H4}|T]) ->
	case {element(2,H1),element(2,H2)} of
		{Name,Lastname} -> element(2,H4);
		{_,_} -> getEmails(Name,Lastname,T)
	end.

getPhones(_Name,_Lastname,[]) -> "null";
getPhones(Name,Lastname,[{H1,H2,H3,_H4}|T]) ->
	case {element(2,H1),element(2,H2)} of
		{Name,Lastname} -> element(2,H3);
		{_,_} -> getPhones(Name,Lastname,T)
	end.

findByEmail(_Email,[]) -> "null";
findByEmail(Email,[{H1,H2,_H3,H4}|T]) ->
	case lists:any(fun(X) -> X == Email end, element(2,H4)) of
		true -> {element(2,H1),element(2,H2)};
		false -> findByEmail(Email,T)
	end.

findByPhone(_Phone,[]) -> "null";
findByPhone(Phone,[{H1,H2,H3,_H4}|T]) ->
	case lists:any(fun(X) -> X == Phone end, element(2,H3)) of
		true -> {element(2,H1),element(2,H2)};
		false -> findByPhone(Phone,T)
	end.

print_list([]) -> '';
print_list([H|T]) -> io:format("~s | ",[H]), print_list(T).

friendly([]) -> '';
friendly([{H1,H2,H3,H4}|T]) -> 
	io:format("Name: ~s, Lastname: ~s~n", [element(2,H1),element(2,H2)]),
	io:format("Phone numbers: "),print_list(element(2,H3)),
	io:format("~nEmail addresses: "), print_list(element(2,H4)),
	io:format("~n----------------------------------------------------------~n"),
	friendly(T).