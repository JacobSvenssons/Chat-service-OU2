import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QUIT_PDUTest {

    int chagne = 3;

    @Test
    public void checkLengthTest() {
        byte[] temp;

        QUIT_PDU quit_pdu = new QUIT_PDU((byte) 11);
        temp = quit_pdu.Serialize();

        assertEquals(4, temp.length);
    }

    @Test
    public void checkOPcode() {
        byte[] temp;

        QUIT_PDU quit_pdu = new QUIT_PDU((byte) 11);
        temp = quit_pdu.Serialize();

        assertEquals(11, temp[0]);

    }

    @Test
    public void checkPadding() {
        byte[] temp;
        int incrementer = 0;

        QUIT_PDU quit_pdu = new QUIT_PDU((byte) 11);
        temp = quit_pdu.Serialize();

        for(int i = 1; i < 3; i++) {
            if(temp[i] != 0) {
                incrementer++;
            }
        }

        assertEquals(0, incrementer);
    }

}