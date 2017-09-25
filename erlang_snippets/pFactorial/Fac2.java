package pFactorial;

import java.util.Date;

public class Fac2 {
	
	public void loop2(int N){
		for (int i = 0; i<=N; i++){
			Thread thr = this.new manager(i);
			thr.start();
		}
		while(Common.getInstance(N).N >= 0){}
	}
		
	class manager extends Thread{
		int N;
		public manager(int N){
			this.N = N;
		}
		
		public  void run(){
			LongInt res = new LongInt(1);
			for (int i = 1; i<= N; i++){
				LongInt I = new LongInt(i);
				res = res.multiply(I);
			}
			Common cs = Common.getInstance(N);
			cs.manage(N,res);
			
		}
	}
	
	public static void fac2(int N){
		Date date = new Date();
		long start = date.getTime();
		System.out.println("The task 2 has started");
		Common c = Common.getInstance(N);
		new Fac2().loop2(N);
		Date d = new Date();
		long end = d.getTime();
		c.getRes();
		System.out.println("\nThe time it lasted " + (end - start));
		
	}
}
