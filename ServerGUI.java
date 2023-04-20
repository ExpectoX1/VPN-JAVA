import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUI extends JFrame {
    private JTextArea logTextArea;

    public ServerGUI() {
        setTitle("Server");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // UI components
        JPanel mainPanel = new JPanel(new BorderLayout());
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);

        // Start server in a separate thread
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    log("New client connected: " + socket.getRemoteSocketAddress());

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) throws IOException {
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
        log("Shared key with " + socket.getRemoteSocketAddress() + " = " + sharedKey);

        while (true) {
            String msgFromClient = bufferedReader.readLine();
            log("Received message from " + socket.getRemoteSocketAddress() + ": " + msgFromClient);
            bufferedWriter.write("received");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    private void log(String message) {
        logTextArea.append(message + "\n");
    }

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

    public static void main(String[] args) {
        new ServerGUI();
    }
}
