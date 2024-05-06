package com.example.messengerclient;

import Firebase.CRUDfirebase;
import Firebase.ConnectionFirebase;
import com.google.cloud.firestore.*;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class Main extends Application {
    private final ConnectionFirebase connectionFirebase=new ConnectionFirebase();
    public static Firestore db;
    public static CRUDfirebase cruDfirebase=new CRUDfirebase();
    private boolean isFirstCall=true;
    @Override
    public void start(Stage stage) throws IOException {
        db=connectionFirebase.StartFirebase();
        StageAndScene.stage=stage;
        StageAndScene.showLoginScene();
        db.collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirestoreException e) {
                if (e != null) {
                    System.out.println("Listen failed: " + e);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        if(!isFirstCall){
                            System.out.println("New user: " + dc.getDocument().getData());
                            cruDfirebase.readUserFirebase();
                        }
                    }
                    isFirstCall =false;
                } else {
                    System.out.println("Current data: null");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}