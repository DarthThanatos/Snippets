package lab6;

public class MessageFuture {
	private Integer value;
	public MessageFuture(){
		value = null;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
	
}
