# Task 01
## Client-Server Socket Application

This is a simple client-server socket application implemented in JAVA. The application demonstrates communication between a client and a server over a network using socket programming.

### Requirments
- Server listens on a specified port for incoming connections.
- Client connects to the server using the server's IP address and port.
- Client can send messages to the server, which are displayed on the server's CLI.
- The client can type messages continuously until the keyword `terminate` is entered, upon which the client will disconnect and terminate.


### Server
To start the server, run the following command in your terminal, replacing `<port>` with the desired port number:

```
javac pubsub_v1/Server.java 
java pubsub_v1.Server <port>
```

### Client
To start the client, run the following command in your terminal

```
javac pubsub_v1/Client.java 
java pubsub_v1.Client <server ip> <port number> <PUBLISHER/SUBSCRIBER> <TOPIC>
```

# Task 2
## Client-Server Socket Application

This is an improved client-server socket application implemented in JAVA. The application demonstrates communication between a client and a server over a network using socket programming, with support for multiple concurrent client connections and role-based messaging (Publisher/Subscriber).

## Requirments
- Server handles multiple concurrent client connections.
- Multiple client applications can connect to the server.
- Typed text in a given client CLI is displayed on the Server terminal CLI.
- Client application takes a third command line argument to indicate whether the client will act as either "Publisher" or "Subscriber".
- The server echoes messages sent by any "Publisher" clients to all "Subscriber" clients’ terminals.
- Publisher messages are only shown on Subscriber terminals, not on any Publisher terminals.

### Server
To start the server, run the following commands in your terminal, replacing `<port>` with the desired port number:

```
javac pubsub_v2/Server.java 
java pubsub_v2.Server <port>
```
### Client
To start the client, run the following command in your terminal

```
javac pubsub_v2/Client.java 
java pubsub_v2.Client <server ip> <port number> <PUBLISHER/SUBSCRIBER>
```

# Task 3
## Client-Server Socket Application

This is an improved client-server socket application implemented in JAVA. The application demonstrates communication between a client and a server over a network using socket programming, with support for multiple concurrent client connections, role-based messaging (Publisher/Subscriber), and topic/subject-based filtering of messages.

## Requirements
- Server handles multiple concurrent client connections.
- Multiple client applications can connect to the server.
- Typed text in a given client CLI is displayed on the Server terminal CLI.
- Client application takes a third command line argument to indicate whether the client will act as either "Publisher" or "Subscriber".
- Client application takes a fourth command line argument specifying the topic/subject of either the Publisher or Subscriber.
- The server echoes messages sent by any "Publisher" clients to all "Subscriber" clients’ terminals who are subscribed to the same topic/subject.
- Publisher messages are only shown on Subscriber terminals that are subscribed to the same topic/subject, not on any Publisher terminals.

### Server
To start the server, run the following commands in your terminal, replacing `<port>` with the desired port number:

```
javac pubsub_v3/Server.java 
java pubsub_v3.Server <port>
```

### Client
To start the client, run the following command in your terminal

```
javac pubsub_v3/Client.java 
java pubsub_v3.Client <server ip> <port number> <PUBLISHER/SUBSCRIBER> <Topic>
```