import java.util.Scanner;

public class OnlineSystem {
	
private OnlineAccount theLoginAccount;

    DataStorage data; 

    public OnlineSystem(DataStorage d)
    {
        //when the system is created, no login account yet
    	data = d;
        theLoginAccount = null;
    }
    
    public void showMainPage()
    {
        Scanner input = new Scanner(System.in);
        String selection = "";
        
        while(!selection.equals("x"))
        {
            //menu of the online system
            System.out.println();
            System.out.println("Please make your selection");
            System.out.println("1: create a new online ID");
            System.out.println("2: Login your online account");
            System.out.println("x: Leave the online account system");
            System.out.println();
            
            //get input from user
            selection = input.next();
            
            if(selection.equals("1"))
            {
                //register right here
                register();
            }
            else if(selection.equals("2"))
            {
                //login right here
            	login();
                 
            }
            else
            {
                ;
            }
            
            
        }
        
    }
    
    public void register()
    {
        Scanner input = new Scanner(System.in);
        //get the SSN, id and password
        String ssn, accountID, password;
        System.out.println("Please enter your ssn");
        ssn = input.next();
        System.out.println("Please enter your login ID");
        accountID = input.next();
        System.out.println("Please enter your new password");
        password = input.next();
        
        data.createOnlineAccount(ssn, accountID, password); 
        
    }
    
    public void login()
    {
        //we need id and password
        Scanner input = new Scanner(System.in);
        String id="";
        String password="";
         
        
        //get the login info.
        System.out.println("Please enter your ID");
        id = input.next();
        System.out.println("Please enter your password");
        password = input.next();
        
        theLoginAccount = data.login(id, password);
        if(theLoginAccount != null)
        {
            theLoginAccount.setData(data);
            theLoginAccount.welcome();
        }
        else
        {
            System.out.println("The login failed");
            System.out.println();
        }
         
    }

}
