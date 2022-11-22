package org.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientSync {

  private InetAddress host = null;
  private Socket socket = null;

  public SocketClientSync () {
    try{
      host = InetAddress.getLocalHost();
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  public void contactServer (String msg)throws UnknownHostException, IOException,
      ClassNotFoundException, InterruptedException {

    socket = new Socket(host.getHostName(), 9876);

      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      System.out.println("Sending request to Socket Server");


      oos.writeObject(msg);

      // read the server response message
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      String message = (String) ois.readObject();
      System.out.println("Message: " + message);

      // close resources
      ois.close();
      oos.close();

      System.out.format("Tick: %d\n", System.nanoTime());
      Thread.sleep(1000);
//    }
  }

}
