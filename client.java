import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class client {
    private static final int prvkey = 4;
    private static final int prime = 23;
    private static final int primative = 9;
    private static long sharedKey;

    private static InputStreamReader inputStreamReader = null;
    private static OutputStreamWriter outputStreamWriter= null;
    private static BufferedReader bufferedReader = null;
    private static BufferedWriter bufferedWriter = null;

    private static long power(int a, int b, int p)
    {
        if (b == 1)
            return a;
        else
            return (((long)Math.pow(a, b)) % p);
    }


    public static void main(String[] args) {

        StartConnection("localhost", 3000);

    }

    public static void StartConnection(String host,int port){
        try{
            Socket socket = new Socket(host,port);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            SetKeys();
            ConnectToLAN(scanner);
            while (true){
                SendMessage(scanner.nextLine());
                System.out.println(ReceiveMessage());
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void SendMessage(String msgToSend){
        try{
            bufferedWriter.write(msgToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ConnectToLAN(Scanner scanner){
        String lanInfo = scanner.nextLine();
        SendMessage(lanInfo);
    }
    public static String ReceiveMessage() throws IOException {
        return bufferedReader.readLine();
    }

    public static void SetKeys() throws IOException {
        Long serverKey = power(primative,prvkey,prime);
        bufferedWriter.write(serverKey.toString());
        bufferedWriter.newLine();
        bufferedWriter.flush();

        if(bufferedReader.readLine() != ""){
            String keyFromServer = bufferedReader.readLine();
            sharedKey = power(Integer.parseInt(keyFromServer),prvkey,prime);
            System.out.println(sharedKey);
        }
    }
}
