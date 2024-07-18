package pubsub_v1;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MyServer <port number>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("Client connected. Waiting for messages...");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client: " + inputLine);
                out.println("Server received: " + inputLine);
                if (inputLine.equals("terminate")) {
                    break;
                }
            }

            System.out.println("Client disconnected.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
