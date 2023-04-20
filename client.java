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

    private static long power(int a, int b, int p)
    {
        if (b == 1)
            return a;
        else
            return (((long)Math.pow(a, b)) % p);
    }

    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter= null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try{
            socket = new Socket("localhost",3000);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            Long serverKey = power(primative,prvkey,prime);
            bufferedWriter.write(serverKey.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            if(bufferedReader.readLine() != ""){
            String keyFromServer = bufferedReader.readLine();
            sharedKey = power(Integer.parseInt(keyFromServer),prvkey,prime);
            System.out.println(sharedKey); }

            while (true){
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println(bufferedReader.readLine());
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
