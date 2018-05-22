import Modele.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class ClientFX extends Application implements Observer {
    Client modeleClient;
   // TextFlow zoneMessages;

    private String NomSalleActuelle;

    //Fonts
    private Font fontTitres;
    private Font fontBoutons;

    BorderPane border;

    GridPane gpBoutonsSalles;
    GridPane gpZoneHaute;
    TextArea textAreaNomSalle;
    TextArea textareaIpServeur;
    TextArea textAreaEnterMessage;

    GridPane gpZoneMessage;

    Button[] boutonsSalles;
    Button btnEnvoyerMessage;
    Button btnMiseAJour;

    ///// valeurs par défaut
    static final int MAX_NUMBER_SALLES=3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        modeleClient=new Client();

        //Fonts
        fontTitres=new Font("Arial",20);
        fontBoutons=new Font("Arial",15);

        //zones
        Label pasDeMessage=new Label("Pas de message");
        pasDeMessage.setFont(fontTitres);
       // zoneMessages=new TextFlow(pasDeMessage);
        gpZoneMessage=new GridPane();

        border=new BorderPane();
        border.setCenter(gpZoneMessage);

        gpZoneHaute=new GridPane();

        textAreaNomSalle=new TextArea();
        textAreaNomSalle.setPrefRowCount(1);
        textAreaNomSalle.setText("nom de nouvelle salle");
        gpZoneHaute.add(new Label("nom d'une nouvelle salle:"),0,0);
        gpZoneHaute.add(textAreaNomSalle,0,1);

        textareaIpServeur=new TextArea();
        textareaIpServeur.setPrefRowCount(1);
        textareaIpServeur.setText("127.0.0.1");
        gpZoneHaute.add(new Label("adresse Ip du serveur:"),0,3);
        gpZoneHaute.add(textareaIpServeur,0,4);

        border.setTop(gpZoneHaute);



        //////////// Controlleurs
        gpBoutonsSalles =new GridPane();
        boutonsSalles=new Button[MAX_NUMBER_SALLES];

        for(int i=0;i<MAX_NUMBER_SALLES;i++){
            Button boutonNouvelleSalle = new Button("Nouvelle Salle");
            boutonsSalles[i]=boutonNouvelleSalle;
            gpBoutonsSalles.add(boutonNouvelleSalle,0,i);
            boutonNouvelleSalle.setOnAction(
                    event -> {
                            String nomSalle=textAreaNomSalle.getText();
                            boutonNouvelleSalle.setText(nomSalle);
                            String ipServer=textareaIpServeur.getText();
                            modeleClient.connexion(ipServer,nomSalle);


                            boutonNouvelleSalle.setOnAction(
                                    event1 -> {
                                        modeleClient.setCurrentCommunication(boutonNouvelleSalle.getText());
                                    }
                            );
                    }
            );
        }
        btnEnvoyerMessage=new Button("Envoyer Message");
        btnEnvoyerMessage.setOnAction(
                event -> {
                    modeleClient.envoyerMessage(textAreaEnterMessage.getText());
                }
        );
        gpBoutonsSalles.add(btnEnvoyerMessage,0,5);

        btnMiseAJour=new Button("mettre a jour Vue");
        btnMiseAJour.setOnAction(
                event->{
                    miseAJour();
                }
        );
        gpBoutonsSalles.add(btnMiseAJour,0,6);
        border.setLeft(gpBoutonsSalles);

        textAreaEnterMessage=new TextArea();
        border.setBottom(textAreaEnterMessage);



        Scene scene = new Scene(border, Color.LIGHTBLUE);
        primaryStage.setTitle("Le super client!");
        primaryStage.setScene(scene);
        primaryStage.show();


        scene.setOnKeyPressed(
                event -> {
                    if(event.getCode()==KeyCode.ENTER){
                        String message=textAreaEnterMessage.getText();
                        modeleClient.envoyerMessage(message);
                    }
                }

        );
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                miseAJour();
            }
        });
    }

    public void miseAJour(){
        String[] messages=modeleClient.getDerniersMessages(NomSalleActuelle,5);
        Label[] labels=new Label[messages.length];
        for(int i=0;i<messages.length;i++){
            Label mess=new Label(messages[i] + '\n');
            labels[i]=mess;
            gpZoneMessage.add(mess,0,i);
        }
//        border.setCenter(zoneMessages);
    }


}
