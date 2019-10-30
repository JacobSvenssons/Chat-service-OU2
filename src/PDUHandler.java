import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class PDUHandler {

    DataInputStream input;
    String ID;
    byte OP;

    /* VI GÖR DETTA I MAIN, DÅ VILL MAN INTE GÖRA RETURN NEW I VARJE CASE, UTAN SKAPA EXEMPELVIS SKAPA EN NY
    GETLIST_PDU OCH SKAPA ETT SÅDDANT OBJEKT
     */

    public PDUHandler(DataInputStream input, String ID, byte OP) {
        this.input = input;
        this.ID = ID;
        this.OP = OP;
    }



}
