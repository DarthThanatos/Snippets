package lab6;
import java.util.Random;

public class Producer implements Runnable {
    Random generator = new Random();
    Proxy proxy;

    public Producer(Proxy proxy){
        this.proxy = proxy;
    }

    public void run() {
        while (true){
        	int value = generator.nextInt(9) + 1; 
        	System.out.println("\n\nProducer putting " + value);
            proxy.put(value);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
        }
    }
}
