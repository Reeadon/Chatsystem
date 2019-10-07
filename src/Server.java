import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

//Fields
//Hashsets bruges for at undgå duplikater i listerne

    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

//Konstruktør

    public Server(int port) {
        this.port = port;
    }

    // Denne metode gør at  chatserveren startes op og lytter på om der er nogle der vil koble på.
    // Er der det vil de blive tilføjet til UserThreaden

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Vores server lytter på port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
                System.out.println("Ny bruger tilføjet...");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5555);
        server.execute();
    }

    //Denne metode sørger for at sende en chatbrugers besked ud til alle

    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    //Denne metode tilføjer brugerens username til listen

    void addUserName(String userName) {
        userNames.add(userName);
    }

    //Denne metode tjekker om brugeren forlader chatten

    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("Vores bruger " + userName + " forlod chatten.");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }
}