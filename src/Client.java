import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends Util {
    Scanner scannerConsole;
    Com communication;
    Client(){
        super();
        scannerConsole=new Scanner(System.in);
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
        //interrogation du serveur
        System.out.println("entrez le nom de la conversation");
        String nomSalle=scannerConsole.nextLine();
        String requete=RQ_CONNEXION_DEMANDE_TO_SERVER+" "+nomSalle;
        envoyer(requete,ipServer,PORT_SERVER);

        //reponse du serveur
        byte[] data=new byte[100];
        DatagramPacket dp=ecouter(data);
        String reponse = new String(dp.getData(), StandardCharsets.UTF_8);
        reponse=reponse.substring(0,dp.getLength());
        Scanner scanner=new Scanner(reponse);
        int codeRequete=Integer.valueOf(scanner.next());
        if(codeRequete==RQ_CONNEXION_ACCEPTE_SERVER){
            if(scanner.next().equals("hote")){
                //cas ou la salle est déjà crée et il faut la rejoindre
                initialisationCom(nomSalle);
                String ipHote=scanner.next();
                int portHote=scanner.nextInt();
                connexionVersHoteClient(nomSalle,ipHote,portHote);
            }else{
                //cas ou on est l'hote
                initialisationCom(nomSalle);
            }
        }
    }

    private void connexionVersHoteClient(String nomSalle, String ipHoteString, int portHote){
        String requete=String.valueOf(RQ_CONNEXION_DEMANDE_TO_HOST);
        InetAddress ipHote = null;
        try {
            ipHoteString=ipHoteString.substring(1);
            ipHote = InetAddress.getByName(ipHoteString);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        AdresseComplete adresse=new AdresseComplete(ipHote,portHote);
        communication.addAdresse(adresse);

        envoyer(requete,ipHote,portHote);
    }

    private void initialisationCom(String nomSalle){
        int portEcoute=getPortEcoute();
        communication=new Com(ds,nomSalle);

    }
}
