import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private static final int prvkey = 3;
    private static final int prime = 23;
    private static final int primative = 9;
    private static long sharedKey;

    static InputStreamReader inputStreamReader = null;
    static OutputStreamWriter outputStreamWriter = null;
    static BufferedReader bufferedReader = null;
    static BufferedWriter bufferedWriter = null;

    private static long power(long a, long b, long p) {
        if (b == 1)
            return a;
        else
            return (((long) Math.pow(a, b)) % p);
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);

        while (true) {
            StartConnection(serverSocket);
        }
    }

    private static void StartConnection(ServerSocket serverSocket){
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
    private static void handleClient(Socket socket) throws IOException {
        inputStreamReader = new InputStreamReader(socket.getInputStream());
        outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        bufferedWriter = new BufferedWriter(outputStreamWriter);

        SetKeys();

        ConnectLan();

        while (true) {
            String msgFromClient = bufferedReader.readLine();
            System.out.println("Received message from " + socket.getRemoteSocketAddress() + ": " + msgFromClient);
            bufferedWriter.write("received");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void SetKeys() throws IOException {
        String keyFromClient = bufferedReader.readLine();
        bufferedWriter.newLine();
        bufferedWriter.flush();

        Long clientKey = power(primative, prvkey, prime);
        bufferedWriter.write(clientKey.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        sharedKey = power(Integer.parseInt(keyFromClient), prvkey, prime);
        System.out.println(sharedKey);
    }

    public static void ConnectLan() throws IOException {
        String keyFromClient = bufferedReader.readLine();
        bufferedWriter.newLine();
        bufferedWriter.flush();


    }


}
