package practise.poc.distributedLock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.curator.shaded.com.google.common.io.Files;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.recipes.lock.LockListener;
import org.apache.zookeeper.recipes.lock.WriteLock;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;

import practise.poc.distributedLock.curator.VerifierCuratorLockImpl;

public class ZookeeperDistributedEnvExp {

	private static ZooKeeper zk;
	private static CountDownLatch connSignal = new CountDownLatch(0);
	private final static List<ACL> DEFAULT_ACL = ZooDefs.Ids.OPEN_ACL_UNSAFE;


	// host should be 127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002
	public static ZooKeeper connect(String host) throws Exception {
		zk = new ZooKeeper(host, 3000, new Watcher() {
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					System.out.println("Connected");
					connSignal.countDown();
				}
			}
		});
		connSignal.await();
		return zk;
	}

	public void close() throws InterruptedException {
		zk.close();
	}

	public void createNode(String path, byte[] data) throws Exception {
		zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public void updateNode(String path, byte[] data) throws Exception {
		System.out.println("Exists: "+zk.exists(path, true));
		zk.setData(path, data, zk.exists(path, true).getVersion());
	}

	public void deleteNode(String path) throws Exception {
		zk.delete(path, zk.exists(path, true).getVersion());
	}

	/**
	 * Works on create and delete of ZkNodes.
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void usingCreateDeleteRetryZKNode () throws KeeperException, InterruptedException, Exception {
		int numRequests = 20;
		CountDownLatch cd = new CountDownLatch(numRequests);
		ZooKeeper zk = connect("127.0.0.1:2181");
		String newNode = "/test1Date";


		if(zk.exists(newNode, true) != null) {
			deleteNode(newNode);
		}

		Set<Integer> s22 = new HashSet<Integer>();
		AtomicInteger i20 = new AtomicInteger(1);
		Runnable r12 = new Runnable() {
				@Override
				public void run() {
					try {
						boolean wait = true;
						while(wait) {
							try {
								Stat zkNodeStat = zk.exists(newNode, true);
								System.out.println(zkNodeStat);
								if(zkNodeStat==null) {
									System.out.println(Thread.currentThread().getName()+": Creating ZK Node ");
									createNode(newNode, "false".getBytes());
									System.out.println(Thread.currentThread().getName()+":  ZK Node Created");
									wait = false;
								}
							} catch (KeeperException e) {
								wait=true;
								e.printStackTrace();
							} catch (InterruptedException e) {
								wait=true;
								e.printStackTrace();
							} catch (Exception e) {
								wait = true;
								e.printStackTrace();
							}
						}
						int i=9;
						//Performing some computations- Get and update schema
						while (i<200){
							i++;
							System.out.print("--");
						}
						System.out.println("");
						System.out.println(Thread.currentThread().getName()+":	Deleting Node");
						s22.add(i20.getAndIncrement());
						deleteNode(newNode);
						cd.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

		};
		System.out.println("GetData after setting");
		ExecutorService exec = Executors.newFixedThreadPool(10);

		for (int i = 0; i < numRequests; i++) {
			exec.submit(r12);
		}
		cd.await();
		System.out.println("Printing Now------");
		for(Integer i : s22) {
			System.out.println(i);
		}

	}

	public void usingWriteAndReadDataZkNode () throws KeeperException, InterruptedException, Exception {

		CountDownLatch cd = new CountDownLatch(10);
		ZooKeeper zk = connect("127.0.0.1:2181");
		String newNode = "/test1Date";
		if(zk.exists(newNode, true) != null) {
			deleteNode(newNode);
		}
		createNode(newNode, "false".getBytes());
		List<String> zNodes = zk.getChildren("/", true);
		for (String zNode : zNodes) {
			System.out.println("ChildrenNode " + zNode);
		}
		byte[] data = zk.getData(newNode, true, zk.exists(newNode, true));
		System.out.println("GetData before setting");
		for (byte dataPoint : data) {
			System.out.print((char) dataPoint);
		}
		AtomicInteger i2 = new AtomicInteger(1);


		Runnable r22 = new Runnable() {
			@Override
			public void run() {
				try {
					boolean wait = true;
					while(wait) {
						try {
							byte[] dataNew = zk.getData(newNode, true, zk.exists(newNode, true));
							StringBuilder sb = new StringBuilder();
							for (byte dataPoint : dataNew) {
								sb.append((char)dataPoint);
							}
							System.out.println(sb);
							if(sb.toString().equals("false")) {
								System.out.println("Making it false");
								wait= false;
							}
						} catch (KeeperException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(Thread.currentThread().getName());
					updateNode(newNode, ("true").getBytes());
					byte[] data = zk.getData(newNode, true, zk.exists(newNode, true));
					System.out.println("");
					System.out.println("----------");
					for (byte dataPoint : data) {
						System.out.print((char) dataPoint);
					}
					updateNode(newNode, ("false").getBytes());
					System.out.println("");
					System.out.println("----------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Set<Integer> s22 = new HashSet<>();
		System.out.println("GetData after setting");
		ExecutorService exec = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 10; i++) {
			 exec.submit(r22);
		}
		cd.await();
		System.out.println("Printing Now------");
		for(Integer i : s22) {
			System.out.println(i);
		}

		data = zk.getData(newNode, true, zk.exists(newNode, true));
		for (byte dataPoint : data) {
			System.out.print((char) dataPoint);
		}
		deleteNode(newNode);
	}

	public void usingWriteLock() throws KeeperException, InterruptedException, Exception {

		int numRequests = 10;
		int numThreads = 5;
		CountDownLatch cd = new CountDownLatch(numRequests);

		ZooKeeper zk = connect("127.0.0.1:2181");
		String newNode = "/test1Date";
		String newNode2 = "/test2Date";
		if(zk.exists(newNode, true) == null) {
			createNode(newNode, "false".getBytes());
		}

		if(zk.exists(newNode2, true) == null) {
			createNode(newNode2, "false".getBytes());
		}
		List<String> s22 = new ArrayList<String>();
		System.out.println("GetData after setting");
		ExecutorService exec = Executors.newFixedThreadPool(numThreads);

		for (int i = 0; i < numRequests; i++) {
			if(i%2==0) {
			exec.submit(new VerifierWriteLockImpl("name"+i,newNode, cd, s22));
			} else {
				exec.submit(new VerifierWriteLockImpl("name"+i,newNode2, cd, s22));
			}
		}
		cd.await();
		System.out.println("Printing Now------");
		for(String i : s22) {
			System.out.println(i);
		}

	}
	public void usingCuratorLock() throws KeeperException, InterruptedException, Exception {

		int numRequests = 10;
		int numThreads = 5;
		CountDownLatch cd = new CountDownLatch(numRequests);

		ZooKeeper zk = connect("127.0.0.1:2181");
		String newNode = "/test1Date";
		String newNode2 = "/test2Date";
		if(zk.exists(newNode, true) == null) {
			createNode(newNode, "false".getBytes());
		}

		if(zk.exists(newNode2, true) == null) {
			createNode(newNode2, "false".getBytes());
		}
		List<String> s22 = new ArrayList<String>();
		System.out.println("GetData after setting");
		ExecutorService exec = Executors.newFixedThreadPool(numThreads);
		Thread.sleep(2000);
		for (int i = 0; i < numRequests; i++) {
			if(i%2==0) {
			exec.submit(new VerifierCuratorLockImpl("name"+i,newNode, cd, s22));
			} else {
				exec.submit(new VerifierCuratorLockImpl("name"+i,newNode2, cd, s22));
			}
		}
		cd.await();
		System.out.println("Printing Now------");
		for(String i : s22) {
			System.out.println(i);
		}

	}
	public static void main(String args[]) throws Exception {
		ZookeeperDistributedEnvExp connector = new ZookeeperDistributedEnvExp();
	//	connector.usingCreateDeleteRetryZKNode();
	//	connector.usingWriteLock();
		connector.usingCuratorLock();
	}



//	class VerifierWriteLockImpl implements Runnable{
//		String path;
//		ZooKeeper zk = connect("127.0.0.1:2181");
//		CountDownLatch cd;
//		List<String> dataCap;
//		BlockingWriteLock wl;
//		String name;
//
//		public VerifierWriteLockImpl(String name, String path, CountDownLatch cd, List<String> dataCap) throws Exception {
//			this.path = path;
//			this.cd= cd;
//			this.dataCap = dataCap;
//			this.name=name;
//			wl =  new BlockingWriteLock(name, zk, path);
//		}
//		public void writeToFile() {
//			try {
//				Files.write(name.getBytes(), new File("/Users/junas01/Documents/workspace/practise-java8/src/main/resources/abc.txt"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		public void sendRequest() {
//			int docTypeNum= 550;
//			String mapping = "";
//			mapping = "{\"product_id\":\"PIM_4\",\"doc_type_id\":\"wb\",\"doc_type_version\":\""+560+"\",\"mappings\":{\"data\":{\"properties\":{\"userid1\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"userid39\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"userid322\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"userid321\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"new_add1\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"timings\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd\",\"isArray\":true},\"GeoLocation\":{\"type\":\"object\",\"properties\":{\"altitude\":{\"type\":\"integer\",\"isArray\":true}}},\"name"+name+"\":{\"type\":\"string\"}}}}}";
//			ClientConfig config = new DefaultClientConfig();
//			config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
//			Client client = Client.create(config);
//			WebResource service = client.resource(UriBuilder.fromUri(
//				"http://junas01-I188017.ca.com:8080/onboarding").build());
//			ClientResponse response = service.path("doc_type").accept(MediaType.APPLICATION_JSON).type("application/json").method("PATCH", ClientResponse.class, mapping);
//
//			System.out.println(response.getStatus() +"-----");
//			if (response.getStatus() != 204) {
//
//		            throw new RuntimeException("Failed 2: HTTP error code : "
//		                    + response.getStatus()+"----"+response.toString());
//		     }
//		        // display response
////		        String output = response.getEntity(String.class);
////		        System.out.println(output + "\n");
//		}
//		public void run() {
//			try {
//				System.out.println("Acquiring lock: "+Thread.currentThread().getName());
//				wl.lock();
//				System.out.println("WriterLock acquired for thread: "+Thread.currentThread().getName());
//				int i=9;
//				//Performing some computations- Get and update schema
////				while (i<20){
////					writeToFile();
////				}
//				try {
//				sendRequest();
//				} catch (RuntimeException e) {
//
//				}
//				System.out.println("");
//				System.out.println("WriterLock releasing for thread: "+Thread.currentThread().getName());
//				dataCap.add(name);
//				cd.countDown();
//				wl.unlock();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}

//	class BlockingWriteLock {
//	    private final String name;
//	    private final String path;
//	    private final WriteLock writeLock;
//	    private final CountDownLatch lockAcquiredSignal = new CountDownLatch(1);
//
//
//	    public BlockingWriteLock(String name, ZooKeeper zookeeper, String path) {
//	        this(name, zookeeper, path, DEFAULT_ACL);
//	    }
//
//	    public BlockingWriteLock(String name, ZooKeeper zookeeper, String path, List<ACL> acl) {
//	        this.name = name;
//	        this.path = path;
//	        writeLock = new WriteLock(zookeeper, path, acl, new SyncLockListener());
//	    }
//
//	    public void lock() throws InterruptedException, KeeperException {
//	        writeLock.lock();
//	        lockAcquiredSignal.await();
//	    }
//
//	    public boolean lock(long timeout, TimeUnit unit) throws InterruptedException, KeeperException {
//	        writeLock.lock();
//	        return lockAcquiredSignal.await(timeout, unit);
//	    }
//
//	    public boolean tryLock() throws InterruptedException, KeeperException {
//	        return lock(1, TimeUnit.SECONDS);
//	    }
//
//	    public void unlock() {
//	        writeLock.unlock();
//	    }
//
//	    class SyncLockListener implements LockListener {
//	        @Override
//	        public void lockAcquired() {
//	            lockAcquiredSignal.countDown();
//	        }
//
//	        @Override
//	        public void lockReleased() {
//	            System.out.println("Lock released by "+name+" on "+path);
//	        }
//	    }
//	}

}
