package practise.coding.techgig;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class BobTheBear {

  public static void main(String[] args) {

    List<Integer> fishLength = new LinkedList<>();
    fishLength.add(2);
    fishLength.add(4);
    fishLength.add(1);
    fishLength.add(2);
    fishLength.add(4);
    fishLength.add(1);

    List<Integer> fishTime = new LinkedList<>();
    fishTime.add(1);
    fishTime.add(4);
    fishTime.add(1);
    fishTime.add(6);
    fishTime.add(4);
    fishTime.add(2);

    calculateMaxFishes(fishLength, fishTime);


  }

  public static Integer calculateMaxFishes(List<Integer> fishLength, List<Integer> fishTime) {
    List<Integer> fishCollection = new LinkedList<>();
    List<Integer> sortedTimeIndexes = sortedIndexes(fishTime);
    for (Integer index : sortedTimeIndexes) {
      System.out.print(index + " ");
    }
    System.out.println("");
    for (Integer index : sortedTimeIndexes) {
      System.out.print(fishLength.get(index) + " ");
    }
    System.out.println("");
    for (Integer index : sortedTimeIndexes) {
      System.out.print(fishTime.get(index) + " ");
    }
    System.out.println("");
    int loopIndex = 0;
    while (loopIndex < fishTime.size()) {
      int numOfFishes = 0;
      int startIndex = sortedTimeIndexes.get(loopIndex);
      int minSum = fishLength.get(startIndex) + fishTime.get(startIndex);
      loopIndex++;
      numOfFishes++;
      for (int j = loopIndex; j < fishTime.size(); j++) {
        int index = sortedTimeIndexes.get(j);
        if (minSum > fishTime.get(index)) {
          int sum = fishLength.get(index) + fishTime.get(index);
          if (sum < minSum) {
            minSum = sum;
          }
          loopIndex++;
          numOfFishes++;
        } else {
          break;
        }
      }
      fishCollection.add(numOfFishes);
      System.out.println("Fishes: " + numOfFishes);
    }
    Comparator<Integer> descendSort = new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return o2 - o1;
      }

    };
    Collections.sort(fishCollection, descendSort);
    int numOfFishes = 0;
    if (fishCollection.size() >= 2) {
      numOfFishes = fishCollection.get(0) + fishCollection.get(1);
    } else {
      numOfFishes = fishCollection.get(0);
    }
    System.out.println(numOfFishes);
    return numOfFishes;
  }


  public static List<Integer> sortedIndexes(List<Integer> fishTime) {
    List<Integer> sortedIndexes = new LinkedList<>();
    List<Integer> tempFishTimes = new LinkedList<>(fishTime);
    for (int i = 0; i < fishTime.size(); i++) {
      int minIndex = 0;
      for (int j = 0; j < tempFishTimes.size(); j++) {
        if (tempFishTimes.get(j) < tempFishTimes.get(minIndex)) {
          minIndex = j;
        }
      }
      sortedIndexes.add(minIndex);
      tempFishTimes.remove(minIndex);
      tempFishTimes.add(minIndex, Integer.MAX_VALUE);
    }
    return sortedIndexes;
  }

}
