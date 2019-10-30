
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;

public class SLIST_PDU extends PDU{

    LinkedList<Servers> serverList = new LinkedList<>();


    public SLIST_PDU(byte OP) {
        super(OP);

    }

    /* We will never send a SLIST_PDU only receive as answer after sending a GETLIST_PDU.
    Therefore we don't need to implement this method. */
    @Override
    public byte[] Serialize(){
        return null;
    }

    //Gör allt till sträng och sen parsa med hjälpfunktioner?
    @Override
    public void Deserialize(DataInputStream input) throws IOException {

        if(input.readByte() != 0){
            System.out.println("Invalid SLIST-PDU-padding");
            System.exit(-1);
        }

        InetAddress address;
        String TempAddress;
        int port;
        int clients;
        int servernameLength;
        String serverName;

        int numberOfServers = input.readUnsignedShort();

        int[] temp2 = new int[4];

        for (int i = 0; i < numberOfServers; i++) {
            StringBuilder temp = new StringBuilder();
            for(int j = 0; j < 4; j++){
                temp2[j] = input.readUnsignedByte();
                if(j!=3) {
                    temp.append(temp2[j]);
                    temp.append('.');
                }
                else{
                    temp.append(temp2[j]);

                }
            }


            TempAddress = temp.toString();
            address = InetAddress.getByName(TempAddress);
            port = input.readUnsignedShort();
            clients = input.readUnsignedByte();
            servernameLength = input.readUnsignedByte();

            StringBuilder temp33 = new StringBuilder();

            for(int k = 0; k < servernameLength ; k++){
                temp33.append((char) input.readByte());
            }

            //Check padding and make sure its correct
            if(servernameLength%4 == 3){
                if(input.readByte() != 0){
                    System.out.println("Invalid SLIST-PDU-padding");
                    System.exit(-1);
                }
            }
            else if(servernameLength%4 == 2){
                if(input.readByte() != 0){
                    System.out.println("Invalid SLIST-PDU-padding");
                    System.exit(-1);
                }
                if(input.readByte() != 0){
                    System.out.println("Invalid SLIST-PDU-padding");
                    System.exit(-1);
                }
            }
            else if(servernameLength%4 == 1){
                if(input.readByte() != 0){
                    System.out.println("Invalid SLIST-PDU-padding");
                    System.exit(-1);
                }
                if(input.readByte() != 0){
                    System.out.println("Invalid SLIST-PDU-padding");
                    System.exit(-1);
                }
                if(input.readByte() != 0){
                    System.out.println("Invalid SLIST-PDU-padding");
                    System.exit(-1);
                }
            }

            String tempServerName = temp33.toString();
            Servers tempServer = new Servers(address,port,clients,tempServerName);
            serverList.add(tempServer);
        }

    }

    public LinkedList<Servers> getServerList() {
        return serverList;
    }
}
