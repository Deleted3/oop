package Model;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Comparator;

public class User {
    private String name;
    private String id;
    private Image avatar=null;
    private String password;
    private boolean isOline;

    public User(String id, String name, String password,String avatarString) {
        this.name = name;
        this.id = id;
        this.avatar = stringToAvatar(avatarString);
        this.password=password;
    }
    public User(String id,String name,String password){
        this.name=name;
        this.id=id;
        this.password=password;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatarString) {
        this.avatar = stringToAvatar(avatarString);
    }

    public boolean isOline() {
        return isOline;
    }

    public void setOline(boolean oline) {
        isOline = oline;
    }
    private Image stringToAvatar(String imageString){
        Image image=null;
        byte[]imageBytes= Base64.getDecoder().decode(imageString);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(imageBytes);
        image=new Image(byteArrayInputStream);
        return image;
    }

}
