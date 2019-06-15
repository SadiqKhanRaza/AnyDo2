package sadiq.raza.anydo;

import android.app.AlarmManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import sadiq.raza.anydo.Receivers.AlarmReceiver;
import sadiq.raza.anydo.Utils.AlarmUtils;
import sadiq.raza.anydo.Utils.NotificationUtils;

public class Test2 extends AppCompatActivity {

    Calendar cal;
    DatePicker datePicker;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        cal = Calendar.getInstance();

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);





        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAlarm();
                //NotificationUtils.PostNotification(Test2.this);

                Toast.makeText(Test2.this, "Alarm set", Toast.LENGTH_SHORT).show(); //datePicker.getYear();


            }
        });
    }

    private void callAlarm(){
        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra("title","My apna title");
        intent.putExtra("description","Ye thoda bada wala description");
        cal.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timePicker.getHour(),timePicker.getMinute(),0);




        int r = new Random().nextInt(1000);

        AlarmUtils.setAlarm(this,intent,r,cal);
    }
}
