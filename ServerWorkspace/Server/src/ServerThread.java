import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.time.LocalDate; // import the LocalDate class

public class ServerThread extends Thread {

    private Socket myConnection;            // The socket connection to the client
    private ObjectOutputStream out;         // Output stream for sending data to the client
    private ObjectInputStream in;           // Input stream for receiving data from the client
    private String message;                 // Variable to store messages from the client
    private String name, employeeID, email, password, deptName, role, passwordAttempt, emailAttempt, reportType; // Variables to store employee data
    private AccountDB shared; // Shared instance
    private int loginChoice, reportChoice, reportID = 0, reportStatus,assignedID;
    private ReportDB reportDB;
    
    Scanner keyboard = new Scanner(System.in); 
    LocalDate date = LocalDate.now(); // Create a date object
    // Constructor that accepts a socket connection and AccountDB
   
    public ServerThread(Socket s, AccountDB db, ReportDB reportDB) {
        myConnection = s;
        shared = db;
        this.reportDB = reportDB;
        
    }

    // The main logic of the thread that handles client requests
    public void run() {
        try {
            // Initialize the output and input streams for communication
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());
            
            boolean keepRunning = true; // Loop control flag
            // The server is ready to communicate
           while(keepRunning) {
                // Prompt user for operation choice until a valid input is received
                    sendMessage("Please enter 1 to register or 2 to login or 0 to exit");
                    message = (String) in.readObject();
                    
                    if (message.equalsIgnoreCase("0")) {
                        sendMessage("Goodbye!");
                        keepRunning = false; // Exit the loop
                        break;
                    }
                    
                // Enter details if the client selected option 1
                if (message.equalsIgnoreCase("1")) {
                    sendMessage("Please enter your name: ");
                    name = (String) in.readObject();
                   
                    sendMessage("Please enter your employee ID: ");
                    employeeID = (String) in.readObject();
                    
                    sendMessage("Please enter your email: ");
                    email = (String) in.readObject();
                    
                    sendMessage("Please enter your password");
                    password = (String) in.readObject();
                    
                    sendMessage("Please enter department name");
                    deptName = (String) in.readObject();
                    
                    sendMessage("Please enter your role");
                    role = (String) in.readObject();
                    
                    shared.addAccount(name, employeeID, email, password, deptName, role);
                   
                }
                // Login if the client selected option 2
                else if (message.equalsIgnoreCase("2")) {
                	 sendMessage("Please enter your email: ");
                	 emailAttempt = (String) in.readObject();
                	 
                	 sendMessage("Please enter your password");
                	 passwordAttempt = (String) in.readObject();
                	 
                	 // Prints account details to screen 
                	 shared.printAccounts();
               
                	 if (shared.searchAccount(emailAttempt, passwordAttempt)) {
                		    boolean loggedIn = true;

                		    while (loggedIn) {
                		        sendMessage("Main Menu\n"
                		                + "1. Create a health and safety report\n"
                		                + "2. Retrieve all registered accident reports\n"
                		                + "3. Assign health and safety report to employee\n"
                		                + "4. Update your password\n"
                		                + "0. Log out");

                		        message = (String) in.readObject();

                		        switch (message) {
                		            case "1":
                		                createReport(); // Handle report creation
                		                break;
                		            case "2":
                		                sendMessage("Retrieving accident reports...");
                		                // Add logic to retrieve reports
                		                break;
                		            case "3":
                		                sendMessage("Assigning report...");
                		                // Add logic for assigning reports
                		                break;
                		            case "4":
                		                sendMessage("Updating password...");
                		                // Add logic for updating password
                		                break;
                		            case "0":
                		                sendMessage("Logging out...");
                		                loggedIn = false; // Exit the logged-in loop
                		                break;
                		            default:
                		                sendMessage("Invalid choice. Please try again.");
                		        }
                		        }
                		} else {
                		    sendMessage("Login failed. Please check your email and password.");
                		}
                }
           }
        }
            catch (IOException | ClassNotFoundException e) {
            // Handle exceptions during I/O or object read errors
            e.printStackTrace();
        } finally {
            // Close the connection and resources in the end
            try {
                in.close();
                out.close();
                myConnection.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // Helper method to send messages to the client
    void sendMessage(String msg) {
        try {
            out.writeObject(msg);    // Write the message object to output stream
            out.flush();             // Flush the stream to ensure the message is sent
            System.out.println("server> " + msg);   // Print the message on the server console
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    
    private void createReport() throws IOException, ClassNotFoundException {
        sendMessage("1 for accident report or 2 for health and safety risk report");

        message = (String) in.readObject();
        reportChoice = Integer.parseInt(message);
        reportID++;

        if(reportChoice == 1)
        {
        	reportType = "Accident Report";
        }
        else {
        	reportType = "Health and Safety Report";
        }
        sendMessage("Report ID: " + reportID);
        String dateString = date.toString(); // Convert LocalDate to string
        sendMessage("Date: " + dateString);
        sendMessage("Employee ID of creation: " + employeeID);
        sendMessage("Status: Open");
        sendMessage("Assigned Employee ID: ");

        

        reportDB.addReport(reportID, date, employeeID, reportType, "Open", assignedID);

        // Confirm report creation
        sendMessage("Report created successfully!\n" +
                "Report Details:\n" +
                "Type: " + reportType + "\n" +
                "Report ID: " + reportID + "\n" +
                "Date: " + dateString + "\n" +
                "Created By: " + employeeID + "\n" +
                "Assigned To: " + assignedID + "\n" +
                "Status: Open");
        
        sendMessage("Returning to the main menu...");
    }

}
