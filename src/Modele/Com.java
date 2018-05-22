package Modele;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Com extends Util implements Runnable{
    private ArrayList<AdresseComplete> adresses;
    private ArrayList<String> messages;

    public String getNomSalle() {
        return nomSalle;
    }

    private String nomSalle;

    public Com(){
        super();
        adresses=new ArrayList<>();
        messages=new ArrayList<>();
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
                    InetAddress ipRecue=dp.getAddress();
                    int portRecue= dp.getPort();
//                    System.out.println("Accepter connexion a "+nomSalle+" au client "
//                            +"{ ip="+ipRecue+" ,port="+portRecue);

                case RQ_ADD_ADRESS:
                    addAdresse(dp);
                    System.out.println("adresse ajoutée a "+nomSalle
                            +" "+adresses.get(adresses.size()-1).toString());
                    break;
                case RQ_COM_MESSAGE:
                    String message= str.substring(1);
                    System.out.println(message);
                    messages.add(message);
                    break;
            }
        }
    }

    public void sendAll(String message){
        messages.add(message); //enregistre le message

        message=RQ_COM_MESSAGE+" "+message;
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


    public void connexion(String ipServer,String nomSalle){
        this.nomSalle=nomSalle;
        //interrogation du serveur
        String requete=RQ_CONNEXION_DEMANDE_TO_SERVER+" "+nomSalle;
        envoyer(requete,ipServer,PORT_SERVER);

        //reponse du serveur
        byte[] data=new byte[100];
        DatagramPacket dp=ecouter(data,true);  //ecoute
        String reponse = new String(dp.getData(), StandardCharsets.UTF_8);
        reponse=reponse.substring(0,dp.getLength());
        Scanner scanner=new Scanner(reponse);
        int codeRequete=Integer.valueOf(scanner.next());

        if(codeRequete==RQ_CONNEXION_ACCEPTE_SERVER){
            if(scanner.next().equals("hote")){
                //cas ou la salle est déjà crée et il faut la rejoindre
                String ipHote=scanner.next();
                int portHote=scanner.nextInt();
                connexionVersHoteClient(nomSalle,ipHote,portHote);
            }else{
                //cas ou on est l'hote
            }
        }
        new Thread(this).start();
    }

    private void connexionVersHoteClient(String nomSalle, String ipHoteString, int portHote){
        String requete=String.valueOf(RQ_CONNEXION_DEMANDE_TO_HOST)+" demande de connexion";
        InetAddress ipHote = null;
        try {
            ipHoteString=ipHoteString.substring(1);
            ipHote = InetAddress.getByName(ipHoteString);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        AdresseComplete adresse=new AdresseComplete(ipHote,portHote);
        addAdresse(adresse);

        envoyer(requete,ipHote,portHote);
    }

    public String[] derniersMessages(int nbMessages){
        int l=messages.size();

        if(l<=nbMessages){
            return messages.toArray(new String[0]);
        }
        else{
            String[] resultat= (messages.subList(l - nbMessages, l)).toArray(new String[0]);  //crée un tableau des derniers messages
            return resultat;
        }
    }


}
