package sadiq.raza.anydo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import  java.util.Calendar;
import java.util.Objects;

import sadiq.raza.anydo.Fragments.AllTasks;
import sadiq.raza.anydo.Fragments.Settings;
import sadiq.raza.anydo.Fragments.MyCalendar;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Fragment fragment;
    public static int requestCode=0;
    BottomNavigationView navigation;
    Task t;
    public  static HashMap<String,String > hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Task t = new Task(MainActivity.this);
        hm = t.loadMap();
        boolean alarm = (PendingIntent.getBroadcast(this, (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE),
                new Intent(Long.toString(System.currentTimeMillis())),PendingIntent.FLAG_ONE_SHOT) == null);

        if(alarm){
            Log.e("Main","Inside Alarm True");
            Intent itAlarm = new Intent("ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,(int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE),itAlarm,0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            Objects.requireNonNull(alarme).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),600, pendingIntent);
        }

//        toolbar = findViewById(R.id.toolbar);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        loadFragment(new AllTasks());


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_Task:
                    fragment = new AllTasks();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_Calendar:
                    hm=null;
                    hm=new Task(MainActivity.this).loadMap();
                    loadFragment(new MyCalendar());
                    return true;
                case R.id.navigation_Settings:
                    loadFragment(new Settings());
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
