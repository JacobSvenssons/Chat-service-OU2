import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GETLIST_PDUTest {


    @Test
    public void arrayRightSize() {
        byte[] temp;
        GETLIST_PDU getlist_pdu = new GETLIST_PDU((byte)3);
        temp = getlist_pdu.Serialize();

        assertEquals(4, temp.length);

    }

    @Test
    public void correctOPtest() {
        byte[] temp;

        GETLIST_PDU getlist_pdu = new GETLIST_PDU((byte) 3);
        temp = getlist_pdu.Serialize();

        assertEquals(3, temp[0]);
    }

    @Test
    public void correctPaddingTest() {
        byte[] temp;
        int incrementer = 0;

        GETLIST_PDU getlist_pdu = new GETLIST_PDU((byte) 3);
        temp = getlist_pdu.Serialize();

        for(int i = 1; i < temp.length; i++) {
            if(temp[i] != 0) {
                incrementer++;
            }
        }

        assertEquals(0, incrementer);
    }

}