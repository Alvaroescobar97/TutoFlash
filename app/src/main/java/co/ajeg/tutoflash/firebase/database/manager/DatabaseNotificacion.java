package co.ajeg.tutoflash.firebase.database.manager;

import android.app.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.model.notificacion.Notificacion;

public class DatabaseNotificacion {


    private static FragmentActivity activity;
    private static DatabaseNotificacion thisClass;
    private ListenerRegistration listenerNotificaciones;

    private DatabaseNotificacion(FragmentActivity activity) {
        this.activity = activity;
    }

    static public DatabaseNotificacion getInstance(FragmentActivity activity) {
        activity = activity;
        if (thisClass == null) {
            thisClass = new DatabaseNotificacion(activity);
        }
        return thisClass;
    }

    public void getAllNotificaciones(OnCompleteListenerAllNotificaciones onCompleteListenerAllNotificaciones){
        activity.runOnUiThread(()->{
            CollectionReference collectionReference = getRefMyCollectionAllNotificaciones();
            if(collectionReference != null){
                collectionReference.addSnapshotListener((value , error)->{
                    List<Notificacion> notificacions = value.toObjects(Notificacion.class);
                    onCompleteListenerAllNotificaciones.onLoadAllNotificaciones(notificacions);
                });
            }else{
                onCompleteListenerAllNotificaciones.onLoadAllNotificaciones(null);
            }
        });
    }

    public void createNotificacion(String para, Notificacion notificacion, OnCompleteListenerNotificacion onCompleteListenerNotificacion){
        activity.runOnUiThread(()->{
            getRefCollectionAllNotificaciones(para).document(notificacion.getId()).set(notificacion).addOnCompleteListener(task -> {
                onCompleteListenerNotificacion.onLoadNotificacion(notificacion);
            });
        });
    }



    public static void getMyNotificacion(AppCompatActivity appCompatActivity, String idNotificaction, OnCompleteListenerNotificacion onCompleteListenerNotificacion){
        User user = Autenticacion.getUser();
        if(user != null){
            getNotificacionUser(appCompatActivity, user.getId(), idNotificaction, onCompleteListenerNotificacion);
        }else{
            onCompleteListenerNotificacion.onLoadNotificacion(null);
        }

    }

    public static void getNotificacionUser(AppCompatActivity appCompatActivity, String userId, String idNotificacion, OnCompleteListenerNotificacion onCompleteListenerNotificacion){
        appCompatActivity.runOnUiThread(()->{
            getRefCollectionAllNotificaciones(userId).document(idNotificacion).get().addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot =  task.getResult();
                    if(documentSnapshot.exists()){
                        onCompleteListenerNotificacion.onLoadNotificacion(documentSnapshot.toObject(Notificacion.class));
                    }else{
                        onCompleteListenerNotificacion.onLoadNotificacion(null);
                    }
                }else{
                    onCompleteListenerNotificacion.onLoadNotificacion(null);
                }
            });
        });
    }


    private static CollectionReference getRefMyCollectionAllNotificaciones() {
        User user = Autenticacion.getUser();
        CollectionReference collectionReference = null;
        if(user != null){
            collectionReference = getRefCollectionAllNotificaciones(user.getId());
        }
        return collectionReference;
    }

    public static CollectionReference getRefCollectionAllNotificaciones(String userId){
        CollectionReference collectionReference = FirebaseFirestore
                .getInstance()
                .collection(DBROUTES.USERS)
                .document(userId)
                .collection(DBROUTES.USERS_NOTIFICACIONES);

        return collectionReference;
    }


    public interface OnCompleteListenerAllNotificaciones{
        void onLoadAllNotificaciones(List<Notificacion> notificacions);
    }

    public interface OnCompleteListenerNotificacion{
        void onLoadNotificacion(Notificacion notificacion);
    }
}
