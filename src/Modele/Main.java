package Modele;

public class Main {

    public static void main(String[] args) {
//        List<Integer> scan =Modele.Util.scan(0,65525);
//        for(Integer i:
//                scan){
//            System.out.println(i);
//        }

//        Modele.Serveur s=new Modele.Serveur();
//        s.ecoute();
        Client c=new Client();
        c.run("127.0.0.1");
    }


}
