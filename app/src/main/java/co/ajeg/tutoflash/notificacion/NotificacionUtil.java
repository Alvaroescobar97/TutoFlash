package co.ajeg.tutoflash.notificacion;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import co.ajeg.tutoflash.R;

public class NotificacionUtil {

    private static final String CHANNEL_ID = "messages";
    private static final String CHANNEL_NAME = "Mensaje";
    private static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
    private static  int idCounter = 0;

    public static void createNotification(Context context, String titulo, String mensaje){
        createNotification(context, titulo, mensaje, null);
    }

    public static void createNotification(Context context, String titulo, String mensaje, Intent intent){

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = null;
        if(intent != null){
            PendingIntent pendingIntent = PendingIntent.getActivity(context, idCounter, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder=  new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

        }else{
            builder=  new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
        }



        Notification notification = builder.build();
        manager.notify(idCounter, notification);
        idCounter++;
    }

}
