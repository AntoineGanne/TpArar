import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

public class Serveur extends Util {
    Serveur(){
        super(1500);

    }

    public static void main(String[] args) {
        Serveur s=new Serveur();
        s.ecoute();
    }

    public void ecoute(){
        byte[] data=new byte[100];
        DatagramPacket dp;
        while(true){
            dp=ecouter(data);
            System.out.println("nouveauClient: "+dp.getPort());
            String str = new String(dp.getData(), StandardCharsets.UTF_8);
            str=str.substring(0,dp.getLength());
            System.out.println(str);
            repondre("Serveur ready");
       }

    }

}
