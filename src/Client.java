import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends Util {
    Scanner scannerConsole;
    Com communication;
    Client(){
        scannerConsole=new Scanner(System.in);
        communication=new Com();
    }

    public static void main(String[] args) {
        Client c=new Client();
        c.run("127.0.0.1");

    }

    public void run(String ip){


        /*
        envoyer("hello serveur RX302",ip,PORT_SERVER);
        byte[] data=new byte[100];
        DatagramPacket dp =ecouter(data);
        System.out.println("Serveur RX302 ready:@IP "+dp.getAddress()+", port:" + dp.getPort());

        String message=scannerConsole.nextLine();
        repondre(message);
        */
        connexion(ip);
        while(true){
            String message = scannerConsole.nextLine();
            communication.sendAll(RQ_COM_MESSAGE+" "+message);
        }
    }

    public void connexion(){
        System.out.println("Veuillez renseigner l'adresse ip du serveur");
        String ipServeur=scannerConsole.nextLine();
        connexion(ipServeur);
    }

    public void connexion(String ipServer){
        System.out.println("entrez le nom de la conversation");
        String nomSalle=scannerConsole.nextLine();
        communication.connexion(ipServer,nomSalle);
    }


}
