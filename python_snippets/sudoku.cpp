void sudoku(){

	int t1[9][9]={
		{3,7,4,0,0,2,5,0,6},
		{5,8,1,6,0,0,7,0,9},
		{0,9,2,0,0,7,3,1,4},
		{8,1,6,0,0,0,0,9,0},
		{0,0,0,0,2,0,0,0,0},
		{7,0,9,0,0,0,1,0,5},
		{1,0,0,8,0,9,0,0,0},
		{0,0,0,0,3,0,0,0,0},
		{0,5,0,0,0,6,0,7,0}



	};

	/*{
		{0,7,0,0,0,0,0,0,0},
		{3,0,0,0,0,0,1,9,0},
		{9,0,8,0,3,0,0,6,0},
		{0,0,3,0,0,8,4,0,0},
		{0,2,0,0,0,7,0,0,9},
		{0,0,6,0,0,3,0,2,0},
		{2,0,0,0,4,5,0,0,0},
		{4,0,1,9,0,6,0,8,0},
		{5,0,9,2,0,1,0,4,0}
		};*/

	/*{
		{0,2,9,0,0,7,0,0,0},
		{0,0,8,1,9,0,0,0,0},
		{0,0,0,0,0,0,0,3,8},
		{2,0,7,0,0,0,0,0,0},
		{0,0,0,9,0,5,0,0,1},
		{0,0,0,2,8,0,0,0,0},
		{0,6,5,7,0,9,3,0,0},
		{0,0,0,3,5,4,0,2,0},
		{0,0,0,6,1,8,7,5,9}
	};*/

	/*{
		{0,0,0,0,0,0,8,1,9},
		{1,6,0,0,8,0,3,4,2},
		{0,3,0,0,0,0,7,5,6},
		{0,0,0,0,0,0,2,9,0},
		{5,4,0,0,1,3,6,7,0},
		{8,0,0,0,0,0,0,3,5},
		{6,0,0,8,0,0,0,2,0},
		{0,5,0,0,3,6,9,8,1},
		{3,8,0,1,0,0,4,0,0}
	};*/

	/*{
		{4,0,6,1,8,0,0,2,0},
		{5,1,0,0,6,0,0,0,0},
		{9,0,0,0,7,0,0,0,0},
		{0,0,0,0,0,8,0,0,9},
		{6,8,2,0,0,1,0,0,7},
		{0,0,0,0,0,7,0,0,0},
		{0,0,0,0,0,0,0,8,4},
		{0,0,9,0,0,2,0,0,3},
		{0,0,4,9,5,0,6,0,0}
		};*/


	/*{
		{0,0,0,0,9,0,1,6,5},
		{9,0,0,0,5,0,0,8,7},
		{0,0,0,0,0,3,2,4,9},
		{1,0,8,0,0,0,0,0,0},
		{5,0,3,4,0,0,0,0,6},
		{7,9,0,1,8,0,0,0,4},
		{6,0,0,0,0,0,0,0,0},
		{0,0,0,0,3,0,0,0,0},
		{4,0,0,0,0,0,0,9,3}
		};
	*/
	/*{
	{0,5,0,4,0,0,0,0,0},
	{0,0,0,0,0,7,0,9,0},
	{7,0,3,0,5,0,4,0,2},
	{0,0,0,0,3,0,6,5,0},
	{0,0,8,0,0,0,0,0,7},
	{3,0,0,8,0,4,2,0,0},
	{0,0,0,0,0,2,0,0,5},
	{0,0,0,0,0,9,7,0,0},
	{4,0,2,0,0,0,8,0,0}
	};*/

	/*
	{
	{6,2,4,1,0,0,0,8,7},
	{7,8,5,6,2,0,0,9,1},
	{1,9,3,8,0,7,2,0,6},
	{9,0,0,0,8,0,0,0,3},
	{8,0,6,3,0,0,9,0,2},
	{3,5,0,0,1,0,8,0,4},
	{0,6,9,2,0,0,7,0,0},
	{0,3,8,0,0,1,6,2,0},
	{2,7,1,0,6,0,0,3,0},

	};*/

	/*{
	{0,0,7,0,0,8,0,3,0},
	{5,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,4,0},
	{8,9,1,5,6,4,2,7,3},
	{7,6,3,1,9,2,0,8,0},
	{0,0,5,8,3,7,0,0,1},
	{9,7,0,0,0,1,3,5,6},
	{3,0,6,9,7,5,0,0,0},
	{0,5,4,6,0,3,9,0,7}
	};*/

	/*{
	{0,0,0,0,2,0,3,9,6},
	{4,6,9,0,0,0,5,2,0},
	{3,2,0,9,5,6,7,0,0},
	{6,0,0,8,7,1,9,0,2},
	{7,9,0,0,6,2,8,0,0},
	{2,8,1,5,0,9,6,0,7},
	{9,3,6,0,0,0,2,8,5},
	{5,4,2,6,8,3,1,7,9},
	{0,0,0,2,9,5,4,6,3}
	};*/
	//{0,0,0,0,6,0,5,0,0,0,0,3,5,0,0,7,0,2,0,0,6,0,0,8,0,0,0,0,0,0,0,0,0,9,5,0,0,5,0,7,0,0,0,0,0,0,3,8,2,0,6,0,0,0,6,0,2,0,4,0,0,0,0,0,0,0,0,0,0,8,0,0,0,4,0,0,1,2,0,0,0}; zrobiona
	//{0,1,0,4,0,0,6,0,3,9,0,0,0,0,3,0,0,0,0,0,0,0,8,2,0,0,7,8,0,0,0,0,0,0,5,0,0,0,0,0,4,7,0,0,9,0,6,4,0,0,9,0,0,0,0,0,0,1,9,0,2,0,8,0,2,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0}; zrobiona
	int wyjdz=1, liczba=0,l1=0;
	int il_zer=0;
	bool pierwszy=false, drugi=false,trzeci=true;
	bool raz;

	for (int i=0;i<9;i++)
		for (int j=0;j<9;j++)
			if (t1[i][j]==0)il_zer++;

	int t[81];

	for (int i=0;i<9;i++)
		for (int j=0;j<9;j++) 
			t[i*9+j]=t1[i][j];

	//start w lewym gornym narozniku kazdego bloku; start_poz - przy marszu po wierszach (na boki)
	cout<< "Wejscie:"<<endl;
	for (int j=0,r=0;j<81;j++,r++) {
		cout<<t[j]<<" "; //cout<<r;
		if (r==8) {
		cout<<endl; r=-1;
		}
	}

	int vertic[18][10];
	bool kon[81][10];
	int pot[9][10];

	while (il_zer!=0 and l1<81){

		int i=0,tw=0,lt=0,gt=0,k=0,p, poczatek_kw=i;//i - indeks, tw - liczba trojek w wierszu(po trzy na wiersz), lt - lokalne trojki (po trzy na blok), gt - globalne trojki (po trzy bloki w jednym z trzech wierszy), k- kwadraty/bloki
		int tw1=0,start,a;

		int c=0;

		/*---------------------------------------------------------------------------------------------------------------------------------*/
		// PIERWSZA CZESC - MOZLIWOSCI DLA KAZDEGO POLA

		for (int f=0;f<81;f++)
			for (int g=0;g<10;g++) 
				kon[f][g]=true;

		for (int f=0;f<9;f++)
			for (int g=1;g<10;g++)
				pot[f][g]=0;

		while (k<9){ //idziemy po kwadratach od lewej do prawej
			for (int j=0;j<18;j++)
				for (int d=0;d<10;d++) 
					vertic[j][d]=0;


			start=poczatek_kw; liczba=0;


			//tutaj sprawdzamy kazdy element
			if (t[i]==0){
				while (tw1<9) { //porownujemy element ze wszystkimi innymi w jego macierzy
					if (start!=i)
						if (t[start]!=0) 
							kon[i][ t[start] ] = false;
					tw1++;
					if (tw1%3==0 and tw1!=0) 
						start+=6;
					start++;
				}//3
				tw1=0;
				//porownujemy element w pionie...
				for(int j=i+9;j<81;j+=9) // idac w dol
					if (t[j]!=0) kon[i][ t[j] ] = false;
			  
				for(int j=i-9;j>=0;j-=9)//idac w gore
					if (t[j]!=0) kon[i][ t[j] ] = false;
				 
				// ...i w poziomie
				for (int j=(i-i%9);j<(i-i%9)+9;j++)
					if (t[j]!=0) kon[i][ t[j] ] = false;
			}


			else for (int j=0;j<10;j++) 
				kon[i][j]=false;
			// ... i cisniemy dalej z koksem
			tw++;
			if (tw%3==0 and tw!=0) {
				if (tw==3)
					p=i;
				if (tw==9) 
					tw=0;
				lt++;

				if (lt==3) { 
					lt=0; 
					poczatek_kw=p+1;
					gt++; 
					k++; 
					c++;
					if (gt!=3 ) 
						i=p;
					else {
						gt=0; 
						poczatek_kw=i+1;
					}
				}
				else 
					i+=6;
			}
			i++;
		}
		raz=true;
		if (trzeci and raz) {trzeci=false; pierwszy=true;raz=false;}
		if (pierwszy and  raz) {pierwszy=false; drugi=true;raz=false;}
		if (drugi and raz) {drugi=false; trzeci=true;raz=false;} //SYSTEM FLAG - MODULY NIE WCHODZA SOBIE W PARADE I ZWRACAJA POPRAWNE WYNIKI

		bool nie1=false,nie2=false,nie3=false;
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
        //DRUGA CZESC - UNIKATY

        for (int j=0;j<18;j++)
            for (int d=0;d<10;d++) 
				vertic[j][d]=0;

		for (int j=0,f=0;j<81;j++){
			if (j%9==0 and j!=0) 
				f++; //poziom
			for (int g=1;g<10;g++)
				if (kon[j][g] ) 
					vertic[f][ g ]++;
		}

        for (int j=0,r,f=9;j<9;j++){
			r=j; //pion
			while(r<81){
				for (int g=1;g<10;g++)
					if ( kon[r][g] ) 
						vertic[f][ g ]++;
				r+=9;
			}
			f++;
        }
		
		raz=true;
		for (int f=0,j;f<18 and raz and pierwszy;f++){  //poziom
            for (int g=1;g<10 and raz;g++){
               if ( vertic[f][g]==1) {
                    raz=false;
                    nie1=true;
                    il_zer--;
					if (f<9){
						j=f*9;
						for (j;j<j+9;j++)
							if (kon[j][g]) {
								t[j]=g;
								break;
							}
					}
                    else{
                        j=f-9;
                        while (j<81){
                            if(kon[j][g] ) {
								t[j]=g; break;
							}
							j+=9;
                        }
                    }
                    for (int q=0;q<10;q++) 
						kon[j][q]=false;
				}
			}
		}

		/*---------------------------------------------------------------------------------------------------------------------------------------------------------*/
		//TRZECIA CZESC - STANDARDOWA STRATEGIA - SZACHOWANIE W KWADRACIE

		for (int i=0; i<81;i++){
			if ( (i>=0 and i<3) or (i>=9 and i<12) or(i>=18 and i<21) ) c=0;
			if ( (i>=3 and i<6) or (i>=12 and i<15) or(i>=21 and i<24) ) c=1;
			if ( (i>=6 and i<9) or (i>=15 and i<18) or(i>=24 and i<27) ) c=2;

			if ( (i>=27 and i<30) or (i>=36 and i<39) or(i>=45 and i<48) ) c=3;
			if ( (i>=30 and i<33) or (i>=39 and i<42) or(i>=48 and i<51) ) c=4;
			if ( (i>=33 and i<36) or (i>=42 and i<45) or(i>=51 and i<54) ) c=5;

			if ( (i>=54 and i<57) or (i>=63 and i<66) or(i>=72 and i<75) ) c=6;
			if ( (i>=57 and i<60) or (i>=66 and i<69) or(i>=75 and i<78) ) c=7;
			if ( (i>=60 and i<63) or (i>=69 and i<72) or(i>=78 and i<81) ) c=8;

		   if(t[i]==0 )
			for (int j=1;j<10;j++)
				if (kon[i][j] ) 
					pot[c][j]++;
		}

		raz=true;
		int d;
		
		for (i=0;i<81 and raz and drugi;i++){
			if ( (i>=0 and i<3) or (i>=9 and i<12) or(i>=18 and i<21) ) d=0;
			if ( (i>=3 and i<6) or (i>=12 and i<15) or(i>=21 and i<24) ) d=1;
			if ( (i>=6 and i<9) or (i>=15 and i<18) or(i>=24 and i<27) ) d=2;

			if ( (i>=27 and i<30) or (i>=36 and i<39) or(i>=45 and i<48) ) d=3;
			if ( (i>=30 and i<33) or (i>=39 and i<42) or(i>=48 and i<51) ) d=4;
			if ( (i>=33 and i<36) or (i>=42 and i<45) or(i>=51 and i<54) ) d=5;

			if ( (i>=54 and i<57) or (i>=63 and i<66) or(i>=72 and i<75) ) d=6;
			if ( (i>=57 and i<60) or (i>=66 and i<69) or(i>=75 and i<78) ) d=7;
			if ( (i>=60 and i<63) or (i>=69 and i<72) or(i>=78 and i<81) ) d=8;


			for (int j=1;j<10 ;j++){
				if (pot[d][j]==1 and kon[i][ j ]  and t[i]==0) {
					t[i]=j; 
					il_zer--;
					for (int g=0;g<10;g++)
						kon[i][g]=false;
					raz=false;
					nie2=true;
				}
			}
		}

		/*----------------------------------------------------------------------------------------------------------------------------------------------------------*/
		//CZWARTA CZESC - JEDYNY JEDYNEGO
		raz=true;
        for (int q=0, l ;q<81 and raz;q++){
			l=0;
            for (int w=1;w<10;w++){
                if(kon[q][w]) {
                    l++;
                    a=w;
				}
			}
			if (l==1) { 
                t[q]=a; 
				il_zer--;
                raz=false;
                nie3=true;
            }
        }
        l1++;

		/*======================================================================================================================================================*/
        //Labirynt

		struct NodE{
			int pamieta;
			NodE *back;
			NodE *left;
			NodE *right;
		};
		
		bool wyk[81];
		for (int q=0;q<81;q++) 
			wyk[q]=false;

		if (!nie1 and !nie2 and !nie3) {
			for (int q=0;q<9;q++)
				for (int w=1;w<10;w++)
					if (pot[q][w]==2){

					}
		}
    } 

    cout<<endl<< "Wyjscie: "<<endl;
    for (int j=0,r=0;j<81;j++,r++) {
        cout<<t[j]<<" ";
        if (r==8) {
			cout<<endl;
			r=-1;
		}
    }
	if (il_zer==0) 
		cout<<endl<<"Tadam!"<<endl;
    else {
        cout<<endl<<"Nie da sie tego wypelnic :("<<endl;
        cout<<endl<<"w kwadracie nr P: ";
		for (int q=1;q<10;q++) 
			cout<<q<<" "; 
		cout<<endl<<endl;
		for (int q=0;q<9;q++){
			cout<<"w kwadracie nr "<<q<<": ";
			for (int w=1;w<10;w++){
				cout<<pot[q][w]<<" ";
			}cout<<endl;
        }
     }
}

