package com.bryzgalov.benchPress;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class JustClass {
  public static void main(String[] args) {
    String ransomNote = "aa";
    String magazine = "ab";

    char[] ransomNoteArray = ransomNote.toCharArray();
    boolean can = true;
    for (int i=0; i<ransomNoteArray.length; i++) {
      String stChar = String.valueOf(ransomNoteArray[i]);
      if(!magazine.contains(stChar)) {
        can = false;
      } else {
        magazine = magazine.replaceFirst(stChar, "");
      }
    }
    int a;
    //bubbleSort(mas);
  }
  public static void bubbleSort(int[] items) {
    boolean isSorted = false;
    while (!isSorted) {
      isSorted = true;
      for (int i = 0; i < items.length-1; i++) {
        if (items[i] > items[i+1]) {
          var value = items[i];
          items[i] = items[i+1];
          items[i+1] = value;
          isSorted = false;
        }
      }
    }
  }
}
