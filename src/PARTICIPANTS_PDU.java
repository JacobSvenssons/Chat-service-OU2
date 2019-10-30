import java.io.DataInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;

public class PARTICIPANTS_PDU extends PDU {

    LinkedList<String> identityList = new LinkedList<>();

    public PARTICIPANTS_PDU(byte OP) {
        super(OP);
    }

    @Override
    public byte[] Serialize() {
        return new byte[0];
    }

    @Override
    public void Deserialize(DataInputStream input) throws IOException {

        byte numOfIDss = input.readByte();
        short length = input.readShort();

        byte[] clientIDarray = new byte[length];

        int j = 0;

        byte temp = 0;
        for(int i =0; i < length; i++){
            temp = input.readByte();
            if(temp != '\0'){
                clientIDarray[i] = temp;
                j++;
            }
            else{
                clientIDarray[i] = temp;

                byte[] tempID = new byte[j];
                System.arraycopy(clientIDarray,i - j,tempID,0,j);

                String tempClientIDname = new String(tempID, "UTF-8");
                identityList.add(tempClientIDname);
                j = 0;

            }
        }

        //Read padding
        if(length%4 == 3){
            if(input.readByte() != 0){
                System.out.println("Invalid PARTICIPANTS-PDU-padding");
                System.exit(-1);
            }
        }
        else if(length%4 == 2){
            if(input.readByte() != 0){
                System.out.println("Invalid PARTICIPANTS-PDU-padding");
                System.exit(-1);
            }
            if(input.readByte() != 0){
                System.out.println("Invalid PARTICIPANTS-PDU-padding");
                System.exit(-1);
            }
        }
        else if(length%4 == 1){
            if(input.readByte() != 0){
                System.out.println("Invalid PARTICIPANTS-PDU-padding");
                System.exit(-1);
            }
            if(input.readByte() != 0){
                System.out.println("Invalid PARTICIPANTS-PDU-padding");
                System.exit(-1);
            }
            if(input.readByte() != 0){
                System.out.println("Invalid PARTICIPANTS-PDU-padding");
                System.exit(-1);
            }
        }

    }

    public LinkedList<String> getIdentityList() {
        return identityList;
    }
}
