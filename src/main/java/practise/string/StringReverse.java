package practise.string;

public class StringReverse {

	public static String reverse(String str, String result, int index) {
		if(index<str.length()) {
			result = str.charAt(index)+result;
			return reverse(str,result, index+1);
		}
		return result;
	}

	public static void main(String[] args) {

		System.out.println(reverse("abcddffgghhhjjj", "", 0));

	}
}
