
public class Account {
	private String name, employeeID, email, password, deptName, role;
	// Constructor for account details
	public Account(String n, String id, String e, String p, String d, String r )
	{
		name = n;
		employeeID = id;
		email = e;
		password = p;
		deptName = d;
		role = r;
	}
	
	 // Override toString to format account details
    @Override
    public String toString() {
        return "Name: " + name + ", Employee ID: " + employeeID + ", Email: " + email +
               ", Department: " + deptName + ", Role: " + role;
    }
}