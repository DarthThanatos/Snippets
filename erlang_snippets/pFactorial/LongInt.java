package pFactorial;

public class LongInt {
	QList res = null;
	
	public LongInt(int N){
		this.res = new QList();
		while (N >0){
			this.res.addFirst(N%10);
			N/=10;
		}
	}
		
	public LongInt multiply(LongInt L){
		QList Res = new QList();
		QList shorter = this.res.size()<L.res.size() ? this.res : L.res;
		QList longer = this.res.size()>=L.res.size() ? this.res : L.res;
		int vert = 0;
		for (int small : shorter.reverse().toArray()){
			int transfer = 0;
			int i = 0;
			for(int big: longer.reverse().toArray() ){
				int tmp;
				if (vert + i< Res.size()){
					tmp = Res.toArray()[vert + i] + big * small + transfer;
					Res.changeAt(vert + i,tmp%10);					
				}
				else {
					tmp = big*small + transfer;
					Res.addLast(tmp%10);
				}
				i++;
				transfer = tmp/10;		
			}
			if(transfer !=0)  Res.addLast(transfer);
			vert ++;
		}
		LongInt lRes = new LongInt(0);
		lRes.res = Res.reverse();
		return lRes;
	}
	
	public String toString(){
		String res = "";
		for (int step : this.res.toArray()) res += step;
		return res;
	}
}
