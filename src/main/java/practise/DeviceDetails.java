package practise;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceDetails implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private String deviceId;
	private int seqNum;
	private String deviceAccessToken;
	private Map<String, String> deviceIdToCommandIdMap = new ConcurrentHashMap<>();
	// private Queue<String> executedDeviceIds = new LinkedList<>();
	private List<String> device_ids = new ArrayList<String>();
	private String id;
	private String command_id;

	public DeviceDetails(String ip, String deviceId, int seqNum) {
		super();
		this.ip = ip;
		this.deviceId = deviceId;
		this.seqNum = seqNum;
	}

	public String getIp() {
		return ip;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public int getAndIncrementSeqNum() {
		return seqNum++;
	}

	synchronized public String getDeviceAccessToken() {
		return deviceAccessToken;
	}

	synchronized public void setDeviceAccessToken(String accessToken) {
		this.deviceAccessToken = accessToken;
	}

	public List<String> getDeviceCommandsIds() {
		return new ArrayList<String>(deviceIdToCommandIdMap.values());
	}

	/*
	 * public void addExecutedDeviceCommandId(String id) {
	 * executedDeviceIds.add(id); }
	 *
	 * public String getExecutedDeviceCommandId() { return
	 * executedDeviceIds.poll(); }
	 */
	@Override
	public String toString() {
		return "DeviceDetails [ip=" + ip + ", deviceId=" + deviceId + ", seqNum=" + seqNum + ", deviceAccessToken="
				+ deviceAccessToken + ", deviceIdToCommandIdMap" + deviceIdToCommandIdMap + ", id =" + id
				+ ", command_id= " + command_id + "]";
	}

	public String getDeviceIdToCommandIdMap(String id) {
		return deviceIdToCommandIdMap.get(id);
	}

	public void putInDeviceIdToCommandIdMap(String id, String commandID) {
		this.deviceIdToCommandIdMap.put(id, commandID);
	}

	public List<String> getDeviceCommandIds() {
		return device_ids;
	}

	public void addDeviceCommandId(String id) {
		device_ids.add(id);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommand_id() {
		return command_id;
	}

	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}

	public DeviceDetails clone() {
		// DeviceDetails obj = new DeviceDetails(this.ip, this.deviceId,
		// this.seqNum);
		DeviceDetails obj = (DeviceDetails) deepClone(this);
		return obj;
	}

	public Object deepClone(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static void main(String[] args) {
		DeviceDetails device = new DeviceDetails("ip1", "id1", 1);
		device.setCommand_id("abc_command");
		device.setId("abc_id");
		for(int i=0; i< 10; i++) {
			device.addDeviceCommandId("abc_id"+i);
			device.putInDeviceIdToCommandIdMap("abc_id"+i, "commandID:"+i);
		}

		System.out.println("Orig: "+device.toString());

		//Creates a new object with all same values
		DeviceDetails cloned = device.clone();

		System.out.println("Cloned: "+cloned.toString());
		System.out.println("================= Changing deviceId");
		device.deviceId="changes";

		System.out.println("Orig: "+device.toString());
		System.out.println("Cloned: "+cloned.toString());
		System.out.println("================= Changing id and command_id");

		cloned.setId("newId");
		cloned.setCommand_id("newCommandId");

		System.out.println("Orig: "+device.toString());
		System.out.println("Cloned: "+cloned.toString());

		System.out.println("=================");

	}
}
