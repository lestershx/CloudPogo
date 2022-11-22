package org.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SocketServer {

  // static ServerSocket variable
  private static ServerSocket server;

  // socket server port on which it will listen
  private static int port = 9876;

  public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
    // create the socket server object
    server = new ServerSocket(port);
    Random random = new Random();

    // keep listens indefinitely until receives 'exit' call or program terminates
    while(true){
      Thread.sleep(random.nextInt(5000));

      System.out.println("Waiting for the client request");

      // creating socket and waiting for client connection
      Socket socket = server.accept();

      // read from socket to ObjectInputStream object
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

      // convert ObjectInputStream object to String
      String message = (String) ois.readObject();
      System.out.println("Message Received Serverside: " + message);
//      int total = Integer.parseInt(message);


      // create ObjectOutputStream object
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

      // write object to Socket
      oos.writeObject("Server Response: "+message);

      // close resources
      ois.close();
      oos.close();
      socket.close();

      // terminate the server if client sends exit request
      if(message.equalsIgnoreCase("exit")) break;
    }
    System.out.println("Shutting down Socket server!!");

    // close the ServerSocket object
    server.close();
  }

}
