import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class server {
    private static final int prvkey = 3;
    private static final int prime = 23;
    private static final int primative = 9;

    private static long sharedKey;

    private static long power(long a, long b, long p)
    {
        if (b == 1)
            return a;
        else
            return (((long)Math.pow(a, b)) % p);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket=null;

        serverSocket = new ServerSocket(3000);

        while (true){
            try{
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                String keyFromClient = bufferedReader.readLine();
                bufferedWriter.newLine();
                bufferedWriter.flush();

                Long clientKey = power(primative,prvkey,prime);
                bufferedWriter.write(clientKey.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();

                sharedKey = power(Integer.parseInt(keyFromClient),prvkey,prime);
                System.out.println(sharedKey);


                while(true){
                    String msgFromClient = bufferedReader.readLine();
                    bufferedWriter.write("received");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
