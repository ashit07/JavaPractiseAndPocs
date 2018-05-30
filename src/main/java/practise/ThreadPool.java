package practise;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
		 private ThreadPoolExecutor executor;
	     private int corePoolSize = 2;
	     private int maximumPoolSize=5;
	     private long keepAliveTime=3000;
	     private static ThreadPool instance=null;

	     private ThreadPool() {
	    	 createThreadPoolExecutor(corePoolSize, maximumPoolSize);
	     }
		 private ThreadPool(int corePoolSize, int maximumPoolSize) {
			 createThreadPoolExecutor(corePoolSize, maximumPoolSize);
		 }
		 public static ThreadPool getThreadPoolInstance(int corePoolSize, int maximumPoolSize) {
			 if(instance==null) {
				 instance = new ThreadPool(corePoolSize, maximumPoolSize);
			 }
			 return instance;
		 }
		 public void createThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
		    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		    executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, workQueue);

		 }



}
