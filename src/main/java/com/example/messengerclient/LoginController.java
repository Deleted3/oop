package com.example.messengerclient;

import Model.Owner;
import Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField fullname;
    @FXML
    private TextField password;
    @FXML
    private TextField id;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Main.cruDfirebase.readUserFirebase()){
            System.out.println("Read success user");
        }else{
            System.out.println("Can't read user");
        }
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StageAndScene.showRegisterScene();
            }
        });
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!fullname.getText().equals("")&&!password.getText().equals("")&&!id.getText().equals("")){
                    if(Main.cruDfirebase.findUser(id.getText().trim(),fullname.getText().trim(), password.getText())){
                        ObservableList<User>listUser=Main.cruDfirebase.getListUsers();
                        int index=Main.cruDfirebase.findID(id.getText().trim(),fullname.getText().trim(),password.getText().trim());
                        User user=listUser.get(index);
                        Controller.owner=new Owner(user.getName(), user.getId(), user.getAvatar());
                        StageAndScene.showMainScene();
                        System.out.println("Account is correct!!");
                    }else{
                        System.out.println("Account isn't correct!!");
                    }
                }
            }
        });
    }
}
