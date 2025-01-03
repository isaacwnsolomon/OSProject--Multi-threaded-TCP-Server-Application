import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {
	//socket for connecting to server
    Socket requestSocket;
    // output and input stream to communicate with server 
    ObjectOutputStream out;
    ObjectInputStream in;
    Scanner input;
    String message;

    //constructor initialise scanner
    Requester() {
        input = new Scanner(System.in);
    }
    
    // method to start client 
    void run() {
        try {
        	// connect to server on port 2004
            requestSocket = new Socket("127.0.0.1", 2004);
            System.out.println("Connected to localhost in port 2004");

            //initialse input and output streams 
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            // main loop 
            do {
            	//initial message to login or register 
                message = (String) in.readObject();
                System.out.println(message);
                message = input.nextLine();
                sendMessage(message);

             // Registration path
                if (message.equals("1")) {  
                    // Registration header
                    message = (String) in.readObject();
                    System.out.println(message);
                    
                    // Name
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);
                    
                    // Employee ID validation loop
                    boolean validEmployeeID = false;
                    do {
                        message = (String) in.readObject();
                        System.out.println(message);
                        message = input.nextLine();
                        sendMessage(message);
                        
                        message = (String) in.readObject();
                        if (message.startsWith("ERROR:")) {
                            System.out.println(message);
                            validEmployeeID = false;
                        } else {
                            validEmployeeID = true;
                          //  System.out.println("Employee ID accepted");
                        }
                    } while (!validEmployeeID);
                    
                    // Email validation loop
                    boolean validEmail = false;
                    do {
                        message = (String) in.readObject();
                        System.out.println(message);
                        message = input.nextLine();
                        sendMessage(message);
                        
                        message = (String) in.readObject();
                        if (message.startsWith("ERROR:")) {
                            System.out.println(message);
                            validEmail = false;
                        } else {
                            validEmail = true;
                            System.out.println("Email accepted");
                        }
                    } while (!validEmail);
                    
                    // Password
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);
                    
                    // Department
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);
                    
                    // Role
                    message = (String) in.readObject();
                    System.out.println(message);
                    message = input.nextLine();
                    sendMessage(message);
                    
                    // Registration result
                    message = (String) in.readObject();
                    System.out.println(message);
                }
                   // login 
                 else if (message.equals("2")) {
                	 message = (String) in.readObject();
                     System.out.println(message);
                	do {
                        for (int i = 0; i < 2; i++) {
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message);
                        }

                        message = (String) in.readObject();
                        System.out.println(message);
                    } while (message.equals("Login Failed, please try again"));

                    // Main Menu
                    do {
                    	//Welcome message
                    	message = (String) in.readObject();
                        System.out.println(message);
                        //Menu choice
                        message = (String) in.readObject();
                        System.out.println(message);
                        message = input.nextLine();
                        sendMessage(message);
                        
                        if (message.equals("1")) {
                        //Confirming option 1
                        message = (String) in.readObject();
                        System.out.println(message);
                        //Accident or health
                        message = (String) in.readObject();
                        System.out.println(message);
                        message = input.nextLine();
                        sendMessage(message);   
                        //status
                        message = (String) in.readObject();
                        System.out.println(message);
                        message = input.nextLine();
                        sendMessage(message);
                      
                        //Confirmation of all details 
                        message = (String) in.readObject();
                        System.out.println(message);
                        }
                        else if(message.equalsIgnoreCase("2"))
                        {
                        	// Retrieving reports message
                        	message = (String) in.readObject();
                        	System.out.println(message);
                        	//reports
                        	message = (String) in.readObject();
                        	System.out.println(message);
                        }
                        else if(message.equalsIgnoreCase("3")) {
                            // search and update title
                            message = (String) in.readObject();
                            System.out.println(message);
                            // Enter report ID
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message); 
                            // Found report details
                            message = (String) in.readObject();
                            System.out.println(message);
                            // Employee id to assign
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message);
                            // update status prompt
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message); 
                            // Open/closed (if yes was selected)
                            if (message.equalsIgnoreCase("1")) {
                                message = (String) in.readObject();
                                System.out.println(message);
                                message = input.nextLine();
                                sendMessage(message); 
                            }
                            // Confirmed report update
                            message = (String) in.readObject();
                            System.out.println(message);
                        }
                        else if(message.equals("4")) {
                            // Retrieving assigned reports message
                            message = (String) in.readObject();
                            System.out.println(message);
                        }
                        else if(message.equals("5")) {
                            // Change password title
                            message = (String) in.readObject();
                            System.out.println(message);
                            
                            // Current password prompt
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message);
                            
                            // New password prompt
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message);
                            
                            // Confirm password prompt
                            message = (String) in.readObject();
                            System.out.println(message);
                            message = input.nextLine();
                            sendMessage(message);
                            
                            // Result message
                            message = (String) in.readObject();
                            System.out.println(message);
                        }
                    } while (!message.equals("-1"));
                }
            } while (true);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    // method to sennd message to server 
    void sendMessage(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client> " + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
// start client
    public static void main(String args[]) {
        Requester client = new Requester();
        client.run();
    }
}