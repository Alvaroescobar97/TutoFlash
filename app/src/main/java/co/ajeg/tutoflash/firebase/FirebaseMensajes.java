package co.ajeg.tutoflash.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.firebase.autenticacion.Autenticacion;
import co.ajeg.tutoflash.firebase.database.DBROUTES;
import co.ajeg.tutoflash.model.User;
import co.ajeg.tutoflash.notificacion.FCMMessage;
import co.ajeg.tutoflash.notificacion.NotificacionUtil;

public class FirebaseMensajes {

    private MainActivity mainActivity;
    public static FirebaseMensajes thisClass;

    public static FirebaseMensajes getIntance(MainActivity mainActivity){
        FirebaseMensajes firebaseMensajes;
        if(thisClass == null){
            thisClass = new FirebaseMensajes(mainActivity);
        }
        return thisClass;
    }

    public FirebaseMensajes(MainActivity mainActivity){
        this.mainActivity = mainActivity;

        User user = Autenticacion.getUser();
        if(user != null){


            FirebaseMessaging.getInstance().subscribeToTopic(user.getId() + "/" + DBROUTES.MENSAJES).addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    Log.e(">>>>", "Subscripcion exitosa");
                }else{
                    Log.e(">>>>", "Subscripcion NO exitosa");
                }
            });
        }
    }

    public void sendNotificacion(String userId, String titulo, String message){
      //  FCMMessage fcmMessage = new
      //  NotificacionUtil.createNotification(this.mainActivity, titulo, message);
    }
}
