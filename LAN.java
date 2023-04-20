import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LAN {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket=null;

        serverSocket = new ServerSocket(4000);

        while (true){
            try{
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);


                while(true){
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println(msgFromClient);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
