package practise.poc.distributedLock.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import locking.FakeLimitedResource;

public class CuratorBlockingLock {
	private static final String ZK_HOST = "127.0.0.1:2181";
	String hosts = ZK_HOST;
	String path;
	CountDownLatch cd;
	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 4);
	CuratorFramework client;
	private final InterProcessMutex lock;
	public CuratorBlockingLock(String hosts, String path, CountDownLatch cd) throws Exception{
		this.hosts=hosts;
		this.path = path;
		this.cd= cd;
		this.client= CuratorFrameworkFactory.newClient(hosts, retryPolicy);
		client.start();
		lock = new InterProcessMutex(client, path);
	}

	public void lock() throws Exception {
		if(!client.isStarted()) {
			client.start();
		}
		if(!lock.isAcquiredInThisProcess()) {
			while(!lock.acquire(500, TimeUnit.MILLISECONDS)) {
				//keep retrying
			}
		}

	}
	public boolean isAcquiredInThisProcess() {
		return lock.isAcquiredInThisProcess();
	}
	public void releaseLock() throws Exception {
		lock.release();
	}

}

class ExampleClientThatLocks {
	private final InterProcessMutex lock;
	private final FakeLimitedResource resource;
	private final String clientName;

	public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource,
			String clientName) {
		this.resource = resource;
		this.clientName = clientName;
		lock = new InterProcessMutex(client, lockPath);
	}

	public void doWork(long time, TimeUnit unit) throws Exception {
		if (!lock.acquire(time, unit)) {
			throw new IllegalStateException(clientName + " could not acquire the lock");
		}
		try {
			System.out.println(clientName + " has the lock");
			resource.use();
		} finally {
			System.out.println(clientName + " releasing the lock");
			lock.release(); // always release the lock in a finally block
		}
	}
}