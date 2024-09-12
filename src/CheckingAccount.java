 
import java.text.DecimalFormat;

public class CheckingAccount extends BankAccount {
	
	private double checksPrice = 10.0;
	
	public CheckingAccount(String n, String s, double b, String st, String type)
	{
		super(n, s, b, st, type); //explicitly call the super class constructor

	}

	public double getChecksPrice() {
		return checksPrice;
	}

	public void setChecksPrice(double checksPrice) {
		this.checksPrice = checksPrice;
	}
	
	//orderChecks method
	public void orderChecks(DataStorage d)
	{
		System.out.println("A book of checks will be sent to your address within 3 business days");
		System.out.println();
		double newBalance = super.getBalance() - checksPrice;  //charge for the checks'
		super.setBalance(newBalance);
		//update the statement
		DecimalFormat df = new DecimalFormat("##.00");
		String stm = super.getS() + DateAndTime.DateTime() + ": $" + df.format(checksPrice) 
		+ " is charged for ordering new checks. New Balance: $" + df.format(super.getBalance()) + "\n";
		super.setS(stm);
		
		d.updateBalance(super.getAccountNum(), super.getBalance(), super.getS());
		
	}
	

}
