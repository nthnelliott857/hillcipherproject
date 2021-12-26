import java.util.*;
import java.io.*;

public class LetterCounter {
   public static void main(String[] args) throws FileNotFoundException {
      
      System.out.println("\"good\"");
      
      
      Scanner input = new Scanner(new File("dictionary.txt"));
      PrintStream output = new PrintStream(new File("letterinfo1.txt"));
      
      //findDigrams(output);
      
      Map<Character, Integer> letterCounts = new TreeMap<Character, Integer>();
      
      for (int i = 0; i < 26; i++) {
         int displacement = 'a' + i;
         char letter = (char)displacement;
         letterCounts.put(letter, 0);
      }
      
      int totalWords = 0;
      int totalCharacters = 0;
      while (input.hasNextLine()) {
         String word = input.nextLine();
         for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (letterCounts.containsKey(letter)) {
               int initialCount = letterCounts.get(letter);
               letterCounts.put(letter, (initialCount + 1));
            }
            totalCharacters++;
         }
         totalWords++;
      }
      
      Set<Character> letters = letterCounts.keySet();
      double sum = 0;
      
      output.println("Total Words: " + totalWords);
      output.println("Total Characters: " + totalCharacters);
      output.println();
      
      for (char letter : letters) {
         output.println(letter + ": " + letterCounts.get(letter));
         double percentage = (((double)letterCounts.get(letter)) / (double)totalCharacters * 100.0);
         output.println(percentage + " %");
         sum += percentage;
         output.println(sum + " % of total");
         output.println();
      }
   }
   
   public static void findDigrams(PrintStream output) throws FileNotFoundException {
      Scanner input2 = new Scanner(new File("dictionary.txt"));
      Map<String, Integer> digrams = new TreeMap<String, Integer>();
      
      while(input2.hasNextLine()) {
         String word = input2.nextLine();
         
         for (int i = 0; i < word.length() - 1; i++) {
            String digram = word.charAt(i) + "";
            digram += word.charAt(i + 1);
            
            if (digrams.containsKey(digram)) {
               int initial = digrams.get(digram);
               digrams.put(digram, initial + 1);
            } else {
               digrams.put(digram, 1);
            }
         }
      
      }
      
      Set<String> digramSet = digrams.keySet();
      String record = "";
      int recordNum = 0;
      
      for (String digram : digramSet) {
         if (digrams.get(digram) > recordNum) { 
            record = digram;
            recordNum = digrams.get(digram);
         }
         
         System.out.println("Digram " + " \""  + digram + "\" " + digrams.get(digram));
      }
      System.out.println();
      System.out.println("\"" + record + "\"" + " was the most common with " + digrams.get(record) + " entries"); 
   }
}