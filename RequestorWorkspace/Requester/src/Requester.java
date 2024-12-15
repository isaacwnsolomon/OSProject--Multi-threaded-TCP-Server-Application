import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {
    
    Socket requestSocket;                   // Socket to connect to the server
    ObjectOutputStream out;                 // Stream to send data to the server
    ObjectInputStream in;                   // Stream to receive data from the server
    String message;                         // Variable to hold messages exchanged
    Scanner input;                          // Scanner to capture user input

    // Constructor initializing the Scanner
    Requester() {
        input = new Scanner(System.in);
    }

    // Main method to establish the client connection and manage communication
    void run() {
        try {
            // 1. Creating a socket to connect to the server on localhost at port 2004
            requestSocket = new Socket("127.0.0.1", 2004);
            System.out.println("Connected to localhost in port 2004");

            // 2. Initialize output and input streams for communication
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            // 3. Communicate with the server in a loop
            do {
                // Prompt user to select an option until they enter a valid one
                do {
                    message = (String) in.readObject(); // Read message from server
                    System.out.println(message);         // Display server message to user
                    message = input.nextLine();          // Get user input
                    sendMessage(message);                // Send user input back to server
                } while (!message.equalsIgnoreCase("1") && 
                         !message.equalsIgnoreCase("2"));
                
                // If user selected option 1 (Register)
                if (message.equalsIgnoreCase("1")) {
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send name

                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send employee ID
                    
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send email
                    
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send employee password
                    
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send dept name
                    
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send employee role

                   
                }
                // If user selected option 2 (Login)
                else if (message.equalsIgnoreCase("2")) {
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send email

                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);               // Send password
                    
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    System.out.println("Client entered: " + message); 
                    System.out.println("Sending choice to server: " + message);
                    sendMessage(message);               // Send choice of reports
                    
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message); 
                    System.out.println("Sent report choice to server: " + message); // Debug confirmation
                    
                    message = (String) in.readObject();
                    System.out.println(message); //report id 
                    
                    message = (String) in.readObject();
                    System.out.println(message); // date
                  
                    message = (String) in.readObject();
                    System.out.println(message); //EmployeeID
                      
                    message = (String) in.readObject();
                    System.out.println(message); // status of report 
                  
                    message = (String) in.readObject();
                    System.out.println(message); // "Assigned Employee ID:"
                    message = input.nextLine();  // Capture user input for assigned employee ID
                    sendMessage(message);        // Send assigned employee ID back to server

                    
                    
                }

                // Prompt if user wants to repeat the operation
                message = (String) in.readObject();
                System.out.println(message);
                message = input.nextLine();
                sendMessage(message);

            } while (message.equalsIgnoreCase("1"));    // Loop if user wants to repeat operation

        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 4. Closing the connection and streams
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // Helper method to send messages to the server
    void sendMessage(String msg) {
        try {
            out.writeObject(msg);          // Write message object to output stream
            out.flush();                   // Flush the stream to send the message
            System.out.println("client> " + msg);   // Print the message for client’s reference
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Main method to create and run the client
    public static void main(String args[]) {
        Requester client = new Requester();  // Create a new client instance
        client.run();                        // Run the client’s main process
    }
}
