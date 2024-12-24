import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class AccountDB {
	
	private LinkedList<Account> list;
	
	public AccountDB()
	{
		list = new LinkedList<Account>();
	}
	
	// Add account details to linked list
	public synchronized void addAccount(String name, String employeeID, String email,String password, String deptID, String role) throws IOException
	{
		Account temp = new Account(name,employeeID,email,password,deptID,role);
		
		list.add(temp);

		  // Save to file, seperated by commas and a new line for each account
        try (FileWriter writer = new FileWriter("AccountDetails.txt", true)) {
            writer.write(name + "," + employeeID + "," + email + "," + password + "," + deptID + "," + role + System.lineSeparator());
        }
		
	}
	
    // Load accounts from file into the list
    public synchronized void loadAccountsFromFile() throws FileNotFoundException {
        File file = new File("AccountDetails.txt");

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("No account details file found.");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 6) { // Ensure the line has all parts
                    String name = parts[0];
                    String employeeID = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String deptID = parts[4];
                    String role = parts[5];

                    // Create Account and add to list
                    Account temp = new Account(name, employeeID, email, password, deptID, role);
                    list.add(temp);
                }
            }
        }
    
	  
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
