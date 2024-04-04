package compiler_project;
import java.util.Formatter;
import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.io.File;  
import java.io.IOException;
public class FileReader {
	public static void main(String[] args) {
		
	}
	
	public String FileReader() {
		// Enter data using BufferReader
		Scanner console = new Scanner(System.in);

		System.out.print("input your file: ");
		// Reading data using nextLine
		String name = console.nextLine();
		String string = "";

		try {
			File myObj = new File(name); // accessing the file given by the user
			// test code follows to print the whole file
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				string = string.concat(myReader.nextLine());
			}
			myReader.close();
			// If no file is found print an error
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		console.close();
		return string;
	}

}