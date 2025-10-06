package assignment;
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);

        new ReadThread(socket).start();
        new WriteThread(socket).start();
    }

    static class ReadThread extends Thread {
        private BufferedReader in;

        public ReadThread(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message: " + message);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }
    }

    static class WriteThread extends Thread {
        private PrintWriter out;
        private BufferedReader keyboard;

        public WriteThread(Socket socket) throws IOException {
            out = new PrintWriter(socket.getOutputStream(), true);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            try {
                String text;
                while ((text = keyboard.readLine()) != null) {
                    out.println(text);
                }
            } catch (IOException e) {
                System.out.println("Error writing to server.");
            }
        }
    }
}
