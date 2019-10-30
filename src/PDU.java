import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.util.Arrays;

public abstract class PDU {

   protected byte OP;

   public PDU(byte OP) {
       this.OP = OP;
   }


    //Method to help pad byte arrays at the end, does not pad before the last "word"
    public byte[] Padder(byte[] array) {
       byte[] temp;

       if(array.length % 4 == 3) {
           temp = new byte[array.length + 1];
       }
       else if(array.length % 4 == 2) {
           temp = new byte[array.length + 2];
       }
       else if(array.length % 4 == 1) {
           temp = new byte[array.length + 3];
       }
       else {
           temp = new byte[array.length];
       }

       //Copy input array to temp and add the padding
       System.arraycopy(array, 0, temp, 0, array.length);
       Arrays.fill(temp, array.length, temp.length, (byte) '\0');

        return temp;


    }

    /*
      A method for calculation the checksum. When we use a received PDU checksum should be 255. If we
      calculate checksum for a PDU we want to send, then the checksum is calculated by "OurChecksum = 255 - sum;
     */
    public int Checksum(byte[] array){

        int sum = 0;

        for(byte b:array){
            sum += b & 255;

            if(sum > 255){
                sum -= 255;
            }
        }
        return ~sum;
    }

    //Abstract method implemented in the different PDU´s
    public abstract byte[] Serialize() throws UnsupportedEncodingException;

    //Abstract method implemented in the different PDU´s
    public abstract void Deserialize(DataInputStream input) throws IOException;


    }
