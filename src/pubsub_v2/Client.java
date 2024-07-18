package pubsub_v2;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java MyClient <server ip> <port number> <PUBLISHER/SUBSCRIBER>");
            return;
        }

        String serverIP = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String clientType = args[2].trim().toUpperCase();

        if (!clientType.equals("PUBLISHER") && !clientType.equals("SUBSCRIBER")) {
            System.out.println("Invalid client type. Use PUBLISHER or SUBSCRIBER.");
            return;
        }

        try (
                Socket socket = new Socket(serverIP, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            System.out.println("Connected to server as " + clientType);
            out.println(clientType);

            Thread listener = new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            });
            listener.start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if ("terminate".equalsIgnoreCase(userInput)) {
                    break;
                }
            }

            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverIP);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}