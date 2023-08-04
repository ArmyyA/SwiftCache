import java.io.*;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheServer {
    private static LRUCache<String, String> cache = new LRUCache<>(1000);

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

    static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                while (true) {
                    String req = inFromClient.readLine();

                    // Just break if client is not there
                    if (req == null) {
                        break;
                    }

                    String[] parts = req.split(":");
                    String action = parts[0];
                    String key = parts[1];

                    if ("get".equals(action)) {
                        String value = cache.get(key);
                        out.println(value);
                    } else if ("put".equals(action)) {
                        String value = parts[2];
                        cache.put(key, value);
                        out.println("OK");
                    } else if ("exit".equals(action)) {
                        break;
                    } else {
                        out.println("Invalid action");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private static final int MAX_ENTRIES = 1000;

        LRUCache(int initialCapacity) {
            super(initialCapacity, 0.75f, true);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > MAX_ENTRIES;
        }
    }
}
