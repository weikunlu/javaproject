package idv.weikun.main.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * like Thread.join, here use ExecutorService way.
 * 
 * @author weikunlu
 *
 */
public class ExecutorServiceWithFuture {

	public ExecutorServiceWithFuture() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//new ExecutorServiceWithFuture().defaultCall();
		
		new ExecutorServiceWithFuture().customCall();
	}

	void defaultCall(){
		final Callable<Integer> callable = new Callable<Integer>() {

			@Override
			public Integer call() throws InterruptedException {
				System.out.println("starting to sleep thread '"
						+ Thread.currentThread().getName() + "'.....");
				Thread.sleep(3000);
				System.out.println("finished sleeping thread '"
						+ Thread.currentThread().getName() + "'.");
				return new Integer(6);
			}
		};

		final ExecutorService execute = Executors.newCachedThreadPool();

		for (int i = 0; i < 3; i++) {
			final Future<Integer> futureTask = execute.submit(callable);

			System.out.println("about to get() via thread '"
					+ Thread.currentThread().getName() + "'.");
			try {
				Integer result = futureTask.get();
				System.out.println("get() = '" + result + "' via thread '"
						+ Thread.currentThread().getName() + "'.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();

			}

		}
		
		execute.shutdown();

		System.out.println("finish");
	}
	
	void customCall(){
		CustomCall customCall = new CustomCall();
		
		final Callable<Integer> callable = customCall;

		final ExecutorService execute = Executors.newSingleThreadExecutor();

		for (int i = 0; i < 3; i++) {
			
			customCall.condition = i;
			
			final Future<Integer> futureTask = execute.submit(callable);

			System.out.println("about to get() via thread '"
					+ Thread.currentThread().getName() + "'.");
			try {
				Integer result = futureTask.get();
				System.out.println("get() = '" + result + "' via thread '"
						+ Thread.currentThread().getName() + "'.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();

			}

		}
		
		execute.shutdown();

		System.out.println("finish");
	}
	
	class CustomCall implements Callable<Integer>{

		int condition;
		
		@Override
		public Integer call() throws Exception {
			System.out.println("starting to sleep thread '"
					+ Thread.currentThread().getName() + "' with condition "+condition);
			Thread.sleep(3000);
			System.out.println("finished sleeping thread '"
					+ Thread.currentThread().getName() + "'.");
			return new Integer(condition);
		}
		
	}
	
}
