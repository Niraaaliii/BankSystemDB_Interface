import java.util.ArrayList;

public interface DataStorage {
	
	void createBankAccount(String ssn, double balance, String type);
    void createOnlineAccount(String ssn, String id, String psw);
    OnlineAccount login(String id, String password);
    //ArrayList<Product> getProductAccounts(String ssn);
    ArrayList<BankAccount> getBankAccounts(String ssn);
    void updateBalance(String accountNumber, double balance, String statement);
    void updateNumWithdraw(String accountNum, int num);
     
}
