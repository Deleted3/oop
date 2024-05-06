package Firebase;

import Model.Message;
import Model.User;
import com.example.messengerclient.Main;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.google.api.core.ApiFuture;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CRUDfirebase {
    private boolean key;
    private ObservableList<Message>listMessages= FXCollections.observableArrayList();
    private ObservableList<User>listUsers=FXCollections.observableArrayList();
    public ObservableList<Message>getListMessages(){
        return listMessages;
    }
    public ObservableList<User>getListUsers(){
        return listUsers;
    }
    public boolean readMessageFirebase(String DocumentID){
        key=false;
        ApiFuture<QuerySnapshot>future= Main.db.collection(DocumentID).orderBy("timestamp", Query.Direction.ASCENDING).get();
        try{
            List<QueryDocumentSnapshot> documents=future.get().getDocuments();
            if(documents.size()>0){
                System.out.println("Reading data...");

                for(QueryDocumentSnapshot document:documents){
                    System.out.println(document.getId()+"=> "+document.getData().get("message"));
                    listMessages.add(new Message(document.getData().get("message").toString(),null,document.getData().get("id").toString()));
                }
            }else {
                System.out.println("Don't have any data");
            }
            key=true;
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        return  key;
    }
    public boolean readUserFirebase(){
        key=false;
        ApiFuture<QuerySnapshot>future= Main.db.collection("User").orderBy("id", Query.Direction.ASCENDING).get();
        try{
            List<QueryDocumentSnapshot> documents=future.get().getDocuments();
            if(documents.size()>0){
                System.out.println("Reading data...");
                listUsers.clear();
                for(QueryDocumentSnapshot document:documents){
                    System.out.println(document.getId()+"=> "+document.getData().get("fullname"));
                    listUsers.add(new User(document.getData().get("id").toString(),document.getData().get("fullname").toString(),document.getData().get("password").toString()));
                }
            }else {
                System.out.println("Don't have any data");
            }
            key=true;
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        return  key;
    }
    public int findID(String id,String fullname,String password){
        return Collections.binarySearch(listUsers, new User(id, fullname, password), (user1, user2) -> user1.getId().compareTo(user2.getId()));
    }
    public boolean findUser(String id,String fullname,String password){
        key=false;
        int index = Collections.binarySearch(listUsers, new User(id, fullname, password), (user1, user2) -> user1.getId().compareTo(user2.getId()));

        if (index >= 0) {
            User user=listUsers.get(index);
            if(user.getName().equals(fullname)&&user.getPassword().equals(password)){
                key=true;
            }
            System.out.println("Phần tử được tìm thấy tại vị trí: " + index);
        } else {
            System.out.println("Phần tử không được tìm thấy trong danh sách.");
        }
        return key;
    }
    public boolean addAccToFirebase(String id,String fullname,String password,String avatar){
        key=false;
        Map<String, Object> docUser = new HashMap<>();
        docUser.put("id",id);
        docUser.put("fullname",fullname);
        docUser.put("password",password);
        docUser.put("avatar",avatar);
        ApiFuture<WriteResult> future = Main.db.collection("User").document(id).set(docUser);

        try {
            System.out.println("Update time : " +future.get().getUpdateTime());
            key=true;
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return  key;
    }

}
