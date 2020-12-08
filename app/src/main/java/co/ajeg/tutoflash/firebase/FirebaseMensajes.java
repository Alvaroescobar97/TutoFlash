package co.ajeg.tutoflash.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.UUID;

import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.comm.HTTPSWebUtilDomi;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.galeria.UtilDomi;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.notificacion.FCMMessage;
import co.ajeg.tutoflash.notificacion.FCMWrapper;
import co.ajeg.tutoflash.notificacion.NotificacionUtil;

public class FirebaseMensajes {

    private static MainActivity mainActivity;
    public static FirebaseMensajes thisClass;
    private static HTTPSWebUtilDomi httpsWebUtilDomi;

    public static FirebaseMensajes getInstance(MainActivity mainActivity) {
        FirebaseMensajes firebaseMensajes;
        if (thisClass == null) {
            thisClass = new FirebaseMensajes(mainActivity);
        }
        return thisClass;
    }

    public FirebaseMensajes(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.httpsWebUtilDomi = new HTTPSWebUtilDomi();
    }

    public void suscribir(OnCompleteListenerSubscribe onCompleteListenerSubscribe) {
        User user = Autenticacion.getUser();
        this.mainActivity.runOnUiThread(() -> {
            if (user != null) {
                FirebaseMessaging.getInstance().subscribeToTopic(user.getId()).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        onCompleteListenerSubscribe.onLoadSubscribe(true);
                    } else {
                        onCompleteListenerSubscribe.onLoadSubscribe(false);
                    }
                });
            }else{

            }
        });
    }

    public static void desuscribirse(OnCompleteListenerSubscribe onCompleteListenerSubscribe){
        mainActivity.runOnUiThread(()->{
            User user = Autenticacion.getUser();
            if(user != null){
                FirebaseMessaging.getInstance().unsubscribeFromTopic(user.getId()).addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        onCompleteListenerSubscribe.onLoadSubscribe(true);
                    } else {
                        onCompleteListenerSubscribe.onLoadSubscribe(false);
                    }
                });
            }else{
                onCompleteListenerSubscribe.onLoadSubscribe(false);
            }
        });
    }

    public static void sendNotificacion(String userId, String titulo, String message) {
        new Thread(() -> {
            String uid = UUID.randomUUID().toString();
            FCMMessage fcmMessage = new FCMMessage(uid, titulo, message);
            Gson gson = new Gson();
            FCMWrapper fcmWrapper = new FCMWrapper("/topics/" + userId, fcmMessage);
            String json = gson.toJson(fcmWrapper);
            httpsWebUtilDomi.POSTtoFCM(json);
        }).start();

    }

    public interface OnCompleteListenerSubscribe {
        void onLoadSubscribe(boolean task);
    }
}
