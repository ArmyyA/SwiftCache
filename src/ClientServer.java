import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientServer {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 161;

        // Create socket for client and ping server address
        try (Socket socket = new Socket(serverAddress, serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("Select an action:");
                System.out.println("1. Put");
                System.out.println("2. Get");
                System.out.println("0. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter key:");
                        String key = scanner.nextLine();
                        System.out.println("Enter value:");
                        String value = scanner.nextLine();
                        out.println("put:" + key + ":" + value);
                        String response = in.readLine();
                        System.out.println("Server response: " + response);
                        break;
                    case 2:
                        System.out.println("Enter key:");
                        key = scanner.nextLine();
                        out.println("get:" + key);
                        String getValue = in.readLine();
                        System.out.println("Value: " + getValue);
                        break;
                    case 3:
                        out.println("exit");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
