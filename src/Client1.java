import java.net.*;
import java.io.*;

public class Client1 {
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;

    public Client1(String serverName, int serverPort) {
        System.out.println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            start();
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
        String line = "";
        while (!line.equals("over")) {
            try {
                assert console != null;
                line = console.readLine();
                streamOut.writeUTF(line);
                streamOut.flush();
            } catch (IOException ioe) {
                System.out.println("Sending error: " + ioe.getMessage());
            }
            try {
                DataInputStream streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                String line2 = streamIn.readUTF();
                System.out.println(line2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() throws IOException {
        console = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
    }
}