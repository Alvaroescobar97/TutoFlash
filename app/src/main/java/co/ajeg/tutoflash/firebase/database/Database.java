package co.ajeg.tutoflash.firebase.database;

import android.app.Notification;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.List;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

public class Database {

    AppCompatActivity appCompatActivity;

    public Database(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
        this.getCurrentUser();
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
                    if(user.getImage() != null && user.getImage().equals("") == false && userDatabase.getImage().contains(DBROUTES.USERS_IMAGES) == false){
                        userDatabase.setImage(user.getImage());
                    }
                    Autenticacion.user = user;
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
            Autenticacion.user = user;
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


    public void createNotificacion(Notificacion notificacion, OnCompleteListenerDatabase onCompleteListenerDatabase){
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        User user = Autenticacion.getUser();
        if(user != null) {
            fs.collection(DBROUTES.USERS).document(user.getId()).collection(DBROUTES.USERS_NOTIFICACIONES).
                    document(notificacion.getId()).set(notificacion).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            onCompleteListenerDatabase.onLoad(true);
                        }else{
                            onCompleteListenerDatabase.onLoad(null);
                        }
            });
        }
    }


    public void getRefNotificaciones(OnListenerNotificaciones onListenerNotificaciones){
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        User user = Autenticacion.getUser();
        if(user != null){
            fs.collection(DBROUTES.USERS).document(user.getId()).collection(DBROUTES.USERS_NOTIFICACIONES).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    List<Notificacion> notificacions = value.toObjects(Notificacion.class);
                    onListenerNotificaciones.onLoad(notificacions);
                }
            });
        }
    }


    interface OnListenerNotificaciones{
        void onLoad(List<Notificacion> notificacion);
    }

}
