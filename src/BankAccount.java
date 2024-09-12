import java.text.DecimalFormat;
 

public abstract class BankAccount {
	
	 
	
	//attributes of BankAccount class
	private String ssn;
	private String accountNum;
	private double balance;
	private String s;
	private String type;
	
	//constructor
	public BankAccount(String num, String ssn, double b, String st, String type)
	{
		this.ssn = ssn;
		balance = b;
		accountNum = num;  
		s = st;
		this.type = type;
		//create the first activity of the statement
		 
		 
	}
	
	//deposit method and withdraw method
	public void deposit(double amount, DataStorage d)
	{
		if(amount > 0.0)
		{
			balance = balance + amount;
            DecimalFormat df = new DecimalFormat("##.00");
            s = s + DateAndTime.DateTime() + ": Deposit " 
                 + df.format(amount) 
                    + ". Balance: $" + df.format(balance) + "\n";
            
            d.updateBalance(accountNum, balance, s);
		}
	}
	
	public void withdraw(double amount, DataStorage d)
	{
		if(balance - amount >= 0.0)  //to check if it has enough money to withdraw
		{
			balance = balance - amount;
            DecimalFormat df = new DecimalFormat("##.00");
            s = s + DateAndTime.DateTime() + ": Withdraw " 
                  + df.format(amount) + ". Balance: $" 
                    + df.format(balance) + "\n";
            
            d.updateBalance(accountNum, balance, s);
		}
	}
	
	public void showStatement()
    {
		System.out.println();
        System.out.printf("Account(%s) Activities:\n", accountNum);
        System.out.println(s);
        System.out.println();
    }
	
	//display statement method, we will come back

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	 
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	 
	

}
