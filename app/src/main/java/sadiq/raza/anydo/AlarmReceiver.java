package sadiq.raza.anydo;

import android.annotation.SuppressLint;
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
import java.util.concurrent.CopyOnWriteArrayList;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Hello Saubhik Singh", Toast.LENGTH_SHORT).show();

        String date="26-05-2019",hour="16:03",h2="00:52";
        String[] taskString = intent.getStringArrayExtra("TaskDetail");
        if(taskString==null)
            return;
        Log.e("taskString ","ll"+taskString[1]+" , "+taskString[2]);
        String s1 =taskString[1].substring(0,3);
        StringBuilder sb = new StringBuilder(s1);
        sb.append("0");
        sb.append(taskString[1].substring(3,taskString[1].length()));
        String s4=sb.toString();
        Log.e("sb",s4);

        createTaskNoti(context,taskString[0]+"ss",s4,taskString[2]);
        createTaskNoti(context,"hi there dfothids",date,hour);

    }
    public void createNotification (Context context, Intent intent, CharSequence
            ticker, CharSequence title, CharSequence descricao){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(context, (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(descricao);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(p);
        Notification n = builder.build();
        //create the notification
        n.vibrate = new long[]{150, 300, 150, 400};
        //n.flags = Notification.FLAG_AUTO_CANCEL;
        if (nm != null) {
            nm.notify(R.drawable.icon_any_do, n);
        }
        //create a vibration
        try {

            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();
        } catch (Exception e) {
        }
    }
    public void createTaskNoti(Context context,String tsk,String dt,String hr)
    {
        try {
            Log.e("Trying","tryyy");
            Date d = new Date();
            Date c = Calendar.getInstance().getTime();

            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c);
            @SuppressLint("SimpleDateFormat") DateFormat hour = new SimpleDateFormat("HH:mm:ss");
            Log.e("Datetime",""+formattedDate+"d :  "+d+" d.getTime : "+d.getTime());
            //if (formattedDate.equals(dt) && hr.equals(d.toString().substring(11,16))) {
                Intent it = new Intent(context, MainActivity.class);
                createNotification(context, it, "AnyDo Notify", "Today's Task", tsk);
                return;
            //}
        } catch (Exception e) {
            Log.i("date", "error == " + e.getMessage());
        }
    }

}
