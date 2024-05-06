package Firebase;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;
public class ConnectionFirebase {
    public Firestore StartFirebase(){
        FirebaseOptions options= null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("/Firebase/bai1-28a89-firebase-adminsdk-wbymg-f5c817cfad.json")))
                    .setDatabaseUrl("https://bai1-28a89-default-rtdb.firebaseio.com")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Error initializing Firebase",e);
        }
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}
