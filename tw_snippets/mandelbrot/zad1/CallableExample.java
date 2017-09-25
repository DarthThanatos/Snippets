package lab5.zad1;

import java.util.*;
import java.util.concurrent.*;

public class CallableExample {

  public static class WordLengthCallable implements Callable {
    private String word;
    public WordLengthCallable(String word) {
      this.word = word;
    }
    public Integer call() {
    	try {
    		Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return Integer.valueOf(word.length());
    }
 }

  public static void main(String args[]) throws Exception {
	String lines[] = {"asd","rob","abbbaba","asdasddsasad"};
    ExecutorService pool = Executors.newFixedThreadPool(3);
    Set<Future<Integer>> set = new HashSet<Future<Integer>>();
    for (String word: lines) {
      Callable<Integer> callable = new WordLengthCallable(word);
      Future<Integer> future = pool.submit(callable);
      set.add(future);
    }
    int sum = 0;
    for (Future<Integer> future : set) {
      sum += future.get();
    }
    System.out.printf("The sum of lengths is %s%n", sum);
    System.exit(sum);
  }
}