import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Demo {
    public static void main(String[] args) throws IOException {
        ServerSocket  server =new ServerSocket(8080);
        while (true){
            Socket socket = server.accept();
            InputStream in =socket.getInputStream();
            byte[] bytes = new byte[1024];
            int count;
            count =in.read(bytes);
            String req =new String(bytes,0,count);
            System.out.println(req);
            socket.close();
        }
    }
}
