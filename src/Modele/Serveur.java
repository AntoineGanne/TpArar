package Modele;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class Serveur extends Util {
    private HashMap<String, AdresseComplete> personnesEnAttente;

    Serveur(){
        super(PORT_SERVER);
        personnesEnAttente=new HashMap<String, AdresseComplete>();

    }

    public static void main(String[] args) {
        Serveur s=new Serveur();
        s.ecoute();
    }

    public void ecoute(){
        byte[] data=new byte[100];
        DatagramPacket dp;
        String message;
        Scanner scanner;
        while(true){
            //on attend et recupère la requète
            dp=ecouter(data,true);
            System.out.println("nouveauClient: "+dp.getPort());
            String requeteRecue = new String(dp.getData(), StandardCharsets.UTF_8);
            requeteRecue=requeteRecue.substring(0,dp.getLength());

            scanner=new Scanner(requeteRecue);
            //////
            int codeRequete=Integer.valueOf(scanner.next());
            if(codeRequete==RQ_CONNEXION_DEMANDE_TO_SERVER){
                String code=scanner.next();
                if(personnesEnAttente.containsKey(code)){
                    AdresseComplete adresseHote= personnesEnAttente.get(code);
                    message =RQ_CONNEXION_ACCEPTE_SERVER+" hote "+ adresseHote.ip + " " + adresseHote.port;
                }
                else{
                    AdresseComplete adresseHote=new AdresseComplete(dp.getAddress(),dp.getPort());
                    personnesEnAttente.put(code,adresseHote);
                    message=RQ_CONNEXION_ACCEPTE_SERVER+" création de la salle reussie, votre correspondant doit desormais se connecter avec votre code";
                }
                System.out.println(dp.getAddress()+"  "+dp.getPort());
                envoyer(message,dp.getAddress(),dp.getPort());

            }
       }
    }

}
