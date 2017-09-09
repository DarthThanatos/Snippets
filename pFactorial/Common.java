package pFactorial;
import java.util.*;

public class Common {
	private static volatile Common instance = null;
	LongInt[] factorials = null;
	public int N;
	
	private Common(int N) {
		this.factorials = new LongInt[N+1];
		this.N = N;
	}
 
	public static synchronized Common getInstance(int N) {
		if (instance == null) {
			instance = new Common(N);
		}
		return instance;
	}
 
	public synchronized void manage(int i,LongInt Data) {
			//System.out.println("Receiving " + Data);
			this.factorials[i] = Data;
			N--;	
			System.out.println(N);
	}
	public void getRes(){
		int i = 1;
		for (LongInt step : this.factorials){
			System.out.println(i + "." + step);
			i++;
		}
	}
}


