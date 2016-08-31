import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class FileCopy {

	public static void main(String [] args) {
		System.out.print("Enter the input filename: ");
		Scanner scan = new Scanner(System.in);
		String inputFilename = args[0];
//		String outputFilename = "d:\test5.txt";

		try {
			FileReader fr = new FileReader(inputFilename);
			BufferedReader br = new BufferedReader(fr);
//			FileWriter fw = new FileWriter(outputFilename);
//			PrintWriter pw = new PrintWriter(fw);
			String line = br.readLine();
			System.out.println(line);
//			while (line != null) {
//				pw.println(line);
//				line = br.readLine();
//			}
//			pw.close();
//			fw.close();
			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("FileNotFoundException: " + fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("IOException: " + ioe.getMessage());
		}
	}
}
