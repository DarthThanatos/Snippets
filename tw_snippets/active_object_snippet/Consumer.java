package lab6;
import java.util.Random;

public class Consumer implements Runnable {
    Random generator = new Random();
    Proxy proxy;

    public Consumer(Proxy proxy){
        this.proxy = proxy;
    }

    public void run(){
        while(true){
            MessageFuture result = proxy.get();
            while(result.getValue() == null);
            System.out.println("Got value: " + result.getValue());
        }
    }

}

