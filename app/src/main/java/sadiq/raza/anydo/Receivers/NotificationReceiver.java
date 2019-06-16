package sadiq.raza.anydo.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import sadiq.raza.anydo.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
