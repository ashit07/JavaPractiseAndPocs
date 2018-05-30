package practise.tricks;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TreeMapValueSort {

  public static void main(String[] args) {
    String[] arr = {"010", "111", "100"};
    Comparator<String> valueSorter = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        int length1 = 0;
        for (int i = 0; i < o1.length(); i++) {
          if (o1.charAt(i) == '1') {
            length1++;
          }
        }
        int length2 = 0;
        for (int i = 0; i < o2.length(); i++) {
          if (o2.charAt(i) == '1') {
            length2++;
          }
        }
        if (length1 == length2 && o1.equals(o2)) {
          return 0;
        }
        if (length1 > length2) {
          return 1;
        }
        return -1;
      }
    };

    Set<String> sortedIndexes = new TreeSet<>(valueSorter);
    for (int i = 0; i < arr.length; i++) {
      sortedIndexes.add(arr[i]);
    }
    for (String index : sortedIndexes) {
      System.out.println(index);
    }
  }

}
