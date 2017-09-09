package lab5.zad1;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;

import lab5.zad1.CallableExample.WordLengthCallable;
 
public class MandelbrotVisual extends JFrame {
 
    private final static int MAX_ITER = 570;
    private static final double ZOOM = 150;
    private static BufferedImage I;
    private static double zx, zy, cX, cY, tmp;
 
    
    public static int calculatePartOfScreen(int x, int y){
    	zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        System.out.println("iter:" + iter);
        I.setRGB(x,y, iter | (iter << 8));
        return iter;
    }
    
    public MandelbrotVisual() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }
 
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
 
    public static class WordLengthCallable implements Callable {
        private int x,y;
        public WordLengthCallable(int x, int y) {
          this.x = x;
          this.y = y;
        }
        public Integer call() {
        	return calculatePartOfScreen(x, y);
        }
     }

    public static double fixedPoolTest(boolean setVisible){
    	setVisible = true;
        long startTime = System.currentTimeMillis();
    	MandelbrotVisual mbv = new MandelbrotVisual();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Set<Future<Integer>> set = new HashSet<Future<Integer>>();
        for (int y = 0; y < mbv.getHeight(); y++){
        	for (int x = 0; x < mbv.getWidth(); x++){
                Callable<Integer> callable = new WordLengthCallable(x,y);
                Future<Integer> future = pool.submit(callable);
                set.add(future);        		
        	}
        }
        for (Future<Integer> future : set)
			try {
				future.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        long endTime = System.currentTimeMillis();
        if (setVisible) mbv.setVisible(true);
        System.out.println(endTime - startTime);
    	return (double)(endTime - startTime);
    }
    
    public static void timeTestFixedPool(){
    	int N = 10;
    	double times[] = new double [N];
    	boolean setVisible = false;
    	for(int i =0; i< N; i++){
    		if (i == N-1) setVisible = true;
    		times[i] = fixedPoolTest(setVisible);
    	}
    	Statistics stat = new Statistics(times);
    	System.out.println("Mean: " + stat.getMean());
    	System.out.println("Variance: " + stat.getStdDev());
    }
    
    public static double singlePoolTest(boolean setVisible){
        long startTime = System.currentTimeMillis();
    	MandelbrotVisual mbv = new MandelbrotVisual();
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Set<Future<Integer>> set = new HashSet<Future<Integer>>();
        for (int y = 0; y < mbv.getHeight(); y++){
        	for (int x = 0; x < mbv.getWidth(); x++){
                Callable<Integer> callable = new WordLengthCallable(x,y);
                Future<Integer> future = pool.submit(callable);
                set.add(future);        		
        	}
        }
        long endTime = System.currentTimeMillis();
        if(setVisible) mbv.setVisible(true);
    	return (double)(endTime - startTime);
    }
    
    public static void timeTestSinglePool(){
    	int N = 10;
    	double times[] = new double [N];
    	boolean setVisible= false; 
    	for(int i =0; i< N; i++){
    		if (i == N-1) setVisible = true; 
    		times[i] = singlePoolTest(setVisible);
    	}
    	Statistics stat = new Statistics(times);
    	System.out.println("Mean: " + stat.getMean());
    	System.out.println("Variance: " +stat.getStdDev());
    }
    
    public static void main(String args[]) throws Exception {
    	timeTestFixedPool();
    	//timeTestSinglePool();
    }
}