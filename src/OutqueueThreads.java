import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class OutqueueThreads {

    private Socket socket;
    private String nickname;
    private Scanner input;

    LinkedBlockingQueue<byte[]> outQueue = new LinkedBlockingQueue<>();

    public OutqueueThreads(Socket s, String name, Scanner scan) {
        this.socket = s;
        this.nickname = name;
        this.input = scan;
    }

    public void StartOutputThreads() throws IOException {

        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        Thread outputThread1 = new Thread(() -> {

            JOIN_PDU tempJoin = new JOIN_PDU((byte) 12, nickname);
            outQueue.add(tempJoin.Serialize());

            while (true) {
                String mess = input.nextLine();

                if (mess.equals("quit") || mess.equals("QUIT")) {
                    QUIT_PDU tempQuit = new QUIT_PDU((byte) 11);
                    outQueue.add(tempQuit.Serialize());
                }

                MESS_PDU message = new MESS_PDU((byte) 10, mess, nickname);
                outQueue.add(message.Serialize());
            }

        });
        outputThread1.start();

        Thread outputThread2 = new Thread(() -> {

            while (true) {
                try {
                    byte[] tempTransfer = outQueue.take();
                    outputStream.write(tempTransfer, 0, tempTransfer.length);
                    outputStream.flush();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        });
        outputThread2.start();
    }
}
