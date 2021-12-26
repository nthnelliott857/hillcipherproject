import java.util.*;
import java.io.*;

public class MatrixTest {
   public static void main(String[] args) throws FileNotFoundException {
      
     //Matrix matrix1 = new Matrix(2, 1, 9);
      //Matrix matrix2 = new Matrix(3, 3, 9);
      
      //Matrix matrix = new Matrix(3, 3, 9, false);
      //System.out.println(matrix);      
      //System.out.println();
      
      Scanner input = new Scanner(new File("taleoftwocities.txt"));
      
      String firstLine = input.nextLine();
      String[] words = firstLine.split("[ ]");
      
      System.out.println(Arrays.toString(words));
      
     
      
      //matrix.transposeSquareMatrix(matrix).print();
      
      //matrix.getCofactorMatrix(); 
      //System.out.println("I \n am \n legend");
           
      //Matrix matrix = new Matrix(3, 3);
      
      /*matrix.set(1, 0, 0);
      matrix.set(8, 0, 1);
      matrix.set(7, 0, 2);
      matrix.set(5, 1, 0);
      matrix.set(21, 1, 1);
      matrix.set(7, 1, 2);
      matrix.set(9, 2, 0);
      matrix.set(13, 2, 1);
      matrix.set(11, 2, 2);*/
      
      /*matrix1.print();
      System.out.println();
      matrix2.print();
      System.out.println();
      
      matrix1.multiply(matrix2).print();*/
      
      
      //matrix.print();
      //System.out.println();
      //System.out.println(matrix1.getDeterminant());
      
      
      
      //matrix.getInverse();
      //System.out.println();
      
      //matrix.multiply(matrix.getInverse()).print();
      //matrix.reduceMatrix(3).print();
      
      //System.out.println();
      
      //System.out.println("The determinant is: " + matrix.getDeterminant());      
      
      
      
      
      
      
      
      
      
      
      
      
      
      /*Matrix matrix = new Matrix(2, 2);
      Matrix matrix1 = new Matrix(2, 2);
      matrix.set(1, 1, 1);
      matrix.set(3, 2, 1);
      matrix.set(2, 1, 2);
      matrix.set(4, 2, 2);// (value, column, row)
      //matrix.print();
      //System.out.println();
      
      matrix1.set(5, 1, 1);
      matrix1.set(6, 1, 2);
      matrix1.set(7, 2, 1);
      matrix1.set(8, 2, 2);// (value, column, row)
      //matrix1.print();
      
      
      
     Matrix matrix3 = matrix.multiply(matrix1);
      
      matrix.print();
      System.out.println(" * ");
      matrix1.print();  
      System.out.println(" = ");
      matrix3.print();*/
   }
}