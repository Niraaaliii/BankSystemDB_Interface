import java.util.Scanner;

public class AccountCreator {
	
	private DataStorage data;
	    
	public AccountCreator(DataStorage d)
	{
		data = d;
	}
	
	 
	public void createAccount(String type)
	{
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter your ssn: ");
		String ssn = input.nextLine();
		System.out.println("Please enter your initial deposit: ");
		double balance = input.nextDouble();
		
		data.createBankAccount(ssn, balance, type);
		
	}
	
	 
}
