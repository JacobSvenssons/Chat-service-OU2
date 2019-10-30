import java.io.DataInputStream;
import java.io.IOException;

public class PJOIN_PDU extends PDU {

    String clientID;

    //OP16
    public PJOIN_PDU(byte OP) {
        super(OP);
    }

    @Override
    public byte[] Serialize() {
        return null;
    }

    @Override
    public void Deserialize(DataInputStream inputStream) throws IOException {

        byte IDlength = inputStream.readByte();
        byte[] clientIDarray = new byte[IDlength];

        //Should be 1 byte padding here, if not its a invalid PDU.
        if(inputStream.readByte() != 0){
            System.out.println("Invalid PLEAVE-PDU-padding");
            System.exit(-1);
        }
        //Should be 1 byte padding here, if not its a invalid PDU.
        if(inputStream.readByte() != 0){
            System.out.println("Invalid PLEAVE-PDU-padding");
            System.exit(-1);
        }

        int timeStamp = inputStream.readInt();

        for(int i = 0; i < IDlength; i++) {
            clientIDarray[i] = inputStream.readByte();
        }

        clientID = new String(clientIDarray, "UTF-8");

        if(IDlength%4 == 3){
            if(inputStream.readByte() != 0){
                System.out.println("Invalid PJOIN-PDU-padding");
                System.exit(-1);
            }
        }
        else if(IDlength%4 == 2){
            if(inputStream.readByte() != 0){
                System.out.println("Invalid PJOIN-PDU-padding");
                System.exit(-1);
            }
            if(inputStream.readByte() != 0){
                System.out.println("Invalid PJOIN-PDU-padding");
                System.exit(-1);
            }
        }
        else if(IDlength%4 == 1){
            if(inputStream.readByte() != 0){
                System.out.println("Invalid PJOIN-PDU-padding");
                System.exit(-1);
            }
            if(inputStream.readByte() != 0){
                System.out.println("Invalid PJOIN-PDU-padding");
                System.exit(-1);
            }
            if(inputStream.readByte() != 0){
                System.out.println("Invalid PJOIN-PDU-padding");
                System.exit(-1);
            }
        }

    }

    public String getClientID() {
        return clientID;
    }
}
