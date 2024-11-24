import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket myConnection;            // The socket connection to the client
    private ObjectOutputStream out;         // Output stream for sending data to the client
    private ObjectInputStream in;           // Input stream for receiving data from the client
    private String message;                 // Variable to store messages from the client
    private int result;                     // Variable to store the result of the operation
    private int num1, num2, num3;           // Variables to store numbers input by the client

    // Constructor that accepts a socket connection
    public ServerThread(Socket s) {
        myConnection = s;
    }

    // The main logic of the thread that handles client requests
    public void run() {
        try {
            // Initialize the output and input streams for communication
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());
            
            // The server is ready to communicate
            do {
                // Prompt user for operation choice until a valid input is received
                do {
                    sendMessage("Please enter 1 to add, 2 to multiply, or 3 to sqrt");
                    message = (String) in.readObject();
                } while (!message.equalsIgnoreCase("1") && 
                         !message.equalsIgnoreCase("2") && 
                         !message.equalsIgnoreCase("3"));
                
                // Process addition if the client selected option 1
                if (message.equalsIgnoreCase("1")) {
                    sendMessage("Please enter number 1");
                    message = (String) in.readObject();
                    num1 = Integer.parseInt(message);    // Parse the first number

                    sendMessage("Please enter number 2");
                    num2 = Integer.parseInt((String) in.readObject());  // Parse the second number

                    result = num1 + num2;                // Perform addition

                    sendMessage("The result is " + result);  // Send result back to client
                }
                // Process multiplication if the client selected option 2
                else if (message.equalsIgnoreCase("2")) {
                    sendMessage("Please enter number 1");
                    message = (String) in.readObject();
                    num1 = Integer.parseInt(message);    // Parse the first number

                    sendMessage("Please enter number 2");
                    num2 = Integer.parseInt((String) in.readObject());  // Parse the second number

                    sendMessage("Please enter number 3");
                    num3 = Integer.parseInt((String) in.readObject());  // Parse the third number

                    result = num1 * num2 * num3;         // Perform multiplication

                    sendMessage("The result is " + result);  // Send result back to client
                }
                // Process square root calculation if the client selected option 3
                else if (message.equalsIgnoreCase("3")) {
                    sendMessage("Please enter number 1");
                    num2 = Integer.parseInt((String) in.readObject());  // Parse the number

                    result = (int) Math.sqrt(num2);       // Perform square root operation

                    sendMessage("The result is " + result);  // Send result back to client
                }
                
                // Ask if the client wants to repeat the operation
                sendMessage("Enter 1 to repeat");
                message = (String) in.readObject();
            } while (message.equalsIgnoreCase("1"));
            
        } catch (IOException | ClassNotFoundException e) {
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
}
