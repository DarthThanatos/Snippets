package lab6;

public class Proxy {
    ConsumerQueue consumerQueue;
    ProducerQueue producerQueue;
    Servant servant;
    Scheduler scheduler;
    
    public Proxy(){
        scheduler = new Scheduler();
        servant = new Servant();
    }

    public MessageFuture get(){
    	MessageFuture result = new MessageFuture();
        Get get = new Get(servant,result);
        scheduler.enqueueConsumer(get);
        return result;
    }

    public void put(int number){
        Put methodRequest = new Put(servant, number);
        scheduler.enqueueProducer(methodRequest);
    }
}
