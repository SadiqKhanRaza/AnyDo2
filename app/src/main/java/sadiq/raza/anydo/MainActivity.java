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
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import  java.util.Calendar;
import java.util.Objects;

import sadiq.raza.anydo.Fragments.Settings;
import sadiq.raza.anydo.Fragments.MyCalendar;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Fragment fragment;
    BottomNavigationView navigation;
    public  static HashMap<String,String > hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean alarm = (PendingIntent.getBroadcast(this, 0, new Intent("ALARM"), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarm) {
            Intent itAlarm = new Intent("ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, itAlarm, 0);
            java.util.Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            Objects.requireNonNull(alarme).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);
        }
//        toolbar = findViewById(R.id.toolbar);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        loadFragment(new AllTasks());





//        toolbar.setTitle("All Tasks");
//        toolbar.setBackgroundDrawable(new ColorDrawable(getResources()
//                .getColor(R.color.white)));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_Task:
//                    toolbar.setTitle("All Task");
                    fragment = new AllTasks();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_Calendar:
                    loadFragment(new MyCalendar());
                    Task t = new Task(MainActivity.this);
                    hm = t.loadMap();
                    //Toast.makeText(MainActivity.this, " map  : "+hm, Toast.LENGTH_SHORT).show();
//                    toolbar.setTitle("MyCalendar");
                    return true;
                case R.id.navigation_Settings:
                    loadFragment(new Settings());
//                    toolbar.setTitle("Settings");
                    return true;

            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
