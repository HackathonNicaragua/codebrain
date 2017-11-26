package com.codebrain.minato.tragua;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by edwin on 25/11/2017.
 */

public class NotificationWrapper {

    public void SetNotification (Context context, String text){
        NotificationCompat.Builder mBuilder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        int Icono = R.drawable.logo_tragua;
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(Icono)
                .setContentTitle("Notification!")
                .setContentText(text)
                .setContentInfo("4")
                .setTicker("Alerta!")
                .setVibrate(new long[] {100,250,100,500})
                .setAutoCancel(true);

        notificationManager.notify(1,mBuilder.build());
    }

}
