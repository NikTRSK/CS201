import java.util.Scanner;

public class ReadInput {
	public static void main(String [] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter your name: ");
		String name = in.nextLine();
		System.out.print("Enter the amount: ");
		double amount = in.nextDouble();
		in.close();
		System.out.println("Name = " + name);
		System.out.println("Amount = " + amount);
	}
}
