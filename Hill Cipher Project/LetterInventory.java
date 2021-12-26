/*

Nathan Elliott
4/5/2018
CSE 143
TA: Benjamin MacMillan
Assignment #1 "LetterIventory.java"

Class LetterInventory creates a list of letters.  The list can be printed
as a string and added to or subtracted from another LetterInventory list.
The client can also set the amount of any letter in the string to a certain
value.

*/

public class LetterInventory {
   private int[] countArray;// the counts of each letter in the list
   public int size;// the total amount of letters in the list
   
   public static final int COUNT_ARRAY_SIZE = 26;
   
   // takes a string of characters chosen by the client
   // pre : data != null (throws NullPointerException otherwise)
   // post: constructs a count of each letter present in "data"
   public LetterInventory(String data) {
      countArray = new int[COUNT_ARRAY_SIZE];
      
      for (int i = 0; i <= data.length() - 1; i++) {
         data = data.toLowerCase();
         int letter = data.charAt(i);
         int displacement = letter - 'a';
         
         if (letter >= 'a' && letter <= 'z') {//ASCII index between 97 and 122 (a-z)
            countArray[displacement]++;// increments the count for each respective letter
            size++;// increments the number of elements in the list by one
         }
      }
   }
   
   // pre :
   // post: returns the number of letters in the list
   public int size() {
      return size;
   }
   
   // pre :
   // post: returns true if the list has letters in it and false otherwise
   public boolean isEmpty() {
      return size == 0;
   }
   
   // takes a char letter that the client wants to know the amount of in the list
   // pre :
   // post: returns the amount of occurrences of a specific letter in the list
   public int get(char letter) {
      letter = Character.toLowerCase(letter);
      checkIfLetter(letter);
      int displacement = letter - 'a';
      return countArray[displacement];
   }
   
   // pre :
   // post: returns a string version of the list that begins with "[" and
   // ends with "]". The number of each letter in the string is equals the
   // amount of each letter in the inventory
   public String toString() {
      String result = "[";
      for (int i = 0; i <= countArray.length - 1; i++) {
         for (int j = 0; j <= countArray[i] - 1; j++) {
            int letterNum = 'a' + i;
            char letter = (char)letterNum;
            result += letter;
         }
      }
      result += "]";
      return result;
   }
   
   // takes a letter of type char that a client wishes to change the amount
   // of in the list
   // takes an integer that represent the desired amount of that
   // letter in the list
   // pre : value >= 0 (throws IllegalArgumentException otherwise)
   // pre : letter >= 97 && letter <= 122 (throws IllegalArgumentException otherwise)
   // post: sets the amount of a letter in the list to a specific value
   public void set(char letter, int value) {
      letter = Character.toLowerCase(letter);
      
      if (value < 0) {
         throw new IllegalArgumentException();
      }
      
      checkIfLetter(letter);
      int displacement = letter - 'a';
      
      // value - countArray[displacement] = change
      // total - change = new total
      
      if (value > countArray[displacement]) {// value > letter count in inventory
         size += Math.abs(value - countArray[displacement]); // size += change
      }
      // if value == letter count in inventory, size doesn't change
      
      if (value < countArray[displacement]) {// value < letter count in inventory
         size -= Math.abs(value - countArray[displacement]); // size -= change
      }
      countArray[displacement] = value;// sets the index to this new value
      
      
   }
   
   // takes a LetterInventory object that the client wishes combine
   // with another LetterInventory object
   // pre :
   // post: returns a new LetterInventory that represents the result of
   // "combination" of the original LetterInventory and another LetterInventory
   public LetterInventory add(LetterInventory other) {
      LetterInventory sum = new LetterInventory("");
      
      for (int i = 0; i <= sum.countArray.length - 1; i++) {
         sum.countArray[i] = other.countArray[i] + countArray[i];
         sum.size += sum.countArray[i];
      }
      return sum;
   }
   
   // takes a LetterInventory the client wishes to "subtract" from another LetterInventory
   // pre :
   // post: returns a LetterInventory object that represents the
   // "difference" between the original LetterInventory and another LetterInventory
   // returns null if the subtraction is not possible
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory difference = new LetterInventory("");
      
      for (int i = 0; i <= difference.countArray.length - 1; i++) {
         if ((countArray[i] - other.countArray[i]) < 0) {
            return null;
         }
         difference.countArray[i] = countArray[i] - other.countArray[i];
         difference.size += difference.countArray[i];
      }
      return difference;
   }
   
   // takes a char that may or may not be one of the 26 letters in the English Alphabet
   // pre: 97 < letter's ASCII index < 122 (otherwise throws an IllegalArgumentException)
   // post: checks whether or not a letter lies outside the range of the
   // (lowercase) English Alphabet in the ASCII index
   private void checkIfLetter(char letter) {
      if (letter > 'z' || letter < 'a') {
         throw new IllegalArgumentException();
      }
   }
}