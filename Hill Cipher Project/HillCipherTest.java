import java.util.*;
import java.io.*;

public class HillCipherTest {
   public static final boolean CONTROL = false;
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      
      
      System.out.print("Type the message you want to use as keyWord: ");
      String keyWord = console.nextLine(); 
      HillCipher cipher = new HillCipher(keyWord);
      if (CONTROL) {   
         System.out.print("Type the name of the file you want encrypted: ");
         String fileName = console.nextLine();
         File input = new File(fileName);
         cipher.encryptMessage(input);
      } else {
         System.out.print("Type the message you want encrypted: ");
         String message = console.nextLine();
         cipher.encryptMessage(message);
      }
      cipher.encodingMatrix.print();
      System.out.println();
      System.out.print("Encrypted Message: ");
      File encryptedOutput = new File("encryptedOutput.txt");
      cipher.printEncryptedMessage(true, encryptedOutput);
      System.out.println();
      System.out.print("Decrpyting Message");
      cipher.decryptMessage();
      System.out.println();
      System.out.print("Decrypted Message: ");
      File decryptedOutput = new File("decryptedOutput");
      cipher.printDecryptedMessage(true, decryptedOutput);
   }
}