import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A chat client that connects to a server and is able to chat by sending
 * and receiving PDUs through a socket
 */
public class ChatClient {

    public static void main(String[] args) throws IOException {

        String nickname = args[0];
        String server = args[1];
        InetAddress host = InetAddress.getByName(args[2]);
        int port = Integer.parseInt(args[3]);

        /*If the user wants to connect to name server, create a specific socket for the NS and close
        it when the user has hade his/her choice
        */
        if (server.equals("ns") || server.equals("NS")) {

            Socket nameServerSocket = new Socket(host, port);
            DataInputStream datain = new DataInputStream(nameServerSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(nameServerSocket.getOutputStream());

            byte getOP = 3;
            GETLIST_PDU getList = new GETLIST_PDU(getOP);

            output.write(getList.Serialize());
            byte datainOPcode = datain.readByte();

            if(datainOPcode != 4) {
                System.out.println("Incorrect OP-code, exiting program...");
                nameServerSocket.close();
                datain.close();
                output.close();
                System.exit(-1);
            }

            SLIST_PDU slist = new SLIST_PDU(datainOPcode);

            slist.Deserialize(datain);

            System.out.println("Current list of available servers:");
            for(int i = 0; i < slist.serverList.size() ; i++){
               System.out.println(i+1 + ". " + slist.getServerList().get(i).getServerName() + " PORT: " +
                       slist.getServerList().get(i).getPort() + " ACTIVE CLIENTS: " + slist.getServerList().get(i).getClients());
            }
            System.out.println("ENTER NUMBER OF SERVER YOU WANT TO CONNECT TO: ");

            Scanner in = new Scanner(System.in);
            int serverChooser = in.nextInt();

            host = slist.getServerList().get(serverChooser-1).getiPv4Adress();
            port =  slist.getServerList().get(serverChooser-1).getPort();


            nameServerSocket.close();
            datain.close();
            output.close();


        }

        //If user chooses client server immediately, type out a message to confirm
        else if(server.equals("cs") || server.equals("CS")){
            System.out.println("You have entered arguments that want to access a chatserver directly: ");
            System.out.println(nickname + server + host + port);
        }

        System.out.println("Trying to connect...");
        Socket chatServerSocket = new Socket(host,port);

        System.out.println("Connected");
        Scanner input = new Scanner(System.in);

        /*Thread to add a join PDU and take user input and convert it into a message PDU and
         * to add to outgoing queue */
        OutqueueThreads outqueueThreads = new OutqueueThreads(chatServerSocket, nickname, input);
        outqueueThreads.StartOutputThreads();

        InqueueThreads inqueueThreads = new InqueueThreads(chatServerSocket);
        inqueueThreads.StartInqueueThreads();

    }
}