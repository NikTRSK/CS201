import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BufferedReaderInput {
	public static void main(String [] args) {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			System.out.print ("Enter a number: ");
			String line = br.readLine();
			int num = Integer.parseInt(line);
			System.out.println ("You entered " + num);
		} catch (IOException ioe) {
			System.out.println ("IOException: " + ioe.getMessage());
		}
	}
}