package com.example.messengerclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StageAndScene {
    public static Stage stage;
    private static void showStage(Scene scene) {
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Messenger Client");
        Image icon= new Image("icons8-facebook-messenger-50.png");
        stage.getIcons().add(icon);
        stage.show();
    }
    private static Scene loadScene(String path){
        Scene scene=null;
        try{
            scene=new Scene(FXMLLoader.load(StageAndScene.class.getResource(path)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return scene;
    }
    public static void showLoginScene(){
        showStage(loadScene("login.fxml"));
    }
    public static void showMainScene(){
        showStage(loadScene("boxChat.fxml"));
    }
    public static void showRegisterScene(){
        showStage(loadScene("register.fxml"));
    }
}
