import java.util.*;
import java.io.*;

public class HillCipher {
   public Matrix encodingMatrix;
   private List<Matrix> messageMatrices;
   private List<Matrix> encipheredMatrices;
   private List<Matrix> decipheredMatrices;
   private Matrix decodingMatrix;
   private double determinant;
   private int dimension;
   private Matrix adjugateMatrix;
   private Map<Integer, List<Integer>> spacePos;
   private int matrixCounter;
   
   
   public HillCipher(String keyWord) {
      encodingMatrix = getKeyMatrix(keyWord);
      determinant = encodingMatrix.getDeterminant();
      
      if (!gCDEqualsOne()) {
         throw new IllegalStateException("Please choose a different keyword");
      }
      
      matrixCounter = 0;
      messageMatrices = new ArrayList<Matrix>();
      encipheredMatrices = new ArrayList<Matrix>();
      decipheredMatrices = new ArrayList<Matrix>();
      
      adjugateMatrix = encodingMatrix.getAdjugateMatrix();
      adjugateMatrix = adjugateMatrix.modTwentySix();
      decodingMatrix = adjugateMatrix.multiplyByNum(findMultiplicativeInverseTest());
      decodingMatrix = decodingMatrix.modTwentySix();
      spacePos = new HashMap<Integer, List<Integer>>();
   }
   
   public Matrix getKeyMatrix(String keyWord) {
      if (keyWord.length() < 2) {
         throw new IllegalArgumentException("Word must be longer than two characters");   
      
      }
      
      int dimension = findDimensionOfEncryptionMatrix(keyWord);
      this.dimension = dimension;
      Matrix keyMatrix = new Matrix(dimension, dimension);
      for (int i = 0; i < keyWord.length(); i++) {
         char letter = keyWord.charAt(i);
         int displacement = letter - 'a';
         keyMatrix.add(displacement);
      }
      return keyMatrix;
   }
   
   private int findDimensionOfEncryptionMatrix(String keyWord) {
      for (int k = 2; k < keyWord.length(); k++) {
         if ((k * k) >= keyWord.length()) {
            return k;
         }
      }
      return 0; 
   }
   
   public void encryptMessage(String message) {
      double spaceDisplacement = 0;
      for (int i = 0; i < message.length(); i += dimension) {
         Matrix matrix = new Matrix(dimension, 1);
         
         for (int j = 0; j < dimension; j++) {
            if ((i + j) < message.length()) {
               char letter = message.charAt(j + i);
               int displacement = letter - 'a';
               matrix.set(displacement, j, 0);
            }
            if (matrix.contains(' ' - 'a')) {
               if (!spacePos.containsKey(i)) {// does not have an entry for this matrix
                  List<Integer> list = new ArrayList<Integer>();
                  list.add(j);
                  spacePos.put(matrixCounter, list); // matrix i has a space at position j
               } else {
                  spacePos.get(matrixCounter).add(j);
               }
            }
         }
         messageMatrices.add(matrix);
         matrixCounter++;
      }
      
      //possSpaceCharacters =  
      for (Matrix matrix : messageMatrices) {
         encipheredMatrices.add(encodingMatrix.multiply(matrix).modTwentySix());
      }
   }
   
   public void encryptMessage(File inputFile) throws FileNotFoundException {
      Scanner input = new Scanner(inputFile);
      
      while (input.hasNextLine()) {
         String line = input.nextLine(); // "hi my name is nathan"
         String[] words = line.split("[ ]");// ["hi", "my", "name", "is", "nathan"]
         
         for (int i = 0; i < words.length; i++) {
            encryptMessage(words[i]);
         }
      }
   }
   
   public void printEncryptedMessage(boolean control, File outputFile) throws FileNotFoundException {
      PrintStream output = new PrintStream(outputFile);
      
      for (Matrix matrix : encipheredMatrices) {
         for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                int intValue = (int)matrix.get(i, j);
                char letter = (char)intValue;
                letter += 'a';
               
               if (control) {
                  System.out.print(letter + " ");
               } else {
                  output.print(letter + " ");
               }
            }
         
         }
      }
   }
    
   public void decryptMessage() {
      for (Matrix matrix : encipheredMatrices) {
         decipheredMatrices.add(decodingMatrix.multiply(matrix).modTwentySix());   
      }
   }
   
   public double findMultiplicativeInverse() {
      double det = correctMod(determinant, 26);
      double y = 26;
      int counter = 0;
      double z = 0;
      double p = 0;
      int u = 0;
            
      do {
         counter++;
         z = y * counter + 1;
         p = z / det;
         u = (int)p;
         
      } while(p - u != 0);
      
      return z / det;
      
   }
   
   public double findMultiplicativeInverseTest() {
      double det = correctMod(determinant, 26);
      double y = 0;
      double x = 0;
      double pos = 0;
      int counter = 0;
      
      do {
         
         y = det * counter;
         x = (y - 1) / 26.0;
         // x = (105 - 1) / 26.0
         pos = x - (int)x;
         counter++;  
      
      } while(pos != 0.0);
      
      return y / det;
   }
   
   public double correctMod(double a, double b) {
      //a mod b = a - b × floor(a/b)
      return a - b * Math.floor(a / b);
   }
   
   public boolean gCDEqualsOne() {
      return findGCD((int)correctMod(determinant, 26), 26) == 1;  
   
   }
   
   private int findGCD(int a, int b) { 
      int d = a % b;
      
      if (d == 0) {
         return b;
      } else {
         return findGCD(b, d);
      }
   }
   
   
   public void printDecryptedMessage(boolean control, File outputFile) throws FileNotFoundException {
      Set<Integer> set = spacePos.keySet();
      PrintStream output = new PrintStream(outputFile);
      for (int h = 0; h < decipheredMatrices.size(); h++) {
         Matrix matrix = decipheredMatrices.get(h);
         for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
               int intValue = (int)matrix.get(i, j);
               char letter = (char)intValue;
               letter += 'a';
               
               if (spacePos.containsKey(h)) {// wait a second! this chunk has a space in it
                  List<Integer> list = spacePos.get(h);
                  for (int k = 0; k < list.size(); k++) {
                     if (list.get(k) == i) {
                        letter = ' ';
                     }
                  }
               }
               
               if (control) {  
                  System.out.print(letter + " ");
               } else {
                  output.print(letter + " ");
               }
            }
         }
      }
   }
}