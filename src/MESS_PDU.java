

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MESS_PDU extends PDU{

    private int IDlength;
    private int checkSum;
    private int messageLength;
    private int timeStamp;
    private int checkSumCounter;
    private String message;
    private String clientID;
    private String actualTime;

    public MESS_PDU(byte OP, String message, String ID) {
        super(OP);
        this.message = message;
        this.clientID = ID;
    }

    @Override
    public byte[] Serialize() {

        int extraMessageLenght = 0;
        byte[] returnMess = new byte[0];

        try {
            byte[] tempMess = message.getBytes("UTF-8");

            if(tempMess.length %4 == 3){
                extraMessageLenght++;
            }
            else if(tempMess.length%4 == 2){
                extraMessageLenght++;
                extraMessageLenght++;
            }
            else if(tempMess.length%4 == 1){
                extraMessageLenght++;
                extraMessageLenght++;
                extraMessageLenght++;
            }


            int totalLenght = 12 + tempMess.length + extraMessageLenght;

            byte[] messPDU = new byte[totalLenght];

            short messageLength = (short) tempMess.length;


            messPDU[0] = OP;
            messPDU[1] = '\0';
            messPDU[2] = 0;
            messPDU[3] = 0;
            messPDU[4] = (byte) (messageLength >> 8);
            messPDU[5] = (byte) messageLength;
            messPDU[6] = (byte) '\0';
            messPDU[7] = (byte) '\0';
            messPDU[8] = 0;
            messPDU[9] = 0;
            messPDU[10] = 0;
            messPDU[11] = 0;

            System.arraycopy(tempMess, 0, messPDU, 12, tempMess.length);

            for(int i = 0; i < extraMessageLenght ; i++) {
                messPDU[12 + tempMess.length] = '\0';
            }

            messPDU[3] = (byte) checkSum(messPDU);


            returnMess =  messPDU;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return returnMess;

    }

    public int checkSum(byte[] array) {
        int sum = 0;

        for(byte b:array){
            sum += b & 255;

            if(sum > 255){
                sum -= 255;
            }
        }

        return ~sum;
    }

    public void Deserialize(DataInputStream inputStream) throws IOException {

        checkSumCounter = 0;

        //Should be 1 byte padding here, if not its a invalid PDU.
        if(inputStream.readByte() != 0){
            System.out.println("Invalid Message-PDU-padding");
            System.exit(-1);
        }
        IDlength = inputStream.readUnsignedByte();
        checkSum = inputStream.readUnsignedByte();
        messageLength = inputStream.readUnsignedShort();
        //Should be 1 byte padding here, if not its a invalid PDU.
        if(inputStream.readByte() != 0){
            System.out.println("Invalid Message-PDU-padding");
            System.exit(-1);
        }
        //Should be 1 byte padding here, if not its a invalid PDU.
        if(inputStream.readByte() != 0){
            System.out.println("Invalid Message-PDU-padding");
            System.exit(-1);
        }

        //Read int and convert to long to use as date
        timeStamp = inputStream.readInt();
        long longTimeStamp = Integer.toUnsignedLong(timeStamp);

        //format the date for our time zone
        Date date = new Date(longTimeStamp * 1000);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("CET"));

        actualTime = formatter.format(date);

        byte[] tempMess = new byte[messageLength];

        for(int i = 0; i < messageLength ; i++){
            tempMess[i] =inputStream.readByte();
            checkSumCounter += Byte.toUnsignedInt(tempMess[i]);
        }

        message = new String(tempMess,"UTF-8");


        //Read padding if needed
        if(messageLength%4 == 3){
            inputStream.readByte();
        }
        else if(messageLength%4 == 2){
            inputStream.readByte();
            inputStream.readByte();
        }
        else if(messageLength%4 == 1){
            inputStream.readByte();
            inputStream.readByte();
            inputStream.readByte();
        }


        byte[] tempClient = new byte[IDlength];

        for(int i = 0; i < IDlength ; i++){
            tempClient[i] =inputStream.readByte();
            checkSumCounter += Byte.toUnsignedInt(tempClient[i]);
        }

        clientID = new String(tempClient,"UTF-8");


        //Read padding if needed
        if(IDlength%4 == 3){
            if(inputStream.readByte() != 0){
                System.out.println("Invalid Message-PDU-padding");
                System.exit(-1);
            }
        }
        else if(IDlength%4 == 2){
            if(inputStream.readByte() != 0){
                System.out.println("Invalid Message-PDU-padding");
                System.exit(-1);
            }
            if(inputStream.readByte() != 0){
                System.out.println("Invalid Message-PDU-padding");
                System.exit(-1);
            }
        }
        else if(IDlength%4 == 1){
            if(inputStream.readByte() != 0){
                System.out.println("Invalid Message-PDU-padding");
                System.exit(-1);
            }
            if(inputStream.readByte() != 0){
                System.out.println("Invalid Message-PDU-padding");
                System.exit(-1);
            }
            if(inputStream.readByte() != 0){
                System.out.println("Invalid Message-PDU-padding");
                System.exit(-1);
            }
        }

        /*checkSumCounter =+ (byte) 10 + IDlength + checkSum + timeStamp + messageLength;

        while(checkSumCounter > 255) {
            checkSumCounter -= 255;
        }
        */

    }

    public String getMessage() {
        return message;
    }

    public String getClientID() {
        return clientID;
    }

    public int getTimeStamp() {return this.timeStamp;}

    public String getActualTime() {
        return this.actualTime;
    }

    public int getCheckSum() {
        return this.checkSum;
    }

    public int getIDlength() {return this.IDlength;}
}
