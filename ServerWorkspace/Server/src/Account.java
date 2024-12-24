
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
	

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }
    
    //getter for employeeID
    public String getEmployeeID()
    {
    	return employeeID;
    }
    // getter for name 
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPassword(String newPassword) {
        this.password = newPassword;
    }
	
	 // Override toString to format account details
    @Override
    public String toString() {
        return "Name: " + name + ", Employee ID: " + employeeID + ", Email: " + email +
               ", Department: " + deptName + ", Role: " + role;
    }
}
