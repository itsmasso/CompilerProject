import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Filereader {
  public static void main(String[] args) {
            // Enter data using BufferReader
        Scanner console = new Scanner(System.in);

        System.out.print("input your file: ");
        // Reading data using nextLine
        String name = console.nextLine();
    try {
      File myObj = new File(name); //accessing the file given by the user
      //test code follows to print the whole file
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();
      //If no file is found print an error
    } catch (FileNotFoundException e) { 
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    console.close();
  }
}