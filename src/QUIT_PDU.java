import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class QUIT_PDU extends PDU {

    public QUIT_PDU(byte OP) {
        super(OP);
    }
    int test = 1;

    public byte[] Serialize() {
        byte[] temp = new byte[4];

        temp[0] = this.OP;
        //Pad remainder
        byte[] temp1 = Padder(temp);

        return temp1;

    }

    //Client sends and recieves, need to serialize and deserialize
    public void Deserialize(DataInputStream input) throws IOException {
        for(int i = 0; i < 3; i++) {
            if(input.readByte() != 0){
                System.out.println("Invalid QUIT-PDU-padding");
                System.exit(-1);
            }
        }
    }
}
