public class MultiplicativeInverseTest {
   public static void main(String[] args) {
      
      for (int i = 0; i < 26; i++) {
         System.out.println("The number is " + i);
         System.out.println("The multiplicative inverse is " + findMultiplicativeInverseTest(i));
         System.out.println();
      }
   }
   
   public static double findMultiplicativeInverseTest(double det) {
      //double det = correctMod(determinant, 26);
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
      
      } while(pos != 0.0 && counter < 10000);
      
      if (counter < 10000) {
         System.out.println("Non found");
         return -1;
      } 
      return y / det;
      
   }
   
   public static double correctMod(double a, double b) {
      //a mod b = a - b × floor(a/b)
      return a - b * Math.floor(a / b);
   }
}