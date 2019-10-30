

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class JOIN_PDUTest {


    @Test
    public void checkOPcode() throws UnknownHostException {
        byte[] temp;
        JOIN_PDU join_pdu = new JOIN_PDU((byte) 10, "MARCUS");

        temp = join_pdu.Serialize();

        assertEquals(10, temp[0]);

    }

    @Test
    public void checkIDlength() throws UnknownHostException, UnsupportedEncodingException {
        byte[] temp;
        String ID = "APAN";
        byte[] IDbytes = ID.getBytes("UTF-8");

        JOIN_PDU join_pdu = new JOIN_PDU((byte) 10, ID);

        temp = join_pdu.Serialize();

        assertEquals(IDbytes.length, temp[1]);
    }

    @Test
    public void checkFirstPadding() throws UnknownHostException {
        byte[] temp;
        String ID = "TESLA";
        int incrementer = 0;

        JOIN_PDU join_pdu = new JOIN_PDU((byte) 10, ID);

        temp = join_pdu.Serialize();

        for(int i = 2; i < 3; i++) {
            if(temp[i] != (byte) '\0') {
                incrementer++;
            }
        }

        assertEquals(0, incrementer);
    }

    @Test
    public void checkIDname() throws UnknownHostException {

        byte ID = 10;

        String tempName = "Mister Jacob";

        JOIN_PDU testPDU = new JOIN_PDU(ID, tempName);

        byte[] temp = testPDU.Serialize();

        byte[] test = new byte[tempName.length()];

        System.arraycopy(temp,4,test,0,tempName.getBytes().length);


        assertEquals(tempName,new String(test));

    }

}