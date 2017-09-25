package lab6;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ActivationQueue {
    int index = 0;
    int size;
	ArrayList<MethodRequest> activationQueue;
    Lock lock = new ReentrantLock();
    Condition requests = lock.newCondition();

    public ActivationQueue(int size){
        this.size = size;
        this.activationQueue = new ArrayList<MethodRequest>();
    }
   
    public void enqueue(MethodRequest methodRequest) {
        lock.lock();
        System.out.println("enqueue");
        activationQueue.add(methodRequest);
        lock.unlock();    
    }

    public void dequeue(){
        lock.lock();
        if(!activationQueue.isEmpty()){
        	MethodRequest result = activationQueue.get(0);
	        if (result.guard()){
	        	System.out.println("dequeue");
	        	result.call();
	        	activationQueue.remove(result);
	        }
        }
        lock.unlock();
    }

}

