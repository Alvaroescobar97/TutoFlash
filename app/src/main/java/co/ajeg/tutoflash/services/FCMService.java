package co.ajeg.tutoflash.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Log.e(">>>>>>",remoteMessage.getData().toString());

        JSONObject object = new JSONObject(remoteMessage.getData());
        String json = object.toString();

        Gson gson = new Gson();

    }

}
