import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ClientThread client;

    public WriteThread(Socket socket, ClientThread client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Man skriver sit brugernavn, som bliver gemt hos Chatclienten og ender i en do/while
    //der gør man kan skrive beskeder til de andre, medmindre man skriver farvel.
    public void run() {
        Scanner console = new Scanner(System.in);

        System.out.println("Skriv dit brugernavn");
        String userName = console.nextLine();

        client.setUserName(userName);
        writer.println(userName);

        String text;
        System.out.println("Velkommen " + userName);

        do {
            text = console.nextLine();
            writer.println(text);

        } while (!text.equals("farvel"));

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}