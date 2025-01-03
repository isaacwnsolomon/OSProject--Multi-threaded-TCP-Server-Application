import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.time.LocalDate;

public class ServerThread extends Thread {

    private Socket myConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message, email, password, name, deptName, role, login, reportType, incidentStatus, employeeID;
    private int reportChoice, loginChoice, assignedID = 0;
    private AccountDB shared;
    private ReportDB reportDB;
    private LocalDate date = LocalDate.now();

    //Constructor initialise socked and shared db
    public ServerThread(Socket s, AccountDB db, ReportDB reportDB) {
        myConnection = s;
        shared = db;
        this.reportDB = reportDB;
    }

    //Entry point for thread
    public void run() {
        try {
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());

            do {
            	// Reset login status 
                login = "-1";
                do {
                    sendMessage("Enter 1. To Register 2. Login");
                    message = (String) in.readObject();
                    loginChoice = Integer.parseInt(message);
                    // loop until valid 
                } while (loginChoice != 1 && loginChoice != 2);

                // If message equals 1 call handleRegisteration method 
                if (message.equals("1")) {
                    handleRegistration();
                } else if (message.equals("2")) {
                	// Call handleLogin method 
                    handleLogin();
                }
               
                if (login.equals("1")) {
                	// If login successful go to main menu 
                    handleMainMenu();
                }
            } while (true);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }
    // method to send message to client
    private void sendMessage(String msg) {
        try {
        	// write message to output stream
            out.writeObject(msg);
            // ensure message sent immediatly 
            out.flush();
            // log message on server side
            System.out.println("server> " + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // handles registeration for a new user 
    private void handleRegistration() throws IOException, ClassNotFoundException {
        sendMessage("|----> Register <-----|");
        sendMessage("Please Enter your Name");
        name = (String) in.readObject();


        // Get and validate Employee ID
        boolean validEmployeeID = false;
        do {
            sendMessage("Please Enter your Employee ID");
            employeeID = (String) in.readObject();
            // if employee id already in data base show errror message
            if (shared.employeeIDExists(employeeID)) {
                sendMessage("ERROR: This Employee ID is already registered. Please try again.");
            } else {
            	sendMessage("Employee ID accpeted");
                validEmployeeID = true;
            }
          // do while validEmployeeID is not true
        } while (!validEmployeeID);

     // Get and validate Email
        boolean validEmail = false;
        do {
            sendMessage("Please Enter your Email");
           
            email = (String) in.readObject();
        
            if (shared.emailExists(email)) {
                sendMessage("ERROR: This email is already registered. Please try again."); 
            } else {
                validEmail = true;
                sendMessage("VALID");
            }
        } while (!validEmail);

     // Prompt for password
        sendMessage("Please Enter your Password");  
        password = (String) in.readObject();
        
        sendMessage("Please Enter your Department Name");
        deptName = (String) in.readObject();

        sendMessage("Please Enter your Role");
        role = (String) in.readObject();

        // if employeeID or email exists - fail 
        if (shared.searchAccount(email, employeeID)) {
            sendMessage("Registration failed, Email or Employee ID already exists");
        } else {
        	// add details to accountDB
            shared.addAccount(name, employeeID, email, password, deptName, role);
            sendMessage("Registration succeeded");
        }
    }
    // Handles login process
    private void handleLogin() throws IOException, ClassNotFoundException {
        do {
            sendMessage("|----> Login <-----|");
            sendMessage("Please Enter your Email");
            email = (String) in.readObject();

            sendMessage("Please Enter your Password");
            password = (String) in.readObject();

            if (shared.searchAccount(email, password)) {
                sendMessage("Login Successful");
             // Fetch the logged-in user's account
                Account loggedInAccount = shared.getAccountByEmail(email); 
                if (loggedInAccount != null) {
                	// Set the employeeID
                    employeeID = loggedInAccount.getEmployeeID(); 
                }
                // Login successful 
                login = "1";
            } else {
                sendMessage("Login Failed, please try again");
                login = "-1";
            }
        } while (login.equals("-1"));
    }
    
    // Handles main menu 
    private void handleMainMenu() throws IOException, ClassNotFoundException {
        do {
            sendMessage("|------- Welcome to the Main Menu -------|");
            sendMessage("Enter \n1.To create a report\n2. View all accident reports\n3. Search and update a report\n4. View reports assigned to you \n5. Change Password\n-1.Exit");
            message = (String) in.readObject();
            reportChoice = Integer.parseInt(message);

            switch (reportChoice) {
                case 1:
                	// create a new report
                    createReport();
                    break;
                case 2: 
                	// get all accident reports
                	sendMessage("Retrieving accident reports...");
		                String reports = reportDB.getAccidentReports();
		                sendMessage(reports); // Send the formatted report details to the client
		                break;
                case 3:
                	// search, assign and update status of report 
                	searchAndUpdateReport();
                	break;
                case 4: 
                	// view reports assigned to user 
                	viewAssignedReports();
                	break;
                case 5:
                	// change password 
                	changePassword();
                	break;
                case -1:
                	//exits app 
                    sendMessage("Exiting... Goodbye!");
                    break;
                default:
                	// handles invalid input 
                    sendMessage("Invalid choice. Please try again.");
            }
        } while (reportChoice != -1);
    }

    //creates a new report
    private void createReport() throws IOException, ClassNotFoundException {
        sendMessage("Option 1: Create a report");
        do {
        	// option of accident report or health and safety
            sendMessage("Enter\n1.To select Accident Report\n2.To select Health and Safety Risk");
            message = (String) in.readObject();
        } while (!message.equals("1") && !message.equals("2"));
        //sets report type 
        if (message.equals("1")) {
            reportType = "Accident Report";
        } else if (message.equals("2")) {
            reportType = "Health and Safety Report";
        }
       
        // set report status
        sendMessage("Report Status\nEnter\n1.To leave incident open\n2.To close the incident");
        message = (String) in.readObject();
        if (message.equals("1")) {
            incidentStatus = "Open";
        } else if (message.equals("2")) {
            incidentStatus = "Closed";
        }
        //sets unique reportID and adds report to database 
        int generatedID = reportDB.addReport(date, employeeID, reportType, incidentStatus, assignedID);
        
      
        String dateString = date.toString();
      
        // sends confirmed details to client
        sendMessage("Report created successfully!\n" +
                "Report Details:\n" +
                "Type: " + reportType + "\n" +
                "Report ID: " + generatedID + "\n" +
                "Date: " + dateString + "\n" +
                "Created By: " + employeeID + "\n" +
                "Assigned To: Null\n" +
                "Status: " + incidentStatus);
    }
    // search for report and update details 
    private void searchAndUpdateReport() throws IOException, ClassNotFoundException {
        sendMessage("|----> Search and Update Report <-----|");

        
        sendMessage("Enter the Report ID to search:");
        message = (String) in.readObject();
        int reportID = Integer.parseInt(message);

        // Search for the report in the database
        Report report = reportDB.getReportById(reportID);
        if (report == null) {
            sendMessage("No report found with ID: " + reportID);
            return;
        }

        // Display the report details 
        sendMessage("Report Found:\n" + report.toString() + "\n");

       
        boolean validEmployeeID = false;
        int newAssignedID = 0;
        // Assign to another employee
        while (!validEmployeeID) {
            sendMessage("Enter the Employee ID to assign this report to (numeric only):");
            message = (String) in.readObject();
            try {
                newAssignedID = Integer.parseInt(message);
                validEmployeeID = true;
            } catch (NumberFormatException e) {
                sendMessage("Invalid Employee ID. Please enter a numeric value.");
            }
        }

        // Update the report's assigned employee ID
        report.setAssignedID(newAssignedID);

        // Optionally update the status
        sendMessage("Do you want to update the status? (1.yes/2.no):");
        message = (String) in.readObject();

        if (message.equalsIgnoreCase("1")) {
            sendMessage("Enter new status (1.Open/2.Closed):");
            message = (String) in.readObject();
            if (message.equalsIgnoreCase("1")) {
                report.setReportStatus("Open");
            } else if (message.equalsIgnoreCase("2")) {
                report.setReportStatus("Closed");
            } else {
                sendMessage("Invalid status choice. Keeping the existing status.");
            }
        }

        // Save the updated report
        reportDB.updateReport(report);

        // Confirm the update
        sendMessage("Report updated successfully!\n" + report.toString());
    }
    // view reports assigned to current user 
    private void viewAssignedReports() throws IOException {
        try {
            int currentEmployeeID = Integer.parseInt(employeeID);
            // fetch assigned reports 
            String assignedReports = reportDB.getAssignedReports(currentEmployeeID);
            sendMessage(assignedReports);
        } catch (NumberFormatException e) {
            sendMessage("Error: Unable to process employee ID.");
        }
    }
   // change users password
    private void changePassword() throws IOException, ClassNotFoundException {
        sendMessage("|----> Change Password <-----|");
        
        // Get current password for verification
        sendMessage("Please enter your current password:");
        String currentPassword = (String) in.readObject();
        
        
        sendMessage("Please enter your new password:");
        String newPassword = (String) in.readObject();
        
       
        sendMessage("Please confirm your new password:");
        String confirmPassword = (String) in.readObject();
        
        // Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            sendMessage("Password change failed: New passwords do not match");
            return;
        }
        
        //  update the password
        if (shared.updatePassword(email, currentPassword, newPassword)) {
            sendMessage("Password successfully changed!");
        } else {
            sendMessage("Password change failed: Current password is incorrect");
        }
    }
    
    private void closeConnections() {
        try {
            in.close();
            out.close();
            myConnection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}