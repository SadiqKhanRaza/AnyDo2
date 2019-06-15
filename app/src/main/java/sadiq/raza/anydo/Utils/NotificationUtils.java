package sadiq.raza.anydo.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import sadiq.raza.anydo.Receivers.NotificationReceiver;

public class NotificationUtils {


    public static void PostNotification(Context context,String title, String description,int requestCode){

        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1234,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "123")
                .setSmallIcon(android.R.drawable.star_off)
                .setContentTitle(title)
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 400};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("sadiq", "myservice", NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(Color.BLUE);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            assert manager != null;
            manager.createNotificationChannel(channel);
            builder.setChannelId("sadiq");


        }


        assert manager != null;
        manager.notify(123,builder.build());
    }


}
