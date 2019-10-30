import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class InqueueThreads {

    private Socket socket;
    private LinkedBlockingQueue<PDU> inQueue = new LinkedBlockingQueue<>();

    public InqueueThreads(Socket s) {

        this.socket = s;
    }

    public void StartInqueueThreads() throws IOException {

        DataInputStream inputData = new DataInputStream(socket.getInputStream());


        Thread inputThread1 = new Thread(() -> {

            while(true) {

                try {
                    byte pduChooser = inputData.readByte();

                    // If we get a pduChooser with another OP-code then the one we got in the switch case,
                    // we close everything and exits program.
                    switch (pduChooser) {
                        case 10:
                            MESS_PDU tempMess = new MESS_PDU(pduChooser, null, null);
                            tempMess.Deserialize(inputData);

                            inQueue.add(tempMess);
                            break;
                        case 11:
                            QUIT_PDU tempQuit = new QUIT_PDU(pduChooser);
                            tempQuit.Deserialize(inputData);
                            inQueue.add(tempQuit);
                            break;
                        case 16:
                            PJOIN_PDU tempPjoin = new PJOIN_PDU(pduChooser);
                            tempPjoin.Deserialize(inputData);
                            inQueue.add(tempPjoin);
                            break;
                        case 17:
                            PLEAVE_PDU tempPleave = new PLEAVE_PDU(pduChooser);
                            tempPleave.Deserialize(inputData);
                            inQueue.add(tempPleave);
                            break;
                        case 19:
                            PARTICIPANTS_PDU tempPart = new PARTICIPANTS_PDU(pduChooser);
                            tempPart.Deserialize(inputData);
                            inQueue.add(tempPart);
                            break;
                        default:
                            System.out.println("Received a not valid OP-code, exiting program");
                            inputData.close();
                            System.exit(-1);
                    }


                } catch (EOFException f) {
                    System.out.println("Program terminated, error occured.");
                    System.exit(-1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });inputThread1.start();

        Thread inputThread2 = new Thread(() -> {

            while (true){

                PDU tempPDU = null;
                try {
                    tempPDU = inQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //If a message is recieved, print the time, name of the client and the message
                if(tempPDU instanceof MESS_PDU){
                    MESS_PDU tempMess = (MESS_PDU) tempPDU;
                    System.out.println(tempMess.getActualTime() + " " + tempMess.getClientID() + ": " + tempMess.getMessage());
                }

                //If a QUIT is recieved, type out an exiting message and quit the program
                else if(tempPDU instanceof QUIT_PDU){
                    QUIT_PDU tempQuit = (QUIT_PDU) tempPDU;
                    System.out.println("RECIEVED A QUIT PDU, EXITING PROGRAM");
                    System.exit(-1);


                }
                //If a PJOIN is recieved, write out the name of the client that joined
                else if(tempPDU instanceof  PJOIN_PDU){
                    PJOIN_PDU tempPjoin = (PJOIN_PDU) tempPDU;
                    System.out.println(tempPjoin.getClientID() + " has joined the server");

                }
                //If a PLEAVE is recieved, write out the client that has left
                else if(tempPDU instanceof PLEAVE_PDU){
                    PLEAVE_PDU tempPleave = (PLEAVE_PDU) tempPDU;
                    System.out.println(tempPleave.getClientIDname() + " has left the server");

                }
                //If a PARTICIPANTS is recieved, type out the current clients on the server
                else if(tempPDU instanceof PARTICIPANTS_PDU){
                    PARTICIPANTS_PDU tempPart = (PARTICIPANTS_PDU) tempPDU;

                    for(int i = 0 ; i < tempPart.getIdentityList().size() ; i ++){
                        System.out.println("Clients in session: " + tempPart.getIdentityList().get(i));
                    }

                }

            }

        });inputThread2.start();

    }
}
