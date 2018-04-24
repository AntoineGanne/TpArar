import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
public class Util {
    DatagramSocket ds;
    InetAddress ipRecep; //adresseIP de la derniere personne a avoir envoyé un dp
    int portRecep; //port de la derniere personne a avoir envoyé un dp

    protected Util(){
        try {
            ds=new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    protected Util(int port){
        try {
            ds=new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    protected void envoyer(String data,String ip, int port){
        try {
            InetAddress a =InetAddress.getByName(ip);
            envoyer(data,a,port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    protected void envoyer(String data,InetAddress ip, int port){
        InetAddress a;
        DatagramPacket dp;
        try {
            dp = new DatagramPacket(data.getBytes(),data.length(),ip,port);
            ds.send(dp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected int ecouterPort(byte[] data){
        DatagramPacket dp=ecouter(data);
        return dp.getPort();
    }

    protected DatagramPacket ecouter(byte[] data){
        DatagramPacket dp=new DatagramPacket(data,data.length);
        try {
            ds.receive(dp);
//            String str = new String(data, StandardCharsets.UTF_8);
//            System.out.println("data:"+str);
            ipRecep=dp.getAddress();
            portRecep=dp.getPort();
            return dp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dp;
    }

    protected void repondre(String data){
        envoyer(data,ipRecep,portRecep);
    }

    public static List<Integer> scan(int dep,int fin){
        ArrayList<Integer> res=new ArrayList<>();
        DatagramSocket ds;
        for(int i=dep;i<=fin;i++){
            try {
                ds=new DatagramSocket(i);
                res.add(i);
                ds.close();
            } catch (SocketException e) {
//                System.out.println(i);
            }
        }
//        System.out.println("\n Fin des ports occupés. \n  ports libres:");
        return res;
    }


}
