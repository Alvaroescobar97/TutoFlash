package co.ajeg.tutoflash.firebase.database.manager;

import android.app.Notification;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.CollectionReference;
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

    private static CollectionReference getRefMyCollectionAllNotificaciones() {
        User user = Autenticacion.getUser();
        CollectionReference collectionReference = null;
        if(user != null){
            getRefCollectionAllNotificaciones(user.getId());
        }
        return collectionReference;
    }

    private static CollectionReference getRefCollectionAllNotificaciones(String userId){
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
