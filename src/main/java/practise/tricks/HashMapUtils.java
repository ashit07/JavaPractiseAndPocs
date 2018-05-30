package practise.tricks;

import java.util.HashMap;
import java.util.Map;

public class HashMapUtils {
public static void main(String[] rags) {

	 Map<Object1, String> map1 = new HashMap<Object1, String> ();
	 Object1 obj1 = new Object1("2", "two");
	 Object1 obj2 = new Object1("3", "two");

	 map1.put(obj1, "first");
	 map1.put(obj2, "sec");
	 map1.put(new Object1("2", "two"), "third");
	 map1.put(new Object1("3", "two"), "fourth");
	System.out.println(map1.get(obj1));
	obj1.setId("8");
	System.out.println(map1.get(obj1));

	}
}
class Object1 {

	private String id;
	private String name;
	Object1 (String id, String name) {
		this.id=id;
		this.name=name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Object1 other = (Object1) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
