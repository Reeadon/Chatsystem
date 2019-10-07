import java.net.*;
import java.io.*;

public class ClientThread {

    //Fields
    private String hostname;
    private int port;
    private String userName;

    public ClientThread(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    //Denne metode sørger for at oprette forbindelse til chatserveren
    //samt opretter en read thread og en write thread for den enkelte chatter,

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Forbindelse til server oprettet...");
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();


            //Exeption handlers

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }

    public static void main(String[] args) {
        ClientThread client = new ClientThread("Localhost", 5555);
        client.execute();
    }
}