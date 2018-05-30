package practise.environment;

import java.util.Map;
import java.util.Set;

public class SettingEnvVars {

	public static void main(String[] args) {

		System.out.println(System.getenv("PROP_HOME"));

		ProcessBuilder pb = new ProcessBuilder("/bin/sh"); // or any other program you want to run
	    Map<String, String> envMap = pb.environment();

	    envMap.put("PROP_HOME", "This is PROP_HOME");
	    Set<String> keys = envMap.keySet();
	    for(String key:keys){
	        System.out.println(key+" ==> "+envMap.get(key));
	    }

	    System.out.println(System.getenv("PROP_HOME"));

	}

}
