import java.util.Scanner;

public class BankSystem {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 
		//main menu
		Scanner input = new Scanner(System.in);
		String selection = "";
		DataStorage data = new SQL_Database();
		
		while(!selection.equals("x"))  //while not x, keep displaying the menu
		{
			//display the menu
			System.out.println();
			System.out.println("Please make your selection: ");
			System.out.println("1: Open a new checking account");
			System.out.println("2: Open a new savings account");
			System.out.println("3: Go to online system");
			System.out.println("x: Finish");
			
			//get the selection from the user
			selection = input.nextLine();
			System.out.println();
			
			//based on the input, go to different function
			if(selection.equals("1"))
			{
				//open a new checking 
				new AccountCreator(data).createAccount("Checking");
			}
			else if(selection.equals("2"))
			{
				new AccountCreator(data).createAccount("Savings");
			}
			else if(selection.equals("3"))
			{
				//online system
				new OnlineSystem(data).showMainPage();
			}
			 	 
		}

	}

}
