package sadiq.raza.anydo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "ALARM", Toast.LENGTH_LONG).show();

        try {
            String yourDate = "19/05/2019";
            String yourHour = "14:40:00";
            Date d = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyyHH:mm:ss");
            sd.format(yourDate+yourHour);
            Log.e("datetime",yourDate+yourHour+Calendar.getInstance().getTime());
            DateFormat date = DateFormat.getDateInstance();
            DateFormat hour =DateFormat.getTimeInstance();
            if (sd.equals(Calendar.getInstance().getTime())){
                Intent it =  new Intent(context, MainActivity.class);
                createNotification(context, it, "new mensage", "body!", "this is a mensage");
            }
        }catch (Exception e){
            Log.i("date","error == "+e.getMessage());
        }
    }


    public void createNotification(Context context, Intent intent, CharSequence ticker, CharSequence title, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.ic_bell);
        builder.setContentIntent(p);
        Notification n = builder.build();
        //create the notification
        n.vibrate = new long[]{150, 300, 150, 400};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        Objects.requireNonNull(nm).notify(R.drawable.ic_check, n);
        //create a vibration
        try{

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
