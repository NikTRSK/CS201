import java.util.Scanner;

public class Salary {

	
	public static void main (String [] args) {
		Scanner in = new Scanner(System.in);
		System.out.print ("How many employees: ");
		int num_employees = in.nextInt();
		Employee employees[] = new Employee[num_employees];
		
		for (int i = 0; i < num_employees; i++) {
			System.out.print("Please enter the name of employee #" + (i+1) + " ");
			String name = in.next();
			System.out.print("Please enter the hourly rate for employee #" + (i+1) + " ");
			double rate = in.nextDouble();
			employees[i] = new Employee();
			employees[i].setRate(rate);
			employees[i].setName(name);
		}
		
		for (int i = 0; i < num_employees; i++) {
			System.out.println (employees[i].getName() + "'s annual salary is: " + employees[i].getSalary());
		}
		in.close();
	}
}

class Employee {
	private double rate;
	private String name;
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getSalary() {
		return rate * 40 * 50;
	}
}