package com.example.messengerclient;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;
    @FXML
    private Button button_choose_img;
    private Client client;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            client=new Client(new Socket("localhost",1234));
            System.out.println("Connected to server");
        }catch (IOException e){
            e.printStackTrace();
        }

        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double)newValue);
            }
        });
        client.receiveInformationFromServer(vbox_messages);
        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend=tf_message.getText();
                if(!messageToSend.isEmpty()){
                    HBox hBox=new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));
                    Text text=new Text(messageToSend);
                    TextFlow textFlow=new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239,242,255);"+"-fx-background-color: rgb(15,125,242);"+"-fx-background-radius: 20px");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(0.934,0.945,0.996));


                    hBox.getChildren().add(textFlow);
                    vbox_messages.getChildren().add(hBox);

                    client.sendMessageToServer(messageToSend);
                    tf_message.clear();
                }
            }
        });
        button_choose_img.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) button_choose_img.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                File selectedFile = fileChooser.showOpenDialog(stage);
                if(selectedFile!=null){
                    HBox hBox=new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));
                    Image image=new Image(selectedFile.toURI().toString());
                    ImageView imageView=new ImageView(image);
                    imageView.setFitHeight(200);
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);
                    hBox.getChildren().add(imageView);
                    vbox_messages.getChildren().add(hBox);
                    String path=selectedFile.getPath().toString();
                    int indexDot=path.indexOf('.');
                    String format=path.substring(indexDot+1);
                    client.sendImageToServer(image,format);
                }
            }
        });
    }
    public static void addLabel1(Image image,VBox vBox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        hBox.getChildren().add(imageView);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
    public static void addLabel(String msgFromServer,VBox vbox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text=new Text(msgFromServer);
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"+"-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);

            }
        });
    }
}