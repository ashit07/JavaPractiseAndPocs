package practise.poc.distributedLock.curator;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.ZooKeeper;

import practise.poc.distributedLock.ZookeeperDistributedEnvExp;

public class VerifierCuratorLockImpl implements Runnable {

		private static final String ZK_HOSTS = "127.0.0.1:2181";
		String path;
		ZooKeeper zk = ZookeeperDistributedEnvExp.connect(ZK_HOSTS);
		CountDownLatch cd;
		List<String> dataCap;
		CuratorBlockingLock wl;
		String name;

		public VerifierCuratorLockImpl(String name, String path, CountDownLatch cd, List<String> dataCap) throws Exception {
			this.path = path;
			this.cd= cd;
			this.dataCap = dataCap;
			this.name=name;
			wl =  new CuratorBlockingLock(ZK_HOSTS, path, cd);
		}
		public void run() {
			try {
				System.out.println("Acquiring lock: "+Thread.currentThread().getName());
				wl.lock();
				System.out.println("WriterLock acquired for thread: "+Thread.currentThread().getName());
				int i=9;
				//Performing some computations- Get and update schema
				while (i++<20){
//					writeToFile();
					System.out.print("-");
				}
				System.out.println("");
				System.out.println("WriterLock releasing for thread: "+Thread.currentThread().getName());
				dataCap.add(name);

			} catch (Exception e) {
				System.out.println("-------Exception------");
				e.printStackTrace();
			} finally {
				cd.countDown();
				try {
					wl.releaseLock();
				} catch (Exception e) {
					System.out.println("Exception while releasing lock");
					e.printStackTrace();
				}
			}

		}


}
