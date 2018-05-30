package practise.java8;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Java9Tester {

	public List<String> sortJava8(List<String> toSort) {
		Collections.sort(toSort, (s1,s2)->s1.compareTo(s2));
		return toSort;
	}
	public void display(List<String> vals) {
		for(String val: vals) {
			System.out.println(val);
		}
		System.out.println("------");
	}
	public static void main(String[] args) {
//		List<String> toSort = new ArrayList<String>();
//		toSort.add("b");
//		toSort.add("e");
//		toSort.add("f");
//		toSort.add("s");
//		toSort.add("d");
//		toSort.add("a");
//		toSort.add("c");
//		Java9Tester obj = new Java9Tester();
//		obj.sortJava8(toSort);
//		obj.display(toSort);

		LocalDateTime d = LocalDateTime.now();
		LocalDateTime before = d.minusMinutes(3);
		System.out.println(d.toString());
		System.out.println(before.toString());

	}
}
