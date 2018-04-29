import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Com extends Util implements Runnable{
    private ArrayList<AdresseComplete> adresses;

    public String getNomSalle() {
        return nomSalle;
    }

    private String nomSalle;

    public Com(int portecoute,String nomSalleInput){
        super(portecoute);
        adresses=new ArrayList<>();
        nomSalle=nomSalleInput;
        new Thread().run();
    }

    @Override
    public void run() {

        DatagramPacket dp;
        byte[] data=new byte[100];
        while(true){
            dp=ecouter(data,true);
            String str = new String(dp.getData(), StandardCharsets.UTF_8);
            str=str.substring(0,dp.getLength());
            Scanner scan=new Scanner(str);
            int numRequete=scan.nextInt();
            switch (numRequete){
                case RQ_CONNEXION_DEMANDE_TO_HOST:
//                    InetAddress ipRecue=dp.getAddress();
//                    int portRecue= dp.getPort();
//                    System.out.println("Accepter connexion a "+nomSalle+" au client "
//                            +"{ ip="+ipRecue+" ,port="+portRecue);

                case RQ_ADD_ADRESS:
                    addAdresse(dp);
                    System.out.println("adresse ajoutée a "+nomSalle
                            +" "+adresses.get(adresses.size()-1).toString());
                    break;
                case RQ_COM_MESSAGE:
                    String message= scan.next();
                    System.out.println(message);
                    break;
            }
        }
    }

    public void sendAll(String message){
        for(AdresseComplete adresse:
                adresses){
            envoyer(message,adresse.ip,adresse.port);
        }
    }

    public void addAdresse(DatagramPacket dp){
        InetAddress ip=dp.getAddress();
        int port= dp.getPort();
        AdresseComplete adresse=new AdresseComplete(ip,port);
        adresses.add(adresse);
    }

    public void addAdresse(AdresseComplete adresse){
        adresses.add(adresse);
    }



}
