package lab6;

public class Get extends MethodRequest {
    MessageFuture result;
    Servant servant;

    public Get(Servant servant, MessageFuture result){
    	this.result = result;
        this.servant = servant;
    }
    
    @Override
    public void call(){
        result.setValue(servant.get());
    }
    
    @Override
    public boolean guard(){
    	//System.out.println("checking if empty");
    	return !servant.empty();
    }
}
