import java.net.Socket;
import java.io.*;

public class Client {

    // varialbles for client-server connection
    Socket socket;

    // variables for input and output
    BufferedReader in;
    PrintWriter out;

    public Client() {
        try {
            System.out.println("Requesting server to connect");

            // creating a socket to handle the request
            socket = new Socket("localhost", 5000);
            System.out.println("Connected to the server");

            // reading the message
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // writing the message
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
                    System.out.println("Server: " + msg);
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
                }
                System.out.println("Connection is closed");
            } catch (Exception e) {
                // catches exceptions if any
                //e.printStackTrace(); for troubleshooting uncomment it
                System.out.println("Connection is closed");
            }
        };

        new Thread(w).start();
    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
