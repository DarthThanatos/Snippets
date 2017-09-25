package lab6;
public class Scheduler{
    Servant servant;
    ActivationQueue activationQueue;
    ConsumerQueue consumerQueue;
    ProducerQueue producerQueue;

    public Scheduler(){
        servant = new Servant();
        activationQueue = new ActivationQueue(10);
        producerQueue = new ProducerQueue(10);
        consumerQueue = new ConsumerQueue(10);
        dispatch();
    }
    
    private void dispatch(){
    	Dispatcher dispatcher = new Dispatcher(activationQueue,producerQueue, consumerQueue);
    	new Thread(dispatcher).start();
    }
    
    public void enqueueConsumer(MethodRequest methodRequest){
    	//activationQueue.enqueue(methodRequest);
    	consumerQueue.enqueue(methodRequest);
    }
    
    public void enqueueProducer(MethodRequest methodRequest){
    	//activationQueue.enqueue(methodRequest);
    	producerQueue.enqueue(methodRequest);
    }
    
}
