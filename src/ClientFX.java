import Modele.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class ClientFX extends Application implements Observer {
    Client modeleClient;
    TextFlow zoneMessages;

    //Fonts
    private Font fontTitres;
    private Font fontBoutons;

    BorderPane border;

    private String NomSalleActuelle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        modeleClient=new Client();
        modeleClient.run("127.0.0.1");

        //Fonts
        fontTitres=new Font("Arial",20);
        fontBoutons=new Font("Arial",15);

        //zones
        Label pasDeMessage=new Label("Pas de message");
        pasDeMessage.setFont(fontTitres);
        zoneMessages=new TextFlow(pasDeMessage);

        border=new BorderPane(zoneMessages);
        border.setCenter(zoneMessages);

        Scene scene = new Scene(border, Color.LIGHTBLUE);

        primaryStage.setTitle("Le super client!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        String[] messages=modeleClient.getDerniersMessages(NomSalleActuelle,5);
        Label[] labels=new Label[messages.length];
        for(int i=0;i<messages.length;i++){
            Label mess=new Label(messages[i]);
            labels[i]=mess;
        }
    }
}
