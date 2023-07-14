import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server {

    // varialbles for client-server connection
    ServerSocket server;
    Socket socket;

    // variables for input and output
    BufferedReader in;
    PrintWriter out;

    private int port = 5000;

    public Server() {
        try {
            // establishing the server
            server = new ServerSocket(port);
            System.out.println("Server established on port: " + port);

            // server tries to accept client
            socket = server.accept();

            // reading the data
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // writing the data
            out = new PrintWriter(socket.getOutputStream());

            reading();
            writing();

        } catch (IOException e) {
            // catches exceptions if any
            e.printStackTrace();
        }

    }

    // for reading messages
    public void reading() {
        // creating runnable variable by using arrow function to implement
        // multithreading for reading
        Runnable r = () -> {
            try {
                while (true) {

                    String msg = in.readLine();

                    System.out.println("Client: " + msg);

                    if (msg.equals("quit") || msg.equals(null)) {
                        System.out.println("Client closed the chat");
                        socket.close();
                        break;
                    }
                }

            } catch (Exception e) {
                // catches exceptions if any
                e.printStackTrace();
            }
        };

        new Thread(r).start();
    }

    // for writing messages
    public void writing() {
        // creating runnable variable by using arrow function to implement
        // multithreading for writing
        Runnable w = () -> {

            try {
                while (!socket.isClosed()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String line = br.readLine();

                    out.println(line);
                    out.flush();

                    if(line.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                }
                System.out.println("Connection is closed");
            } catch (Exception e) {
                // catches exceptions if any
                // e.printStackTrace(); for troubleshooting uncomment it
                System.out.println("Connection is closed");
            }
        };

        new Thread(w).start();
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
