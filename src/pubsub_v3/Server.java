package pubsub_v3;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final Map<String, List<ClientHandler>>  topicSubscribers = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MyServer <port number>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server started on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientType;
        private String topic;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

//                out.println("Enter your type (PUBLISHER/SUBSCRIBER):");
                clientType = in.readLine().trim().toUpperCase();

//                out.println("Enter your topic:");
                topic = in.readLine().trim().toUpperCase();

                if ("SUBSCRIBER".equals(clientType)) {
                    topicSubscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(this);
                    System.out.println("A new subscriber has connected on topic: " + topic);
                } else if ("PUBLISHER".equals(clientType)) {
                    System.out.println("A new publisher has connected on topic: " + topic);
                } else {
                    out.println("Invalid type. Disconnecting.");
                    clientSocket.close();
                    return;
                }

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if ("terminate".equalsIgnoreCase(inputLine)) {
                        break;
                    }
                    if ("PUBLISHER".equals(clientType)) {
                        List<ClientHandler> subscribers = topicSubscribers.get(topic);
                        if (subscribers != null) {
                            for (ClientHandler subscriber : subscribers) {
                                subscriber.sendMessage(inputLine);
                            }
                        }
                    }
                    System.out.println(clientType + " [" + topic + "]: " + inputLine);
                }

                if ("SUBSCRIBER".equals(clientType)) {
                    topicSubscribers.getOrDefault(topic, Collections.emptyList()).remove(this);
                }

                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            out.println("Publisher [" + topic + "]: " + message);
        }
    }
}