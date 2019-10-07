import java.io.*;
import java.net.*;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ClientThread client;

    public ReadThread(Socket socket, ClientThread client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Læser indkommende beskeder fra de andre brugere og kører, indtil brugeren forlader chatten.
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);
                if (client.getUserName() != null) {
                    System.out.print(client.getUserName() + ": ");
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}