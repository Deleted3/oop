package com.example.messengerclient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button registerButton;
    @FXML
    private Button returnButton;
    @FXML
    private TextField fullname;
    @FXML
    private TextField password;
    @FXML
    private TextField id;
    @FXML
    private Label label;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!fullname.getText().equals("")&&!password.getText().equals("")&&!id.getText().equals("")){

                    if(Main.cruDfirebase.findID(id.getText().trim(),fullname.getText().trim(), password.getText())>=0){
                        label.setText("Account is existed !");
                    }else{

                        try {
                            BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/avtar.png"));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(bufferedImage, "png", baos);
                            byte[] imageData = baos.toByteArray();
                            String encodedString = Base64.getEncoder().encodeToString(imageData);
                            Main.cruDfirebase.addAccToFirebase(id.getText().trim(),fullname.getText().trim(),password.getText().trim(),encodedString);
                            label.setText("Account is registered successfully");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StageAndScene.showLoginScene();
            }
        });
    }
}
