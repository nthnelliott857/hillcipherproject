import java.util.*;

public class Matrix {
   private int rows;
   private int columns;
   private double[][] elementData;
   int rowCounter;
   int columnCounter;
   
   public Matrix(int rows, int columns, int limit, boolean control) {
      this(rows, columns);
      Random randy = new Random();
         
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            elementData[i][j] = randy.nextInt(limit + 1);
         }
      }
      if (control) {
         while (!isInvertible()) {
            for (int i = 0; i < elementData.length; i++) {
               for (int j = 0; j < elementData[i].length; j++) {
                  elementData[i][j] = randy.nextInt(limit + 1);
               }
            }
         }
      }
   }
   
   public Matrix(int rows, int columns) {
         this.columns = columns; 
         this.rows = rows;
         rowCounter = 0;
         columnCounter = 0;
         elementData = new double[rows][columns];
   }
   
   public void set(double value, int row, int column) {
      elementData[row][column] = value;
   }
   
   public void setAll(double value) {
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            elementData[i][j] = value;
         }
      }
   }
   
   public double get(int row, int column) {
      return elementData[row][column];
   }
   
   public void add(double value) {
      elementData[rowCounter][columnCounter] = value;
      if (columnCounter < (columns - 1)) {
         columnCounter++;
      } else {
         rowCounter++;
         columnCounter = 0;
      }
   }
   
   // control == true for add
   // control == false for subtract
   public Matrix combine(Matrix other, boolean control) {
      if (other.rows != rows || other.columns != columns) {
         throw new IllegalArgumentException("Matrix Dimensions do not match!");  
      }
      
      Matrix newMatrix = new Matrix(rows, columns);  
      
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            if (control) {
               newMatrix.elementData[i][j] = elementData[i][j] + other.elementData[i][j];
            } else {
               newMatrix.elementData[i][j] = elementData[i][j] - other.elementData[i][j];
            }  
         }
      }
      return newMatrix;
   }
   
   public double getDeterminant() {
      if (columns != rows) {
         throw new IllegalArgumentException("Matrix is not square");
      }
   
      double determinant = 0;    
         
      if (columns == 3) {
         determinant += elementData[0][0] * ((elementData[1][1] * elementData[2][2]) - (elementData[1][2] * elementData[2][1]));
         determinant += -1 * (elementData[0][1] * ((elementData[1][0] * elementData[2][2]) - (elementData[1][2] * elementData[2][0])));
         determinant += elementData[0][2] * ((elementData[1][0] * elementData[2][1]) - (elementData[1][1] * elementData[2][0]));
             
      } else if(columns == 2) {
         determinant = (elementData[0][0] * elementData[1][1]) - (elementData[0][1] * elementData[1][0]); 
      } else {
         for (int i = 0; i < elementData.length; i++) {
            if (i % 2 == 0) {
               determinant += elementData[0][i] * reduceMatrix(i).getDeterminant();   
            } else {
               determinant += ((-1 * elementData[0][i]) * (reduceMatrix(i).getDeterminant()));
            }
         }
      }
      return determinant;
   }
   
   // assume row 0
   private Matrix reduceMatrix(int column) {
      Matrix newMatrix = new Matrix(rows - 1, columns - 1);
      
      int v = 0;
         
      for (int i = 1; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            if (j != column) {
               newMatrix.elementData[i - 1][v] = elementData[i][j];
                  if (v < newMatrix.elementData[0].length - 1) {
                     v++;
                  } else {
                     v = 0;
                  }  
            }     
         }   
      }
      return newMatrix;
   }
   
   public Matrix multiply(Matrix other) {
      if (columns != other.rows) {
         throw new IllegalArgumentException();
      }
      
      Matrix otherTransposed = transpose(other);
      Matrix newMatrix = new Matrix(otherTransposed.rows, otherTransposed.columns);
      
      for (int i = 0; i < otherTransposed.elementData.length; i++) {
         for (int j = 0; j < otherTransposed.elementData[i].length; j++) {
            newMatrix.elementData[i][j] = 0;
         }
      }
      
      
      for (int h = 0; h < otherTransposed.elementData.length; h++) {
         for (int i = 0; i < elementData.length; i++) {
            for (int j = 0; j < elementData[i].length; j++) {
               newMatrix.elementData[h][i] += elementData[i][j] * otherTransposed.elementData[h][j];
            }   
         }
      }
      newMatrix = transpose(newMatrix);
      return newMatrix;
   }
   
   public boolean isInvertible() {
      return getDeterminant() != 0;
   }
   
   public Matrix getInverse() {
      if (!isInvertible()) {
         throw new IllegalArgumentException("Determinant is zero; Matrix is not invertible");
      }
      
      //Matrix adjugateMatrix = new Matrix(r *2, c, true);
      Matrix adjugateMatrix = new Matrix(rows, 2 * columns);
      
      boolean control = true;
      
      for (int i = 0; i < adjugateMatrix.elementData.length; i++) {
         for (int j = 0; j < adjugateMatrix.elementData[i].length; j++) {
            if (control) {
               adjugateMatrix.elementData[i][j + i + elementData[i].length] = 1;
               control = false;
            }
         }
         control = true;
      }
      
      for (int p = 0; p < elementData.length; p++) {
         for (int r = 0; r < elementData[p].length; r++) {
            adjugateMatrix.elementData[p][r] = (double)elementData[p][r];  
         
         }
      } 
      
      adjugateMatrix = rowEchelonForm(adjugateMatrix);
      adjugateMatrix = rowReducedEchelonForm(adjugateMatrix);
      
      Matrix inverse = seperateMatrix(adjugateMatrix);
      
      return inverse;
   }
   
   private Matrix rowEchelonForm(Matrix matrix) {
      
      for (int k = 0; k < matrix.elementData.length; k++) {
         double num = matrix.elementData[k][k];
         for (int l = 0; l < matrix.elementData[k].length; l++) {
            matrix.elementData[k][l] = matrix.elementData[k][l] / num;
            
         }
         if (k < matrix.elementData.length - 1) {
            for (int z = k + 1; z < matrix.elementData.length; z++) {
               double num1 = matrix.elementData[z][k];
               for (int m = 0; m < matrix.elementData[k].length; m++) {
                  matrix.elementData[z][m] = matrix.elementData[z][m] - 
                     (num1 * matrix.elementData[k][m]);
               }
            }     
         } 
      }
      return matrix; 
   }
   
   private Matrix rowReducedEchelonForm(Matrix matrix) {
      for (int k = matrix.elementData.length - 1; k > 0; k--) {
         if (k > 0) {
            for (int z = 0; z < k; z++) {
               double num1 = matrix.elementData[z][k];
               for (int m = 0; m < matrix.elementData[k].length; m++) {
                  //if (z != k) { 
                     matrix.elementData[z][m] = matrix.elementData[z][m] - 
                        (num1 * matrix.elementData[k][m]);
                 // }
               }
            }     
         }
         
      }
      return matrix;
   }
   
   private Matrix seperateMatrix(Matrix adjugateMatrix) {
      Matrix matrix = new Matrix(adjugateMatrix.rows, adjugateMatrix.columns / 2);
      
      for (int i = 0; i < adjugateMatrix.elementData.length; i++) {
         for (int j = (adjugateMatrix.elementData[i].length / 2); j < adjugateMatrix.elementData[i].length; j++) {
            double num = adjugateMatrix.elementData[i][j];
            matrix.add(num);     
         }  
      } 
      return matrix;
   }
   
   public Matrix getAdjugateMatrix() {
      Matrix adjugateMatrix = new Matrix(rows, columns);
      if (rows == 2) {
         double a = elementData[0][0];
         adjugateMatrix.set(elementData[1][1], 0, 0);
         adjugateMatrix.set(a, 1, 1);
         adjugateMatrix.set((elementData[0][1] * -1), 0, 1);
         adjugateMatrix.set((elementData[1][0] * -1), 1, 0);
      } else {
         adjugateMatrix = transposeSquareMatrix(getCofactorMatrix());
      }
      return adjugateMatrix;
   }
   
   public Matrix transposeSquareMatrix(Matrix other) {
      if (other.rows != other.columns) {
         throw new IllegalArgumentException();
      }
      
      Matrix transpose = new Matrix(rows, columns);
      Matrix copy = copyMatrix();
      
      for (int i = 0; i < other.elementData.length; i++) {
         for (int j = 0; j < other.elementData[i].length; j++) {
            transpose.set(other.elementData[i][j], j, i);
         }
      }
      return transpose;
   }
   
   private Matrix copyMatrix() {
      Matrix copy = new Matrix(rows, columns);
      
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            copy.set(elementData[i][j], i, j);
         }
      }
      return copy;
   }
   
   
   public Matrix getCofactorMatrix() {
      if (columns != rows) {
         throw new IllegalArgumentException("Matrix is not square");
      }
      
      Matrix cofactorMatrix = new Matrix(rows, columns);
      
       
      boolean sign = true;
         
      for (int i = 0; i < cofactorMatrix.elementData.length; i++ ) {
         for (int j = 0; j < cofactorMatrix.elementData[i].length; j++) {
            Matrix minor = reduceMatrixB(i, j);
               if (sign) {
                  cofactorMatrix.elementData[i][j] = minor.getDeterminant();
                  sign = false;
               } else {
                  cofactorMatrix.elementData[i][j] = minor.getDeterminant() * -1;
                  sign = true;
               }
            }
         }
      return cofactorMatrix;
   }
   
   public Matrix reduceMatrixB(int row, int column) {
      
      Matrix minor = new Matrix(rows - 1, columns - 1);
      if (row == 0) {
         return reduceMatrix(column);
      } else if (row == elementData.length - 1) {
         int v = 0;
            
         for (int i = 0; i < elementData.length - 1; i++) {
            for (int j = 0; j < elementData[i].length; j++) {
               if (j != column) {
                  minor.elementData[i][v] = elementData[i][j];
                  if (v < minor.elementData[0].length - 1) {
                     v++;
                  } else {
                     v = 0;
                  }  
               }     
            }   
         }
         
         return minor;
      } else  {
      
         int c = 0;
         int r = 0;
         for (int i = 0; i < elementData.length; i++) {
            if (row != i) {
               for (int j = 0; j < elementData[i].length; j++) {
                  if (j != column) {
                     minor.elementData[r][c] = elementData[i][j];
                    
                     if (c < minor.elementData[0].length - 1) {
                        c++;
                     } else {
                        r++;
                        c = 0;
                     }
                  }
               }
            }
         } 
         
         return minor;
      } 
   }
   
   
   
   public String toString() {
      String toString = "";
   
      for (int i = 0; i < elementData.length; i++) {
         toString += Arrays.toString(elementData[i]) + "\n";
      }
      return toString;
      
   }
   
   public Matrix modTwentySix() {
      Matrix matrix = new Matrix(rows, columns);
      
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            matrix.elementData[i][j] = correctMod(elementData[i][j], 26.0);
         }
      } 
      return matrix;
   }
   
   public Matrix selectiveModTwentySix() {
      Matrix matrix = new Matrix(rows, columns);
      
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            matrix.elementData[i][j] = correctMod(elementData[i][j], 26.0);
            
         }
      } 
      return matrix;  
   }
   
   public boolean contains(double value) {
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            if (elementData[i][j] == value) {
               return true;
            }
         }
      }
      return false;
   }
   
   public double correctMod(double a, double b) {
      //a mod b = a - b × floor(a/b)
      return a - b * Math.floor(a / b);
   }
   
   public Matrix transpose(Matrix other) {
      
      Matrix transposed = new Matrix(other.columns, other.rows);
      
      for (int i = 0; i < transposed.elementData.length; i++) {
         for (int j = 0; j < transposed.elementData[i].length; j++) {
            if (other.rows == transposed.rows) {
               transposed.elementData[i][j] = other.elementData[i][j];
            } else {
               transposed.elementData[i][j] = other.elementData[j][i];
            }
            
         }   
      }
      
      return transposed;
   }
   
   public void print() {
      for (int a = 0; a < elementData.length; a++) {
         System.out.print("|"); 
         for (int b = 0; b < elementData[a].length; b++) { 
            System.out.print(" " + elementData[a][b]);
         }
         System.out.println("|");  
      }
   }
   
   public Matrix multiplyByNum(double num) {
      Matrix matrix = new Matrix(rows, columns);
      
      for (int i = 0; i < elementData.length; i++) {
         for (int j = 0; j < elementData[i].length; j++) {
            matrix.elementData[i][j] = elementData[i][j] * num;
         }
      }
      return matrix;
   }
   
   public int getRows() {
      return rows;
   }
   
   public int getColumns() {
      return columns;
   }
}