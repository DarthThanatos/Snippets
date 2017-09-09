package pFactorial;

import java.util.ArrayList;
import java.util.Date;

public class Fac1 {
	public static LongInt f1(int N){
		LongInt res = new LongInt(1);
		for(int i = 1; i<=N; i++){
			LongInt I = new LongInt(i);
			res = I.multiply(res);
		}

		System.out.println(res);
		return res;
	}
	public static long fprim(int N){
		long res = 1;
		for (int i = 1; i<=N; i++) {res *= i; System.out.println("res"+i+" " + res);}
		return res;
	}
	public static void loop1(int N){
		ArrayList<LongInt> seq1 = new ArrayList<LongInt>();
		for (int i = 1; i<=N; i++){
			System.out.print(i + ".");
			seq1.add(f1(i));
		}
		
		//fprim(30);
		
	}
	
	public static void fac1(int N){
		Date date = new Date();
		long start = date.getTime();
		System.out.println("The task 1 has started");
		loop1(N);
		Date d = new Date();
		long end = d.getTime();
		System.out.println("\nThe time it lasted " + (end - start));
		//Common c = Common.getInstance(N);
		//c.getRes();
		
	}
}
