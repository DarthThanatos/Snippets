package lab6;
public class Servant{

	int commonSpace = 0;

    public void put(int numberToPut){
        commonSpace = numberToPut;
    }

    public int get(){
        int res = commonSpace;
        commonSpace = 0;
        return res;
    }
    
    public boolean empty(){
    	boolean isEmpty = commonSpace == 0 ? true : false;
    	return isEmpty;
    }
    
    public boolean full(){
    	boolean isFull = commonSpace != 0 ? true : false;
    	return isFull;    	
    }
}
