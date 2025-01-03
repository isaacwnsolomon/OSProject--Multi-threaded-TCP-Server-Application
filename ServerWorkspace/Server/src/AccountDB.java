import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AccountDB {
	// store account objects 
	private LinkedList<Account> list;
	//constructor to instialise accountDB
	public AccountDB()
	{
		list = new LinkedList<Account>();
	}
	
	// Add account details to linked list
	public synchronized void addAccount(String name, String employeeID, String email,String password, String deptID, String role) throws IOException
	{
		// creates a new account 
		Account temp = new Account(name,employeeID,email,password,deptID,role);
		//adds the account to th elist 
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
        // read each line from the file and create an account object 
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                // Ensure the line has all parts
                if (parts.length == 6) {
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
    // searches for account by email and password
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
	// retrieves account by email 
	public synchronized Account getAccountByEmail(String email) {
	    for (int i = 0; i < list.size(); i++) {
	    	// Get the account at index i
	        Account account = list.get(i); 
	        if (account.getEmail().equalsIgnoreCase(email)) {
	        	 // Return the matching account
	            return account;
	        }
	    }
	    return null; // No matching account found
	}

	 // Print all accounts in the list to console 
    public synchronized void printAccounts() {
        if (list.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            for (Account account : list) {
                System.out.println(account);
            }
        }
    }
    // updates password of account and saves to file 
    public synchronized boolean updatePassword(String email, String oldPassword, String newPassword) {
        for (Account account : list) {
            if (account.getEmail().equalsIgnoreCase(email) && account.getPassword().equals(oldPassword)) {
                account.setPassword(newPassword);
                saveAccountsToFile();  // Save changes to file
                return true;
            }
        }
        return false;
    }

    // method to save changes to file
    private void saveAccountsToFile() {
        try (FileWriter writer = new FileWriter("AccountDetails.txt")) {
            for (Account account : list) {
                writer.write(account.getName() + "," + account.getEmployeeID() + "," + 
                            account.getEmail() + "," + account.getPassword() + "," + 
                            account.getDeptName() + "," + account.getRole() + 
                            System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // checks if an email exists in the database
    public synchronized boolean emailExists(String email) {
        for (Account account : list) {
            if (account.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    
    // Checks if employeeID exists in file 
    public synchronized boolean employeeIDExists(String employeeID) {
        for (Account account : list) {
            if (account.getEmployeeID().equals(employeeID)) {
                return true;
            }
        }
        return false;
    }
   
}
