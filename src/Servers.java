import java.net.InetAddress;

public class Servers {

    private InetAddress iPv4Adress;
    private int port;
    private int clients;
    private String serverName;

    public Servers(InetAddress iPv4Adress, int port , int clients, String serverName){

        this.iPv4Adress = iPv4Adress;
        this.port = port;
        this.clients = clients;
        this.serverName = serverName;

    }

    //add getters
    public String getServerName(){
        return serverName;
    }

    public short getPort() { return (short) this.port;}

    public byte getClients() { return (byte) this.clients; }

    public InetAddress getiPv4Adress() {return this.iPv4Adress;}

}
