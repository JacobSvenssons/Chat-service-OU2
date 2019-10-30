import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class PDU_tester {


    public static void main(String[] args) throws IOException {

        /*FUNGERANDE TEST, TA EJ BORT
        //TESTING PARTICIPANT_PDU DESERIALIZING
        PARTICIPANTS_PDU participants_pdu = new PARTICIPANTS_PDU((byte) 19);
        short length = 6;


        byte[] test = new byte[12];
        test[0] = (byte) 19;
        test[1] = (byte) 1;
        test[2] = (byte) (length >> 8);
        test[3] = (byte) length;
        test[4] = (byte) 'P';
        test[5] = (byte) 'O';
        test[6] = (byte) '\0';
        test[7] = (byte) 'T';
        test[8] = (byte) 'A';
        test[9] = (byte) '\0';
        test[10] = (byte) '\0';
        test[11] = (byte) '\0';

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(test);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        byte first = dataInputStream.readByte();

        participants_pdu.Deserialize(dataInputStream);

        LinkedList<String> IDlist = participants_pdu.getIdentityList();
        System.out.println("Testing PARTICIPANT_PDU...");
        System.out.println("The first ID in list is " + IDlist.getFirst());
        System.out.println("The second ID in list is " + IDlist.getLast());
        System.out.println("Amount in list: " + IDlist.size());
        System.out.println("Test completed.");
        */


        /*
        //TESTING SLIST_PDU DESERIALIZING
        System.out.println("Testing SLIST_PDU...");
        SLIST_PDU slist_pdu = new SLIST_PDU((byte) 4);
        byte[] test1 = new byte[16];
        byte[] IP = new byte[4];
        short port = 5000;
        byte nrOfServers = 1;
        byte nrOfClients = 1;
        String serverName = "JACOB";
        InetAddress ip = InetAddress.getLocalHost();
        IP = ip.getAddress();

        test1[0] = (byte) 4;
        test1[1] = (byte) '\0';
        test1[2] = nrOfServers;
        System.arraycopy(IP, 0, test1, 3, 4);
        test1[8] = (byte) (port >> 8);
        test1[9] = (byte) port;
        test1[10] = nrOfClients;
        test1[11] = (byte) 5;
        System.arraycopy(serverName.getBytes("UTF-8"), 0, test1, 12, 4);

        ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(test1);

        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream1);

        byte dump1 = dataInputStream.readByte();

        slist_pdu.Deserialize(dataInputStream);

        LinkedList<Servers> serverList = slist_pdu.getServerList();
        System.out.println("ServerName: " + serverList.getFirst().getServerName());
        System.out.println("IP: " + serverList.getFirst().getiPv4Adress().getCanonicalHostName());
        System.out.println("Port: " + serverList.getFirst().getPort());
        System.out.println("Clients: " + serverList.getFirst().getClients());

        */

        byte[] tempArr = new byte[20];
        byte a = 10;
        int sum = 0;


        Arrays.fill(tempArr,0,20,a);

        tempArr[3] =(byte) 65;

        for(byte b:tempArr){
            sum += (int) b ;

            if(sum > 255){
                sum -= 255;
            }
        }

        int checkSum = 255 - sum;
        System.out.println(checkSum);
        System.out.println(sum);
        Scanner in = new Scanner(System.in);
        String tests = in.nextLine();
        System.out.println(tests);

    }

}
