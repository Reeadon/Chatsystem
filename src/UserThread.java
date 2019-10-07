import java.io.*;
import java.net.*;

public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    //Modtager brugernavnet og tilføjer det til vores set I chatserveren,
    //derefter broadcaster den den nye bruger og viser hvad de enkelte
    //brugere sender til hinanden.
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "Ny bruger tilføjet " + userName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("farvel"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " forlod chatten.";
            server.broadcast(serverMessage, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Hvis settet ikke er null, så vil de aktive brugere blive skrevet når man opretter forbindelse
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Aktive brugere: " + server.getUserNames());
        } else {
            writer.println("Ingen aktive brugere");
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}