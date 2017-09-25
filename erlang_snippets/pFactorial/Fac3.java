package pFactorial;

import java.util.Date;

public class Fac3 {
	
	class manager1 extends Thread{
		int N;
		public manager1(int N){
			this.N = N;
		}
		
		public  void run(){
			for (int i = 1; i<= N; i+=2){
				LongInt res = new LongInt(1);
				for (int j = 1; j<= i; j++){
					LongInt J = new LongInt(j);
					res = res.multiply(J);
				}
				Common cs = Common.getInstance(N);
				cs.manage(i-1,res);
			}
			
		}
		
	}
	
	class manager2 extends Thread{
		int N;
		public manager2(int N){
			this.N = N;
		}
		
		public  void run(){
			for (int i = 2; i<= N; i+=2){
				LongInt res = new LongInt(1);
				for (int j = 1; j<= i; j++){
					LongInt J = new LongInt(j);
					res = res.multiply(J);
				}
				Common cs = Common.getInstance(N);
				cs.manage(i-1,res);
			}
			
		}
	}
	
	public static void fac3(int N){
		Date date = new Date();
		long start = date.getTime();
		System.out.println("The task 3 has started");
		Common c = Common.getInstance(N);
		Fac3 f3 = new Fac3();
		f3.new manager1(N).start();
		f3.new manager2(N).start();
		while(Common.getInstance(N).N > 0){}
		Date d = new Date();
		long end = d.getTime();
		c.getRes();
		System.out.println("\nThe time it lasted " + (end - start));
	}
}
