import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private static final int prvkey = 3;
    private static final int prime = 23;
    private static final int primative = 9;

    private static long sharedKey;

    private static long power(long a, long b, long p) {
        if (b == 1)
            return a;
        else
            return (((long) Math.pow(a, b)) % p);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        while (true) {
            System.out.println("Server Running ");
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getRemoteSocketAddress());

                Thread newThread = new Thread(() -> {
                    try {
                        handleClient(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                newThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void handleClient(Socket socket) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        String keyFromClient = bufferedReader.readLine();
        bufferedWriter.newLine();
        bufferedWriter.flush();

        Long clientKey = power(primative, prvkey, prime);
        bufferedWriter.write(clientKey.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        sharedKey = power(Integer.parseInt(keyFromClient), prvkey, prime);
        System.out.println("Shared key with " + socket.getRemoteSocketAddress() + " = " + sharedKey);

        while (true) {
            String msgFromClient = bufferedReader.readLine();
            System.out.println("Received message from " + socket.getRemoteSocketAddress() + ": " + msgFromClient);
            bufferedWriter.write("received");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }
}
