import java.io.DataInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class JOIN_PDU extends PDU {
    private String ID;
    private byte IDlength;
    private byte[] IDbytes;


    public JOIN_PDU(byte OP, String s) {
        super(OP);
        this.ID = s;
    }

    public byte[] Serialize() {

        int extraClientLenght = 0;
        byte[] returnClient = new byte[0];

        try {
            byte[] tempID = ID.getBytes("UTF-8");

            if(tempID.length %4 == 3){
                extraClientLenght++;
            }
            else if(tempID.length%4 == 2){
                extraClientLenght++;
                extraClientLenght++;
            }
            else if(tempID.length%4 == 1){
                extraClientLenght++;
                extraClientLenght++;
                extraClientLenght++;
            }

            int totalLenght = 4 + tempID.length + extraClientLenght;

            byte[] joinPDU = new byte[totalLenght];

            byte clientIDLength = (byte) tempID.length;

            joinPDU[0] = OP;
            joinPDU[1] = clientIDLength;
            joinPDU[2] = '\0';
            joinPDU[3] = '\0';

            System.arraycopy(tempID, 0, joinPDU, 4, tempID.length);

            for(int i = 0; i < extraClientLenght ; i++) {
                joinPDU[4 + tempID.length] = '\0';
            }

            returnClient = joinPDU;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnClient;

    }

    //Sent from client to server, never recieving this PDU
    public void Deserialize(DataInputStream input) { }

}
