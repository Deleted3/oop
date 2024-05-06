package com.example.messengerclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageAndScene {
    public static Stage stage;
    private static void showStage(Scene scene) {
        stage.setScene(scene);
        stage.centerOnScreen();
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
