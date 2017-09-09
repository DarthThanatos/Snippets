package lab6;

public class Dispatcher implements Runnable {
	
	ActivationQueue activationQueue;
	ConsumerQueue consumerQueue;
	ProducerQueue producerQueue;
	
	public Dispatcher(ActivationQueue activationQueue, ProducerQueue producerQueue, ConsumerQueue consumerQueue){
		//this.activationQueue = activationQueue;
		this.producerQueue = producerQueue;
		this.consumerQueue = consumerQueue;
	}
	
	@Override
	public void run() {
        while(true){
            //activationQueue.dequeue();
        	producerQueue.dequeue();
        	consumerQueue.dequeue();
        }
	}

}
