package pubsub_v2;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final List<ClientHandler> publishers = new ArrayList<>();
    private static final List<ClientHandler> subscribers = new CopyOnWriteArrayList<>();

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

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                clientType = in.readLine().trim().toUpperCase();
                out.println("TYPE SOMETHIG:");


                if ("PUBLISHER".equals(clientType)) {
                    publishers.add(this);
                    System.out.println("A new publisher has connected.");
                } else if ("SUBSCRIBER".equals(clientType)) {
                    subscribers.add(this);
                    System.out.println("A new subscriber has connected.");
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
                        for (ClientHandler subscriber : subscribers) {
                            subscriber.sendMessage(inputLine);
                        }
                    }
                    System.out.println(clientType + ": " + inputLine);
                }

                if ("PUBLISHER".equals(clientType)) {
                    publishers.remove(this);
                } else if ("SUBSCRIBER".equals(clientType)) {
                    subscribers.remove(this);
                }

                clientSocket.close();
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            out.println("Publisher: " + message);
        }
    }
}