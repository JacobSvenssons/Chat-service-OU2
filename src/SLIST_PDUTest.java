
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SLIST_PDUTest {

    @Test
    public void testCreation() throws UnknownHostException, UnsupportedEncodingException {


        short nrofServers = 5;
        short port = 5000;
        byte clients = 10;
        String serverName = "CRACKAH";
        byte[] serverArray = serverName.getBytes("UTF-8");
        byte serverNameLength = (byte) serverArray.length;

        byte[] test = new byte[20];
        test[0] = (byte) 5;
        test[1] = '\0';
        test[2] = (byte) (nrofServers >> 8);
        test[3] = (byte) nrofServers;
        test[4] = (byte) 192;
        test[5] = (byte) 168;
        test[6] = (byte) 123;
        test[7] = (byte) 12;
        test[8] = (byte) (port >> 8);
        test[9] = (byte) port;
        test[10] = clients;
        test[11] = serverNameLength;
        System.arraycopy(serverArray, 0, test, 12, serverNameLength);


        assertEquals(0, test.length % 4);



    }

    @Test
    public void testPadding() {

        short nrofServers = 5;
        short port = 5000;
        byte clients = 10;
        String serverName = "CRACKAH";
        byte[] serverArray = serverName.getBytes();
        byte serverNameLength = (byte) serverArray.length;

        byte[] test = new byte[18];
        test[0] = (byte) 5;
        test[1] = '\0';
        test[2] = (byte) (nrofServers >> 8);
        test[3] = (byte) nrofServers;
        test[4] = (byte) 192;
        test[5] = (byte) 168;
        test[6] = (byte) 123;
        test[7] = (byte) 12;
        test[8] = (byte) (port >> 8);
        test[9] = (byte) port;
        test[10] = clients;
        test[11] = serverNameLength;
        System.arraycopy(serverArray, 0, test, 12, serverNameLength - 2);

        assertEquals(2, test.length % 4);
    }

    /*
    @Test
    public void testCorrectServercode() throws IOException {

        short nrofServers = 5;
        short port = 5000;
        byte clients = 10;
        String serverName = "CRACKAH";
        byte[] serverArray = serverName.getBytes();
        byte serverNameLength = (byte) serverArray.length;

        byte[] test = new byte[20];
        test[0] = (byte) 5;
        test[1] = '\0';
        test[2] = (byte) (nrofServers >> 8);
        test[3] = (byte) nrofServers;
        test[4] = (byte) 192;
        test[5] = (byte) 168;
        test[6] = (byte) 123;
        test[7] = (byte) 12;
        test[8] = (byte) (port >> 8);
        test[9] = (byte) port;
        test[10] = clients;
        test[11] = serverNameLength;
        System.arraycopy(serverArray, 0, test, 12, serverNameLength);

        SLIST_PDU slist_pdu = new SLIST_PDU((byte) 4);

        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(test));

        slist_pdu = slist_pdu.Deserialize(inputStream);

        LinkedList<Servers> serverList = slist_pdu.getServerList();
        Servers tempServer = serverList.get(0);

        assertEquals("CRACKAH", tempServer.getServerName());


    }
*/

}