
public class ReadCommandLine {
	public static void main (String [] args) {
		if (args.length == 2) {
			String name = args[0];
			double amount = Double.parseDouble(args[1]);
			System.out.println("Name = " + name);
			System.out.println("Amount = " + amount);
		}
	}
}
