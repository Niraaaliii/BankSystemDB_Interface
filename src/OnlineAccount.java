import java.util.ArrayList;
import java.util.Scanner;

public class OnlineAccount {
	
	//attributes
    private String ssn;
    private String id;
    private String psw;
    private DataStorage data;
    
    //an arraylist of the bank accounts related to this online account
    private ArrayList<BankAccount> managedAccounts = new ArrayList<BankAccount>();
    //constructor
    public OnlineAccount(String s, String d, String p)
    {   
        ssn = s;
        id = d;
        psw = p;
        
    }
    
    public void welcome()
    {
         
    	System.out.println();
        System.out.println("Hello " + id + 
                ", welcome to your online account");
        
        
        Scanner input = new Scanner(System.in);
        String selection = "";
        
        managedAccounts = data.getBankAccounts(ssn);
        
         
        while(!selection.equals("x"))
        {
            //welcome msg
            System.out.println("\n***Welcome to your online account***");
            //options
            System.out.println("Please select your options");
            //first of all, display all bank accounts numbers
             
            for(int i=0; i<managedAccounts.size(); i++)
            {
                System.out.printf("%s: select %s Account %s to view\n", i+1, managedAccounts.get(i).getType(),
                		managedAccounts.get(i).getAccountNum());
            }
            //other options
            System.out.println("t: Account Transfer");
            System.out.println("c: Order more checks");
            System.out.println("x: sign out\n");
            
            //after display the menu, we ask the user to input selection
            selection = input.next();
            
            if(selection.equals("c"))
            {
            	orderChecks();
            }
            else if(selection.equals("t"))
            {
                //account transfer
                transfer();
            }
            else if(isInteger(selection))
            {
                //we convert the selection into an integer
                int intSelection = Integer.parseInt(selection);
                //make sure the selection is in a good range
                if(intSelection >= 1 && 
                        intSelection<=managedAccounts.size())
                {
                    //now view the statement
                	managedAccounts.get(intSelection-1).showStatement();
                }
             
            }
        }
        
    }
    
    public void orderChecks()
    {
    	ArrayList<CheckingAccount> checkingAccounts = new ArrayList<CheckingAccount>();
    	Scanner input = new Scanner(System.in);
    	
    	for(BankAccount b: managedAccounts)
    	{
    		if(b instanceof CheckingAccount)
    		{
    			checkingAccounts.add((CheckingAccount)b);
    		}
    	}
    		
		if(checkingAccounts.size()>0)
		{
			for(int i=0; i<checkingAccounts.size(); i++)
            {
                System.out.printf("%s: select Account %s\n", i+1,
                        checkingAccounts.get(i).getAccountNum());
            }
            System.out.println();
            
            String selection = input.nextLine();
            
            if(isInteger(selection))
            {
            	int intSelection = Integer.parseInt(selection);
            	if(intSelection >=1 && intSelection <= checkingAccounts.size())
            	{
            		checkingAccounts.get(intSelection-1).orderChecks(data);
            	}
            }
		}
    	
    }
    
    public void transfer()
    {
        //varaibles
        Scanner input = new Scanner(System.in);
        String accountFrom, accountTo;
        double transferAmount;
        
        if(managedAccounts.size()>= 2)
        {
            //list the bank accounts
            for(int i=0; i<managedAccounts.size(); i++)
            {
                System.out.printf("%s: select Account %s\n", i+1,
                        managedAccounts.get(i).getAccountNum());
            }
            System.out.println();
            
            //get the input 
            System.out.println("Please select the account from");
            accountFrom = input.next();
            
            System.out.println("Please select the account to");
            accountTo = input.next();
            
            System.out.println("Please indicate the amount of transfer");
            transferAmount = input.nextDouble();
            
            if(isInteger(accountFrom) && isInteger(accountTo))
            {
                int intFrom = Integer.parseInt(accountFrom);
                int intTo = Integer.parseInt(accountTo);
                
                //validate the input
                if(intFrom >=1 && intFrom <= managedAccounts.size() 
                        && intTo >=1 && intTo <= managedAccounts.size()
                        && intFrom != intTo)
                {
                    if(managedAccounts.get(intFrom - 1).getBalance() 
                            >= transferAmount)
                    {
                   managedAccounts.get(intFrom - 1).withdraw(transferAmount, data);
                    managedAccounts.get(intTo - 1).deposit(transferAmount, data);
                        System.out.println("The transfer is successful!");
                        System.out.println();
                    }
                    else
                    {
                        System.out.println("No enough money");
                    }
                    
                }
                else
                {
                    System.out.println("Your input is not valid!");
                }
            }
            else
            {
                System.out.println("Your input is not valid!");
            }
            
        }
        else
        {
            System.out.println("You must have two bank "
                    + "accounts to do the transfer!");
        }
        
    }
    
    private boolean isInteger(String a)
    {
        try
        {
            int i = Integer.parseInt(a);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    

    //get methods and set methods
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

	public DataStorage getData() {
		return data;
	}

	public void setData(DataStorage data) {
		this.data = data;
	}
	
	

}
