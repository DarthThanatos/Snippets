package lab6;
public class Put extends MethodRequest {
	
    int msg;
    Servant servant;
    
    public Put(Servant servant, int msg){
        this.msg = msg;
        this.servant = servant;
    }
    
    @Override
    public void call(){
        servant.put(msg);
    }
    @Override
    public boolean guard(){
    	//System.out.println("checking if full");
    	return !servant.full();
    }

}
