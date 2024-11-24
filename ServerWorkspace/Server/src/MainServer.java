
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String args[]) {
        ServerSocket provider;  // Server socket that listens for incoming connections
        Socket connection;      // Socket for each individual connection to a client
        ServerThread handler;    // Thread to handle each client connection
        AccountDB sharedDB = new AccountDB(); // Shared AccountDB instance

        try {
            // Initialize the server to listen on port 2004 with a maximum queue of 10 connections
            provider = new ServerSocket(2004, 10);

            // Continuously accept new connections from clients
            while (true) {
                connection = provider.accept();   // Wait and accept an incoming client connection

                // Create a new ServerThread to handle the client connection
                handler = new ServerThread(connection, sharedDB);

                // Start the thread to manage client-server communication
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();   // Print stack trace if an IOException occurs
        }
      
    }
}
