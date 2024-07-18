package pubsub_v1;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java MyClient <server ip> <port number>");
            return;
        }

        String serverIP = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket socket = new Socket(serverIP, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String userInput;
            System.out.println("Connected to server. Type 'terminate' to exit.");

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server: " + in.readLine());
                if (userInput.equals("terminate")) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverIP);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}