package Modele;

import javafx.beans.InvalidationListener;

import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;

public class Client extends Observable {
    Scanner scannerConsole;
    HashMap<String , Com> salles;
    Com currentCommunication;

    public Client(){
        scannerConsole=new Scanner(System.in);
        salles=new HashMap<>(0);
        currentCommunication=new Com();
    }

    public static void main(String[] args) {
        Client c=new Client();
        String ip="192.168.43.93";     //"127.0.0.1";
        c.run(ip);

    }

    public void run(String ip){


        /*
        envoyer("hello serveur RX302",ip,PORT_SERVER);
        byte[] data=new byte[100];
        DatagramPacket dp =ecouter(data);
        System.out.println("Modele.Serveur RX302 ready:@IP "+dp.getAddress()+", port:" + dp.getPort());

        String message=scannerConsole.nextLine();
        repondre(message);
        */


//        connexion(ip);
//        while(true){
//            String message = scannerConsole.nextLine();
//            currentCommunication.sendAll(message);
//        }
    }

    public void connexion(){
        System.out.println("Veuillez renseigner l'adresse ip du serveur");
        String ipServeur=scannerConsole.nextLine();
        connexion(ipServeur);
    }

    public void connexion(String ipServer){
        System.out.println("entrez le nom de la conversation");
        String nomSalle=scannerConsole.nextLine();
        connexion(ipServer,nomSalle);
    }

    public void connexion(String ipServer,String nomSalle){
        Com communication=new Com();
        communication.connexion(ipServer,nomSalle);

        salles.put(nomSalle,communication);
    }

    public String[] getDerniersMessages(String nomSalle, int nbMessages){
        //return salles.get(nomSalle).derniersMessages(nbMessages);
        return currentCommunication.derniersMessages(nbMessages);
    }

    public void envoyerMessage(String message){
        currentCommunication.sendAll(message);
//        setChanged();
//        notifyAll();
    }

    public void setCurrentCommunication(String nomSalle){
        currentCommunication=salles.get(nomSalle);
//        setChanged();
//        notifyAll();
    }

}
