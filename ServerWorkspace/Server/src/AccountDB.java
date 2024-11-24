import java.util.Iterator;
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
	
	public synchronized boolean searchAccount(String email, String password)
	{
		Iterator i = list.iterator();
		Account temp;
		
		while (i.hasNext())
		{
			temp = (Account)i.next();
			
			//If finds email attempt and password attempt are found, return true 
			if(temp.getEmail().equalsIgnoreCase(email) && temp.getPassword().equalsIgnoreCase(password))
			{
				return true; // If match found
			
			}
			
		}
		return false; // If no match found 
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
