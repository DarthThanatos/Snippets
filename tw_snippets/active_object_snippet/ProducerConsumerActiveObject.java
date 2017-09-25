package lab6;
public class ProducerConsumerActiveObject {
	
    public static void main(String[] args){
        Proxy proxy = new Proxy();
        Producer p = new Producer(proxy);
        Consumer c = new Consumer(proxy);
        Thread pt = new Thread(p);
        Thread ct = new Thread(c);
        pt.start();
        ct.start();
    }

}
