package practise.poc.distributedLock.curator;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZKUtil;

/**
 * @author qindongliang
 * Curator operation zookeeper
 * Basic examples
 * **/
public class CuratorEx1 {

	static CuratorFramework zkclient=null;
	static String nameSpace="php";
	static String path = "/testDatCurator";
	static {

		  String zkhost="127.0.0.1:2181";//ZK host
		  RetryPolicy rp=new ExponentialBackoffRetry(1000, 3);//Retry mechanism
		  Builder builder = CuratorFrameworkFactory.builder().connectString(zkhost)
				  .connectionTimeoutMs(5000)
				  .sessionTimeoutMs(5000)
				  .retryPolicy(rp);
		  builder.namespace(nameSpace);
		  CuratorFramework zclient = builder.build();
		  zkclient=zclient;
		  zkclient.start();// Implemented in the front
		  zkclient.newNamespaceAwareEnsurePath(nameSpace);

	}

	public static void main(String[] args)throws Exception {
		CuratorEx1 ct=new  CuratorEx1();
		ct.getListChildren(path);
		//ct.upload("/jianli/123.txt", "D:\\123.txt");
		ct.createrOrUpdate(path,"c");
		ct.checkExist(path);
		ct.read(path);

		ct.delete(path);
		zkclient.close();


	}

	/**
	 * Create or update a node
	 *
	 * @Param path path
	 * @Param content content
	 * **/
	public void createrOrUpdate(String path,String content)throws Exception{

		zkclient.newNamespaceAwareEnsurePath(path).ensure(zkclient.getZookeeperClient());
	    zkclient.setData().forPath(path,content.getBytes());
	    System.out.println("Added successfully!!!");

	}

	/**
	 * Deleting ZK nodes
	 * @The path param path delete node
	 *
	 * **/
	public void delete(String path)throws Exception{
		zkclient.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
		System.out.println("Successfully deleted!");
	}


	/**
	 * To judge whether a path exists
	 * @param path
	 * **/
	public void checkExist(String path)throws Exception{

		if(zkclient.checkExists().forPath(path)==null){
			System.out.println("Path does not exist!");
		}else{
			System.out.println("The path already exists!");
		}

	}

	/**
	 * The read path
	 * @param path
	 * **/
	public void read(String path)throws Exception{


		String data=new String(zkclient.getData().forPath(path),"gbk");

		System.out.println("Read data:"+data);

	}


	/**
	 * @Param path path
	 * Get all the sub documents a node next
	 * */
	public void getListChildren(String path)throws Exception{

		List<String> paths=zkclient.getChildren().forPath(path);
		for(String p:paths){
			System.out.println(p);
		}

	}

	/**
	 * @The path to the param zkPath on ZK
	 * @Param localpath on the local file path
	 *
	 * **/
	public void upload(String zkPath,String localpath)throws Exception{
		createrOrUpdate(zkPath, "");//Create a path
		byte[] bs=FileUtils.readFileToByteArray(new File(localpath));
		zkclient.setData().forPath(zkPath, bs);
		System.out.println("Upload file successfully!");
	}





}
