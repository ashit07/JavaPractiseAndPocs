package practise.poc.distributedLock;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.curator.shaded.com.google.common.io.Files;
import org.apache.zookeeper.ZooKeeper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;


public class VerifierWriteLockImpl implements Runnable{

	String path;
	ZooKeeper zk = ZookeeperDistributedEnvExp.connect("127.0.0.1:2181");
	CountDownLatch cd;
	List<String> dataCap;
	BlockingWriteLock wl;
	String name;

	public VerifierWriteLockImpl(String name, String path, CountDownLatch cd, List<String> dataCap) throws Exception {
		this.path = path;
		this.cd= cd;
		this.dataCap = dataCap;
		this.name=name;
		wl =  new BlockingWriteLock(name, zk, path);
	}
	public void writeToFile() {
		try {
			Files.write(name.getBytes(), new File("abc.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendRequest() {
		int docTypeNum= 550;
		String mapping = "";
		mapping = "{\"mappings\":{\"data\":{\"properties\":{\"userid1\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\"},\"userid39\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"userid322\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"userid321\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"new_add1\":{\"type\":\"geo_point\",\"index\":\"not_analyzed\",\"isArray\":true},\"timings\":{\"type\":\"date\",\"format\":\"yyyy-MM-dd\",\"isArray\":true},\"GeoLocation\":{\"type\":\"object\",\"properties\":{\"altitude\":{\"type\":\"integer\",\"isArray\":true}}},\"name"+name+"\":{\"type\":\"string\"}}}}}";
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(URLConnectionClientHandler.PROPERTY_HTTP_URL_CONNECTION_SET_METHOD_WORKAROUND, true);
		Client client = Client.create(config);
		WebResource service = client.resource(UriBuilder.fromUri(
			"http://localhost:9200/index1").build());
		ClientResponse response = service.path("mappings").accept(MediaType.APPLICATION_JSON).type("application/json").method("POST", ClientResponse.class, mapping);

		System.out.println(response.getStatus() +"-----");
		if (response.getStatus() != 204) {

	            throw new RuntimeException("Failed 2: HTTP error code : "
	                    + response.getStatus()+"----"+response.toString());
	     }
	        // display response
//	        String output = response.getEntity(String.class);
//	        System.out.println(output + "\n");
	}
	public void run() {
		try {
			System.out.println("Acquiring lock: "+Thread.currentThread().getName());
			wl.lock();
			System.out.println("WriterLock acquired for thread: "+Thread.currentThread().getName());
			int i=9;
			//Performing some computations- Get and update schema
			while (++i<15){
//				writeToFile();
				System.out.print("-");
			}
//			try {
//			sendRequest();
//			} catch (RuntimeException e) {
//
//			}
			System.out.println("");
			System.out.println("WriterLock releasing for thread: "+Thread.currentThread().getName());
			dataCap.add(name);
			cd.countDown();
			wl.unlock();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
