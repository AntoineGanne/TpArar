import java.net.InetAddress;

public class AdresseComplete {
    public InetAddress ip;
    public int port;

    public AdresseComplete(InetAddress ip, int port){
        this.ip=ip;
        this.port=port;
    }

    @Override
    public String toString() {
        return "AdresseComplete{" +
                "ip=" + ip +
                ", port=" + port +
                '}';
    }
}
