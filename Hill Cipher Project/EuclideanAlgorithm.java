public class EuclideanAlgorithm {
   public static void main(String[] args) {
   
   
      System.out.println("The GCD is: " + findGCDTest(26, 14));
   }
   
   
   public static int findGCD(int a, int b) {
     int counter = 0;
     int d = 0;
     
     do {
      d = a - (b * counter);
      counter++; 
      
     } while(d > b);
     
     if ((a - (counter * d)) == 0) {// remainder zero
      return d;
     } else {
      return findGCD(b, d);
     }
     
     //return findGCD(b, d);
   }
   
   
   public static int findGCDTest(int a, int b) {
      //a = 1071
      //b = 462
      
      // 1071 
      int d = a % b;
      
      if (d == 0) {
         return b;
      } else {
         return findGCDTest(b, d);
      }
      
   }
}