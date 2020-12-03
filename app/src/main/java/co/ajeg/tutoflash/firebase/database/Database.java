package co.ajeg.tutoflash.firebase.database;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.model.User;

public class Database {

    AppCompatActivity appCompatActivity;

    public Database(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;

        if (Autenticacion.user == null) {
            this.getCurrentUser();
        }
    }

    static public void createUser(User user) {
        createUser(user, null);
    }

    static public void createUser(User user, Autenticacion.OnCompleteListenerUser onCompleteListenerUser) {
        FirebaseFirestore fs = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fs.collection(DBROUTES.USERS).document(user.getId());

        documentReference.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                if(documentSnapshot.exists()){
                    User userDatabase = documentSnapshot.toObject(User.class);
                    if(onCompleteListenerUser != null){
                        onCompleteListenerUser.onLoad(userDatabase);
                    }
                }else{
                    createUserInDatabase(documentReference, user, onCompleteListenerUser);
                }
            }else{
                if(onCompleteListenerUser != null) {
                    onCompleteListenerUser.onLoad(null);
                }
            }

        });
    }

    static private void createUserInDatabase(DocumentReference documentReference, User user, Autenticacion.OnCompleteListenerUser onCompleteListenerUser){
        documentReference.set(user).addOnCompleteListener((task1) -> {
            if (onCompleteListenerUser != null) {
                if (task1.isSuccessful()) {
                    onCompleteListenerUser.onLoad(user);
                } else {
                    onCompleteListenerUser.onLoad(null);
                }
            }
        });
    }

    static private void getUserDatabase(String uid) {
        getUserDatabase(uid, null);
    }

    static public void getUserDatabase(String uid, Autenticacion.OnCompleteListenerUser onCompleteListenerUser) {
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        fs.collection(DBROUTES.USERS).document(uid).get().addOnCompleteListener(
                (task) -> {
                    if (task.isSuccessful()) {
                        User usuario = task.getResult().toObject(User.class);
                        Autenticacion.user = usuario;
                        if (onCompleteListenerUser != null) {
                            onCompleteListenerUser.onLoad(usuario);
                        }
                    } else {
                        if (onCompleteListenerUser != null) {
                            onCompleteListenerUser.onLoad(null);
                        }
                    }
                });
    }

    public static void getCurrentUser() {
        getCurrentUser(null);
    }

    public static void getCurrentUser(Autenticacion.OnCompleteListenerUser onCompleteListenerUser) {
        if (Autenticacion.user == null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String id = user.getUid();
                getUserDatabase(id, onCompleteListenerUser);
            } else {
                onCompleteListenerUser.onLoad(null);
            }
        } else {
            onCompleteListenerUser.onLoad(Autenticacion.user);
        }
    }



}
