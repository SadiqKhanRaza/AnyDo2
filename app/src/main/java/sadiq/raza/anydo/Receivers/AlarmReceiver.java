package sadiq.raza.anydo.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import sadiq.raza.anydo.Utils.NotificationUtils;

public class AlarmReceiver extends BroadcastReceiver {
    String title = "Title";
    String description = "Description";
    int requestCode=123;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Hello Saubhik", Toast.LENGTH_LONG).show();
        if(intent!=null){
             title= intent.getStringExtra("title");
            description = intent.getStringExtra("description");
            requestCode = intent.getIntExtra("requestCode",123);
        }
        NotificationUtils.PostNotification(context,title,description,requestCode);
    }
}
