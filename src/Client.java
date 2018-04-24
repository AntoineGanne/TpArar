import java.net.DatagramPacket;
import java.util.Scanner;

public class Client extends Util {
    Client(){
        super();
    }

    public static void main(String[] args) {
        Client c=new Client();
        c.run("127.0.0.1");
    }

    public void run(String ip){
        envoyer("hello serveur RX302",ip,1500);
        byte[] data=new byte[100];
        DatagramPacket dp =ecouter(data);
        System.out.println("Serveur RX302 ready:@IP "+dp.getAddress()+", port:" + dp.getPort());

        Scanner sc=new Scanner(System.in);
        String message=sc.nextLine();
        repondre(message);

    }
}
