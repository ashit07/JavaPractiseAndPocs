package practise.string;

import java.io.PrintStream;

public class ReverseString {

	public String reverseStringRecursive(String val) {
		if(val.length()==0) {
			return "";
		}
		return val.charAt(val.length()-1)+reverseStringRecursive(val.substring(0, val.length()-1));
	}

	public String reverseStringShashi(String val) {
		try {
		char[] arr = val.toCharArray();
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
		return "";
	}
	public static void main(String[] args) {
		ReverseString rs = new ReverseString();
		System.out.println(rs.reverseStringRecursive("abcdef"));

	}
}
