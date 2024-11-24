import java.util.LinkedList;

public class AccountDB {
	
	private LinkedList<Account> list;
	
	public AccountDB()
	{
		list = new LinkedList<Account>();
	}
	
	// Add account details to linked list
	public synchronized void addAccount(String name, String employeeID, String email,String password, String deptID, String role)
	{
		Account temp = new Account(name,employeeID,email,password,deptID,role);
		
		list.add(temp);
		
	}
	 // Print all accounts in the list
    public synchronized void printAccounts() {
        if (list.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            for (Account account : list) {
                System.out.println(account);
            }
        }
    }
}