import java.io.*;
import java.net.*;

public class CacheServer {
    public static void main(String[] args) {
        int port = 161;

        // Establish the server socket listening at port 161
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port: " + port);

            while (true) {
                // Accepts when client reaches out to server
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
