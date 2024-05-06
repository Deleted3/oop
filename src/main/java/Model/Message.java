package Model;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class Message {
    private String text;
    private Image image;
    private String sender_id;

    public Message(String text, String imageString, String sender_id) {
        this.text = text;
        this.image = stringToImage(imageString);
        this.sender_id = sender_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String imageString) {
        this.image = stringToImage(imageString);
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
    private Image stringToImage(String imageString){
        if (imageString != null && !imageString.isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            return new Image(byteArrayInputStream);
        } else {
            return null;
        }
    }
}
