package com.example.messengerclient;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;

public class Client {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public Client(Socket socket){
        try{
            this.socket=socket;
            this.dataInputStream=new DataInputStream(socket.getInputStream());
            this.dataOutputStream=new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("Set id");
            dataOutputStream.writeUTF(Controller.owner.getId());
        }catch (IOException exception){
            System.out.println("Error connecting to server");
            exception.printStackTrace();
        }
    }
    public void sendImageToServer(Image image,String format){
        try{
            BufferedImage bufferedImage= SwingFXUtils.fromFXImage(image,null);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,format, byteArrayOutputStream);
            byte[] imageData=byteArrayOutputStream.toByteArray();
            dataOutputStream.writeUTF(Controller.receiver_id);
            dataOutputStream.writeUTF("IMAGE");
            dataOutputStream.writeInt(imageData.length);
            dataOutputStream.write(imageData,0,imageData.length);
            dataOutputStream.flush();
        }catch (IOException e){
            closeEveryThing(socket,dataInputStream,dataOutputStream);
            System.out.println("Error sending image to server");
            e.printStackTrace();
        }
    }
    public void receiveInformationFromServer(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        //Đọc id người gửi
                        String senderID=dataInputStream.readUTF();
                        //Đọc kiểu dữ liệu
                        String dataType=dataInputStream.readUTF();
                        if(dataType.equals("IMAGE")) {
                            int length = dataInputStream.readInt();
                            byte[] imageData = new byte[length];
                            dataInputStream.readFully(imageData, 0, length);
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
                            Image image = new Image(byteArrayInputStream);
                            if(senderID.equals(Controller.receiver_id)){
                                Controller.addLabel1(image, vBox);
                            }
                        }
                        else {
                            String messageFromServer=dataInputStream.readUTF();
                            if(senderID.equals(Controller.receiver_id)){
                                Controller.addLabel(messageFromServer,vBox);
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving information from server");
                        closeEveryThing(socket,dataInputStream,dataOutputStream);
                    }
                }
            }
        }).start();
    }
    public void sendMessageToServer(String messageToServer){
        try {
            dataOutputStream.writeUTF(Controller.receiver_id);
            dataOutputStream.writeUTF("MESSAGE");
            dataOutputStream.writeUTF(messageToServer);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message tho the client");
            closeEveryThing(socket,dataInputStream,dataOutputStream);
        }
    }
    public void closeEveryThing(Socket socket,DataInputStream dataInputStream,DataOutputStream dataOutputStream){
        try{
            if(socket!=null){
                socket.close();
            }
            if(dataOutputStream!=null){
                dataOutputStream.close();
            }
            if(dataInputStream!=null){
                dataInputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
