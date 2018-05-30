package practise.string;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {


	public static boolean searchForIpAddress(final String hostIps, final String clientIpAddress) {
		if (null != hostIps && null != clientIpAddress) {
			String[] splitIps = hostIps.split(",");

			for (String ips : splitIps) {
				String[] hostPorts=ips.split(":");
				if (hostPorts[0].trim().equals(clientIpAddress.trim())) {
					return Boolean.TRUE;
				}
			}
		}

		return Boolean.FALSE;

	}

	public static void utfExp () throws UnsupportedEncodingException {
		String abc = "This is a string .  ; @ # $ < > { { } [] ";
		byte[] byteArr = abc.getBytes(Charset.forName("UTF-16"));
		System.out.println(byteArr.length);
		String abcUTF_16 = new String (byteArr, "UTF-16");
		System.out.println(abcUTF_16);

		byte[] byteArr2 = abcUTF_16.getBytes(Charset.forName("UTF-8"));
		System.out.println(System.currentTimeMillis());
//		ByteBuffer byteArr2 = Charset.forName("UTF-8").encode(abcUTF_16);
		System.out.println(byteArr2.length);

		String abcUtf_8 = new String (byteArr2, "UTF-8");
//		String abcUtf_8 = Charset.forName("UTF-8").decode(byteArr2).toString();
		System.out.println(System.currentTimeMillis());
		System.out.println(abcUtf_8);
	}

	public static int countNumberOfWords(String str, String word, int limitIndex) {
		int count =0;
		if(str.contains(word)) {
			int lastIndex = 0;

			while(lastIndex != -1){

			    lastIndex = str.indexOf(word,lastIndex);

			    if(lastIndex != -1){
			        count ++;
			        lastIndex += word.length();
			    }
			    if (limitIndex > 0 && lastIndex >= limitIndex) {
					break;
				}
			}
			System.out.println(count);
		}

		return count;
	}

	private static int getArrayIndexInString(String str, int count){
	int index = -1;
	int lastIndex = 0;
	// System.out.println(str);
	if (str.contains("array")) {
		for (int i = 0; i < count; i++) {
			index = str.indexOf("array[", lastIndex) + 6;
			int index2 = str.indexOf("]", index)+1;
			index = index2;
			lastIndex = index;
			System.out.println(str.substring(0,index));
		}
	}
	return index;
	}

	public static void mergeTwoStrings(String matchKey, String modifiedKey) {
		String word = "¼";
		String namespace = "com.ca.jarvis";
		Pattern nestedObjPattern = Pattern.compile(word + namespace + "[a-zA-Z[\\d]+\\._]+" + word);
		matchKey="body¼app_screen¼array[0]¼apps¼array[0]¼address¼array[0]¼obj¼com.ca.jarvis.obj_name_0¼obj1¼com.ca.jarvis.obj1_name_0¼obj2¼array[0]¼obj4¼string";
		modifiedKey="body¼app_screen¼array[0]¼apps¼array[11]¼address¼array[0]¼obj¼obj1¼obj2¼array[11]¼abj4";

		System.out.println(matchKey.replaceAll("\\[[\\d.]+\\]", ""));

		Matcher m = nestedObjPattern.matcher(matchKey);
		String str = matchKey;
		String[] s1Arr = str.split(word);

		while (m.find()) {
			System.out.println("------------");
			System.out.println("Match Key: "+matchKey);
			System.out.println("Modif Key: "+modifiedKey);
			int count = countNumberOfWords(str, word, m.start());
			String[] s2Arr = modifiedKey.split(word);
			List<String> abc = Arrays.asList(s2Arr);
			List<String> abc2 = new LinkedList<>();
			abc2.addAll(abc);
			abc2.add(count, s1Arr[count]);
			StringBuilder temp2 = new StringBuilder();
			for (String s : abc2) {
				temp2.append(s).append(word);
			}
			temp2.deleteCharAt(temp2.length()-1);
			modifiedKey=temp2.toString();
			System.out.println(temp2);
			System.out.println("");

		}
		System.out.println(modifiedKey);
	}
	public static void main(String[] args) {
//		String s1="abc";
//		String s2 = new String("abc");
//		s2.concat("jkl");
//		System.out.println(s2);
//		System.out.println(s1==s2);

	//	System.out.println(searchForIpAddress("host1:port1", "host1"));
//		try {
//			utfExp();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		//countNumberOfWords("body¼app_screen¼array[0]¼apps¼array[0]¼name2", "array");

	//	String str1 = "body¼app_screen¼array[0]¼apps¼array[10]¼name2¼array[20]¼name3¼string";
	//	System.out.println(str1.length());
	//	int index = getArrayIndexInString(str1, 3);

		mergeTwoStrings("", "");

//		System.out.println(str1.substring(0, index+8));
//		System.out.println(str1.substring(index+6, str1.indexOf("]", index+1)));
//		System.out.println(str1.substring(index+8));

//		String str = 	"{\"User[0]¼Name\":\"xyz\",\"User[0]¼DOB\":\"1962-02-05T00:00:00+0000\",\"User[0]¼location\":\"41.12,-71.34\"}";
//		String pattern = "((\"User.*?¼DOB\"):([\\.]*))";
//		// String str2 = str.replaceAll("(@timestamp):(\\d+)", "");
//		Pattern mixPattern = Pattern.compile(pattern);
//
//		Matcher m = mixPattern.matcher(str);
//		System.out.println("m.find: " + m.find());
//		System.out.println(m.group(0));
//		while (m.find()) {
//			m.start();
//			System.out.println("m2.group(1) " + m.group(0));
//			System.out.println("m2.group(2) " + m.group(1));
//			// System.out.println("m2.group(4) " + m.group(4));
//			// System.out.println("m2.group(4) " + m.group(5));
//			// System.out.println("m2.group(4) " + m.group(6));
//			m.end();
//		}

		String s134 = "http://cinonavrovm.ca.com https://ciavrosslvm.ca.com http://cinonavrodocker.ca.com http://ciavrodocker.ca.com 8080 http://cinonavrodocker.ca.com:8080 http://ciavrodocker.ca.com:8080 http://cinonavrodocker.ca.com:8080 http://ciavrodocker.ca.com:8080 http://cinonavrodocker.ca.com:8080 http://ciavrodocker.ca.com:8080 tenantci product3ci 1067 http://cinonavrovm.ca.com:9200 https://ciavrosslvm.ca.com:9200 http://cinonavrodocker.ca.com:9200 http://ciavrodocker:9200 N JarvisonVMwitavrodisabled JarvisonDockerwithavrodisabled JarvisonVMwithavroenabled JarvisonDockerwithavroenabled TS3249,TS1037,TS1038,TS1040,TS1041,TS1042,TS1043,TS1730,TS1763,TS1762,TS1764,TS1765,TS2131,TS3187,TS2912,TS3303,TS3342,TS3347,TS3319,TS3335,TS3399 TS3249,TS1709,TS3340,TS1729,TS2131,TS2134,TS2090,TS3341,TS2861,TS2916,TS2912,TS2923,TS3112,TS3137,TS3319,TS3335,TS3187,TS3303,TS3342,TS3347,TS3399 Y /home/jarvismail Sridhar.Raghunathan@ca.com,bhamo02@ca.com,katpo01@ca.com,Vasuki.Dileep@ca.com,Santosh.Balasubramanya@ca.com,UdayKumarReddy.Vepenjeri@ca.com,Roshini.Thyagaraj@ca.com,Mohammed.Moiduddin@ca.com,Shashanka.Arnady@ca.com,Sayan.Biswas@ca.com,Sasubilli.KrishnaChaitanya@ca.com,Sreeharsha.Rangarajan@ca.com,AnilaKumar.GVN@ca.com,Nithin.Shakthidhar@ca.com,LasyaPriya.Kancharlapalli@ca.com,Michael.Walker@ca.com,junas01@ca.com,Ashwin.Pai@ca.com,Serge.Marten@ca.com,Sunil.Koundinya@ca.com,Thomas.Donohue@ca.com,Krushna.Bagde@ca.com,Savitha.Satyanarayana@ca.com,vrushali.naik@ca.com,Sameer.Mirji@ca.com,patma22@ca.com,Piyush.Patel@ca.com,kalsh01@ca.com,krish06@ca.com,Mudita.Dixit@ca.com,Jiang.Zhang@ca.com,Silviya.Petrova@ca.com,PrasannaRam.Venkatachalam@ca.com hdfs://cinonavrovm.ca.com:9000 hdfs://ciavrosslvm.ca.com:9000 hdfs://cinonavrodocker.ca.com:9000 hdfs://ciavrodocker.ca.com:9000 http://ciavrodocker.ca.com:8081 http://cinonavrodocker.ca.com:8081 N 8443 https://vmcluster7.ca.com https://vmcluster7.ca.com:8443 https://vmcluster7.ca.com:8443 https://vmcluster7.ca.com:8443 https://vmcluster3.ca.com:9200 JarvisClusteronVMFreshInstall hdfs://vmcluster7.ca.com:9000 https://vmcluster7.ca.com:8443 TS3249,TS1709,TS3340,TS1729,TS2131,TS2134,TS2090,TS3341,TS2861,TS2916,TS2912,TS2923,TS3112,TS3137,TS3187,TS3319,TS3335,TS3303,TS3342,TS3347 http://ciswarm1 http://ciswarm1:8080 http://ciswarm1:8080 http://ciswarm1:8080 http://ciswarm1:9200 JarvisDockerSwarm hdfs://ciswarm1:9000 http://ciswarm1:8081 TS3249,TS1709,TS3340,TS1729,TS2131,TS2134,TS2090,TS3341,TS2861,TS2916,TS2912,TS2923,TS3112,TS3137,TS3187,TS3318,TS3319,TS3303,TS3342,TS3347,TS3398 TS3249,TS2150,TS1709,TS3340,TS1729,TS2131,TS2134,TS2090,TS3341,TS2861,TS2916,TS2912,TS2923,TS3112,TS3137,TS3187,TS3335,TS3319,TS3303,TS3342,TS3347,TS3399 /home/work";
		String[] sArr= s134.split(" ");
		for(int i=0; i< sArr.length; i++)
			System.out.println(i+1+"-->"+sArr[i]);

	}
}
