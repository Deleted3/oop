package com.example.messengerclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class FriendsItem {
    @FXML
    private ImageView imageView;
    @FXML
    private Label label;
    public void setFriend(String name, Image image){
        imageView.setImage(image);
        label.setText(name);
    }
    public Label getLabel(){return this.label;}
    public ImageView getImageView(){return this.imageView;}

}
