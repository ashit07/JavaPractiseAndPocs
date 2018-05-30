package practise.poc.distributedLock;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.recipes.lock.LockListener;
import org.apache.zookeeper.recipes.lock.WriteLock;

class BlockingWriteLock {
    private final String name;
    private final String path;
    private final WriteLock writeLock;
    private final CountDownLatch lockAcquiredSignal = new CountDownLatch(1);
	private final static List<ACL> DEFAULT_ACL = ZooDefs.Ids.OPEN_ACL_UNSAFE;


    public BlockingWriteLock(String name, ZooKeeper zookeeper, String path) {
        this(name, zookeeper, path, DEFAULT_ACL);
    }

    public BlockingWriteLock(String name, ZooKeeper zookeeper, String path, List<ACL> acl) {
        this.name = name;
        this.path = path;
        writeLock = new WriteLock(zookeeper, path, acl, new SyncLockListener());
    }

    public void lock() throws InterruptedException, KeeperException {
        writeLock.lock();
        lockAcquiredSignal.await();
    }

    public boolean lock(long timeout, TimeUnit unit) throws InterruptedException, KeeperException {
        writeLock.lock();
        return lockAcquiredSignal.await(timeout, unit);
    }

    public boolean tryLock() throws InterruptedException, KeeperException {
        return lock(1, TimeUnit.SECONDS);
    }

    public void unlock() {
        writeLock.unlock();
    }

    class SyncLockListener implements LockListener {
        @Override
        public void lockAcquired() {
            lockAcquiredSignal.countDown();
        }

        @Override
        public void lockReleased() {
            System.out.println("Lock released by "+name+" on "+path);
        }
    }
}
