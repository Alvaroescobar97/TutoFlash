package co.ajeg.tutoflash.services;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import co.ajeg.tutoflash.activities.MainActivity;
import co.ajeg.tutoflash.notificacion.FCMMessage;
import co.ajeg.tutoflash.notificacion.NotificacionUtil;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Log.e(">>>>>>",remoteMessage.getData().toString());

        JSONObject object = new JSONObject(remoteMessage.getData());
        String json = object.toString();

        Gson gson = new Gson();
        FCMMessage fcmMessage = gson.fromJson(json, FCMMessage.class);

        NotificacionUtil.createNotification(this, fcmMessage.getTitle(), fcmMessage.getMsg(), new Intent(this,  MainActivity.class));

    }

}
