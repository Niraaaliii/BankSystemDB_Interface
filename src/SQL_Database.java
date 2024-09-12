import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SQL_Database implements DataStorage{
	
	 final String DATABASE_URL = 
             "jdbc:mysql://mis-sql.uhcl.edu/drlin?useSSL=false";
	 final String db_id = "drlin";
	 final String db_psw = "2019drlin";
     
	 Connection connection = null;
	 Statement statement = null;
	 ResultSet resultSet = null;
 
	 @Override
	 public void createBankAccount(String ssn, double balance, String type)
	 {       
        try
        {
             //connect to the database
             connection = DriverManager.getConnection(DATABASE_URL, 
                     "drlin", "2019drlin");
             //create a statement
             statement = connection.createStatement();
             //create the statement String
             DecimalFormat df = new DecimalFormat("##.00");
             String s = DateAndTime.DateTime()+ ": "
                     + "Account Opened with initial balance $"
                     + df.format(balance) + "\n";
             //to get the account number
             resultSet = statement.executeQuery("Select * "
                     + "from nextaccountnumber");
             
             int nextNum = 0;  //local varaible for nextAccountnumber
             String accountNum = "";
             while(resultSet.next())
             {
                 accountNum = "" + resultSet.getInt(1);
                 nextNum = resultSet.getInt(1) + 1; 
             }
             
             //rolled back to here if anything wrong
             connection.setAutoCommit(false);
             //update the new nextAccountNum;
             int t = statement.executeUpdate("Update nextAccountNumber set "
                     + "nextID = '" + nextNum + "'");
             //insert a record into bankAccount Table
             int numWithdraw = 0;
             int r = statement.executeUpdate("insert into BankAccount values "
                     + "('" + accountNum + "', '" + ssn + "', '" 
                     + balance + "', '" + s + "', '" + type + "', '" + numWithdraw + "')");
             
             connection.commit();
             connection.setAutoCommit(true);
             
              //display msg
            System.out.println("***The new bank account is created!***");
            System.out.println("***The account number is " +  accountNum 
                    + "!***");
            System.out.println();
             
         }
         catch(SQLException e)
         {
             //handle the exceptions
             System.out.println("Account creation failed");
             e.printStackTrace();
         }
         finally
         {
             //close the database
             try
             {
                 resultSet.close();
                 statement.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
	 }
 
	 @Override
	 public void createOnlineAccount(String ssn, String id, String psw)
	 {
	     
		try
        {
            
            //connect to the databse
            connection = DriverManager.getConnection(DATABASE_URL, 
                    "drlin", "2019drlin");
            connection.setAutoCommit(false);
            //crate the statement
            statement = connection.createStatement();
            
            //do a query
            resultSet = statement.executeQuery("Select * from onlineAccount "
                    + "where id = '" + id + "' or ssn = '"
                    + ssn + "'");
            
            if(resultSet.next())
            {
                //either the ssn is used or the id is used
                System.out.println("Account creation failed");
            }
            else
            {
                //insert a record into onlineAccount
            	int r = statement.executeUpdate("insert into onlineAccount values"
                        + "('" + id + "', '" + ssn + "', '"
                        + psw +"')");
                System.out.println("Account creation successful!");
                System.out.println();
            }
            connection.commit();
            connection.setAutoCommit(true);
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
             //close the database
             try
             {
                 resultSet.close();
                 statement.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
        }
	     
	 }
 
	 @Override
	 public OnlineAccount login(String id, String password)
	 {
		try
        {
            
            //connect to the databse
			connection = DriverManager.getConnection(DATABASE_URL, 
                  "drlin", "2019drlin");
            //create statement
            statement = connection.createStatement();
            //search the accountID in the onlineAccount table
            resultSet = statement.executeQuery("Select * from onlineAccount "
                    + "where id = '" + id + "'");
            
            if(resultSet.next())
            {
                //the id is found, check the password
                if(password.equals(resultSet.getString(3)))
                {
                    //password is good
                    return new OnlineAccount(resultSet.getString(2), resultSet.getString(1), resultSet.getString(3));
                }
                else
                {
                    //password is not correct
                    return null;
                }
            }
            else
            {
            	return null;
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            //close the database
            try
            {
                connection.close();
                statement.close();
                resultSet.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
	 }
 
	 @Override 
	 public ArrayList<BankAccount> getBankAccounts(String ssn)
	 {
	     try
	     {
	         //connect to the databse
	         connection = DriverManager.getConnection(DATABASE_URL, 
	               db_id, db_psw);
	         //create statement
	         statement = connection.createStatement();
	         //search the accountID in the onlineAccount table
	         resultSet = statement.executeQuery("Select * from bankAccount "
	                 + "where ssn = '" + ssn + "'");
	         
	         ArrayList<BankAccount> aList = new ArrayList<BankAccount>();
	         
	         while(resultSet.next())
	         {
	        	 if(resultSet.getString("type").equals("Checking"))
	        	 {
	        		 BankAccount anAccount = new CheckingAccount(resultSet.getString(1), 
	        	     resultSet.getString(2), resultSet.getDouble(3), 
	        	     resultSet.getString(4), resultSet.getString(5));
	        	     aList.add(anAccount);
	        	 }
	        	 
	        	 if(resultSet.getString("type").equals("Savings"))
	        	 {
	        		 BankAccount anAccount = new SavingsAccount(resultSet.getString(1), 
	        	     resultSet.getString(2), resultSet.getDouble(3), 
	        	     resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
	        	     aList.add(anAccount);
	        	 }
	              
	         }
	         return aList;
	         
	     }
	     catch (SQLException e)
	     {
	         e.printStackTrace();
	         return null;
	     }
	     finally
	     {
	         //close the database
	         try
	         {
	             connection.close();
	             statement.close();
	             resultSet.close();
	         }
	         catch(Exception e)
	         {
	             e.printStackTrace();
	         }
	         
	     }
	 }
 
	 @Override 
	 public void updateBalance(String accountNumber, double balance, 
	         String stm)
	 {
	         
	     try
	     {
	         //connect to the database
	         connection = DriverManager.getConnection(DATABASE_URL, 
	                 db_id, db_psw);
	         connection.setAutoCommit(false);
	         //create the statement
	         statement = connection.createStatement();
	         //update the balance
	         int r = statement.executeUpdate("Update bankAccount set "
	                 + "balance = '" + balance + "' "
	                 + "where accountNumber = '"
	                 + accountNumber + "'");
	         //update the activity
	         r = statement.executeUpdate("Update bankAccount set "
	              + "statement = '" + stm
	                 + "' where accountNumber = '"
	                 + accountNumber + "'");
	         connection.commit();
	         connection.setAutoCommit(true);
	
	
	     }
	     catch (SQLException e)
	     {
	         //handle the exception
	         e.printStackTrace();
	     }
	     finally
	     {
	         //close the database
	         try
	         {
	             statement.close();
	             connection.close();
	         }
	         catch(Exception e)
	         {
	             e.printStackTrace();
	         }
	     }
	 }
 
	 
	 @Override 
	 public void updateNumWithdraw(String accountNum, int num)
	 {
	         
	     try
	     {
	         //connect to the database
	         connection = DriverManager.getConnection(DATABASE_URL, 
	                 db_id, db_psw);
	         connection.setAutoCommit(false);
	         //create the statement
	         statement = connection.createStatement();
	         //update the balance
	         int r = statement.executeUpdate("Update bankAccount set "
	                 + "numWithdraw = '" + num + "' "
	                 + "where accountNumber = '"
	                 + accountNum + "'");
	         connection.commit();
	         connection.setAutoCommit(true);
	
	
	     }
	     catch (SQLException e)
	     {
	         //handle the exception
	         e.printStackTrace();
	     }
	     finally
	     {
	         //close the database
	         try
	         {
	             statement.close();
	             connection.close();
	         }
	         catch(Exception e)
	         {
	             e.printStackTrace();
	         }
	     }
	 }
 

}
