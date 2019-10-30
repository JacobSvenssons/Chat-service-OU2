import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class GETLIST_PDU extends PDU {

    public GETLIST_PDU(byte OP){
        super(OP);
    }

    @Override
    public byte[] Serialize() {
        byte[] arr = new byte[4];
        arr[0] = OP;
        //Add padding
        arr[1] = '\0';
        arr[2] = '\0';
        arr[3] = '\0';


        return arr;
    }

    /* We will never receive a  GETLIST_PDU only send. Since this PDU is sent to the name-server
    that we don't implement this method. */
    @Override
    public void Deserialize(DataInputStream input) {
    }
}


/*

    Parametrar 0-255 klienter
    och OP nummer som man skickar som svar


            SerializeAlive som tar två argument,antal kliendre och OP nummer

    // OP NUMMRET SKA VARA SHORT

    Exempel saker att testa för PDU:
    Testa OP-kod
    Testa antal klienter



*/

