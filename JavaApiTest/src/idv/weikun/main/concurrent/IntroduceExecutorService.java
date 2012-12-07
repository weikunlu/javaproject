package idv.weikun.main.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IntroduceExecutorService {

	public IntroduceExecutorService() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new IntroduceExecutorService().cache();
		
		//new IntroduceExecutorService().fixed();
		
		//new IntroduceExecutorService().single();
	}
	
	void cache(){
		ExecutorService exec = Executors.newCachedThreadPool();
	       for (int i = 0; i < 5; i++)
	           exec.execute(new CustomThread(i));
	       exec.shutdown();
	}
	
	void fixed(){
		ExecutorService exec = Executors.newFixedThreadPool(2);
	       for (int i = 0; i < 5; i++)
	           exec.execute(new CustomThread(i));
	       exec.shutdown();
	}
	
	void single(){
		ExecutorService exec = Executors.newSingleThreadExecutor();
	       for (int i = 0; i < 5; i++)
	           exec.execute(new CustomThread(i));
	       exec.shutdown();
	}

	class CustomThread implements Runnable{
		int id;
		public CustomThread(int id) {
			this.id=id;
		}
		
		@Override
		public void run() {
			System.out.println("["+Thread.currentThread().getName()+"]Thread - "+id +":go sleep");
			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("["+Thread.currentThread().getName()+"]Thread - "+id +"go leave");
		}
	}
	
}
