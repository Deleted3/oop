package com.example.messengerclient;

import Model.Message;
import Model.Owner;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    @FXML
    private ListView list_user;
    private Client client;
    public static String receiver_id;
    public static Owner owner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        receiver_id="01";
        try{
            client=new Client(new Socket("localhost",49152));
            System.out.println("Connected to server");
        }catch (IOException e){
            e.printStackTrace();
        }
        Main.cruDfirebase.readMessageFirebase("01");
        addChatHistory(Main.cruDfirebase.getListMessages());
        vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sp_main.setVvalue((Double)newValue);
            }
        });
        for(int i=0;i<5;i++){
            HBox hBox=createFriendsItem("Khánh",new Image("/onepice.png"));
            list_user.getItems().add(hBox);
        }
        list_user.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            receiver_id=Main.cruDfirebase.getListUsers().get(list_user.getItems().indexOf(t1)).getId();
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
                    Font font = new Font("Arial", 16);
                    text.setFont(font);
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
        tf_message.setOnKeyPressed(keyEvent ->{
            if(keyEvent.getCode()== KeyCode.ENTER){
                String messageToSend=tf_message.getText();
                if(!messageToSend.isEmpty()){
                    HBox hBox=new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));
                    Text text=new Text(messageToSend);
                    Font font = new Font("Arial", 16);
                    text.setFont(font);
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
    private HBox createFriendsItem(String name,Image image){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("friends-item.fxml"));
        try {
            HBox friendItem = loader.load();
            FriendsItem controller = loader.getController();
            controller.setFriend(name, image);
            return friendItem;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void addChatHistory(ObservableList<Message> listMessages){
        for(Message message:listMessages){
            if(message.getSender_id().equals(owner.getId())){
                HBox hBox=new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5,5,5,10));
                Text text=new Text(message.getText());
                Font font = new Font("Arial", 16);
                text.setFont(font);
                TextFlow textFlow=new TextFlow(text);
                textFlow.setStyle("-fx-color: rgb(239,242,255);"+"-fx-background-color: rgb(15,125,242);"+"-fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5,10,5,10));
                text.setFill(Color.color(0.934,0.945,0.996));

                hBox.getChildren().add(textFlow);
                vbox_messages.getChildren().add(hBox);
            }
            else{
                HBox hBox=new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5,5,5,10));
                Text text=new Text(message.getText());
                TextFlow textFlow=new TextFlow(text);
                Font font = new Font("Arial", 16);
                text.setFont(font);
                textFlow.setStyle("-fx-background-color: rgb(233,233,235);"+"-fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5,10,5,10));
                hBox.getChildren().add(textFlow);
                vbox_messages.getChildren().add(hBox);
            }
        }
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
    public static void addLabel(String msgFromServer,VBox vBox){
        HBox hBox=new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text text=new Text(msgFromServer);
        Font font = new Font("Arial", 24);
        text.setFont(font);
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"+"-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}