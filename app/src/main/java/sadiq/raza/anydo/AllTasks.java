package sadiq.raza.anydo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sadiq.raza.anydo.AddActivity;
import sadiq.raza.anydo.Fragments.MyCalendar;
import sadiq.raza.anydo.OnBackPressed;
import sadiq.raza.anydo.R;
import sadiq.raza.anydo.MainActivity;
import sadiq.raza.anydo.Task;
import sun.bob.mcalendarview.vo.DateData;

public class AllTasks extends Fragment implements OnBackPressed, View.OnClickListener {


    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private EditText addEditText;
    private ImageButton add,addToday,addTomorrow,addUpcoming;
    boolean doubleBackToExitPressedOnce = false;
    private TextView laterToday,thisEvening,tomorrowMorning,nextWeek,Someday,Custom;
    private HorizontalScrollView scrollView;
    SharedPreferences sharedPreferences;
    private TextView today,tomorrow,upcoming;
    private ArrayList<MyDs> myDs,myDs1,myDs2;

    private String txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public AllTasks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_task, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        sharedPreferences=Objects.requireNonNull(getActivity()).getSharedPreferences("db",Context.MODE_PRIVATE);

        today=view.findViewById(R.id.todayTv);
       final HashMap<String ,String > map=MainActivity.hm;
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDs= new ArrayList<MyDs>();
                for(Map.Entry<String,String> e : map.entrySet())
                {
                    String Task="";
                    String time="";
                    int yy=0,mm=0,dd=0;
                    Task=e.getKey();
                    String arr[]=e.getValue().split("-");
                    yy=Integer.parseInt(arr[2]);
                    mm=Integer.parseInt(arr[1]);
                    dd=Integer.parseInt(arr[0]);
                    time=arr[3];
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c);
                    String ddd="";
                    if(mm<10)
                         ddd=""+dd+"-0"+mm+"-"+yy;
                    else
                        ddd=""+dd+"-"+mm+"-"+yy;
                    if(formattedDate.equals(ddd))
                        myDs.add(new MyDs(Task,time,yy,mm,dd));
                    Log.e("date",formattedDate+" : "+ddd);

                    //mCalendarView.setMarkStyle(MarkStyle.BACKGROUND);
                }
                StringBuilder sb = new StringBuilder("");
                boolean flag=false;
                if(myDs==null)
                    return;
                for(MyDs d : myDs)
                {
                    {
                        flag = true;
                        sb.append(d.getTask());
                        sb.append("\n");
                        sb.append("At : ");
                        sb.append(d.getTime());
                        sb.append("\n");
                        sb.append("\n");

                    }
                }
                if(sb.length()<2)
                    sb.append("No Task on this day ");
                //Toast.makeText(getContext(),sb.toString(), Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getContext())
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = Objects.requireNonNull(inflater).inflate(R.layout.popup,null);
                ((TextView)layout.findViewById(R.id.textView)).setText(sb.toString());
                float density=getContext().getResources().getDisplayMetrics().density;
                // create a focusable PopupWindow with the given layout and correct size
                final PopupWindow pw = new PopupWindow(layout, (int)density*240, (int)density*285, true);
                //Button to close the pop-up
                ((Button) layout.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
                pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            //pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                pw.showAtLocation(layout,Gravity.CENTER,0,0);

            }
        });

        view.findViewById(R.id.tomorrowTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDs= new ArrayList<MyDs>();
                for(Map.Entry<String,String> e : map.entrySet())
                {
                    String Task="";
                    String time="";
                    int yy=0,mm=0,dd=0;
                    Task=e.getKey();
                    String arr[]=e.getValue().split("-");
                    yy=Integer.parseInt(arr[2]);
                    mm=Integer.parseInt(arr[1]);
                    dd=Integer.parseInt(arr[0]);
                    time=arr[3];
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE,1);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c.getTime());
                    String ddd="";
                    if(mm<10 && dd>=10)
                        ddd=""+dd+"-0"+mm+"-"+yy;
                    else if(mm<10)
                        ddd="0"+dd+"-0"+mm+"-"+yy;
                    else if(dd < 10)
                        ddd="0"+dd+"-"+mm+"-"+yy;
                    else
                        ddd=""+dd+"-"+mm+"-"+yy;
                    if(formattedDate.equals(ddd))
                        myDs.add(new MyDs(Task,time,yy,mm,dd));
                    Log.e("date22",formattedDate+" : "+ddd);

                    //mCalendarView.setMarkStyle(MarkStyle.BACKGROUND);
                }
                StringBuilder sb = new StringBuilder("");
                boolean flag=false;
                if(myDs==null)
                    return;
                for(MyDs d : myDs)
                {
                    {
                        flag = true;
                        sb.append(d.getTask());
                        sb.append("\n");
                        sb.append("At : ");
                        sb.append(d.getTime());
                        sb.append("\n");
                        sb.append("\n");

                    }
                }
                if(sb.length()<2)
                    sb.append("No Task on this day ");
                //Toast.makeText(getContext(),sb.toString(), Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getContext())
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = Objects.requireNonNull(inflater).inflate(R.layout.popup,null);
                ((TextView)layout.findViewById(R.id.textView)).setText(sb.toString());
                float density=getContext().getResources().getDisplayMetrics().density;
                // create a focusable PopupWindow with the given layout and correct size
                final PopupWindow pw = new PopupWindow(layout, (int)density*240, (int)density*285, true);
                //Button to close the pop-up
                ((Button) layout.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        pw.dismiss();
                    }
                });
                pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            //pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                pw.showAtLocation(layout,Gravity.CENTER,0,0);

            }
        });

        view.findViewById(R.id.upcomingTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MyCalendar());
            }
        });
        toolbar.inflateMenu(R.menu.all_task_menu_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        toolbar.setOverflowIcon(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),R.drawable.menu));

        addEditText = view.findViewById(R.id.add_edittext);
        add = view.findViewById(R.id.addButton);
        scrollView = view.findViewById(R.id.horizontalScroll);

        addEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    add.setBackgroundResource(R.drawable.up);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String taskt = addEditText.getText().toString();
                            if(taskt.length()<2)
                                Toast.makeText(getContext(), "Please add a valid task detail", Toast.LENGTH_SHORT).show();
                            else
                            {

                                    final Calendar c = Calendar.getInstance();
                                    mYear = c.get(Calendar.YEAR);
                                    mMonth = c.get(Calendar.MONTH);
                                    mDay = c.get(Calendar.DAY_OF_MONTH);


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                            new DatePickerDialog.OnDateSetListener() {

                                                @Override
                                                public void onDateSet(DatePicker view, int year,
                                                                      int monthOfYear, int dayOfMonth) {

                                                    txtDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                                    Toast.makeText(getContext(), txtDate, Toast.LENGTH_SHORT).show();
                                                    final Calendar c = Calendar.getInstance();
                                                    mHour = c.get(Calendar.HOUR_OF_DAY);
                                                    mMinute = c.get(Calendar.MINUTE);

                                                    // Launch Time Picker Dialog
                                                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                                            new TimePickerDialog.OnTimeSetListener() {

                                                                @Override
                                                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                                                      int minute) {

                                                                    txtTime = hourOfDay + ":" + minute;
                                                                    Toast.makeText(getContext(), txtTime, Toast.LENGTH_SHORT).show();
                                                                    //HashMap<String,String >hm = new HashMap<>();
                                                                    //hm.put(taskt,txtDate+txtTime);
                                                                    Task t = new Task(getContext());
                                                                    String arr[]=(txtDate+"-"+txtTime).split("-");
                                                                    int yy=Integer.parseInt(arr[2]);
                                                                    int mm=Integer.parseInt(arr[1]);
                                                                    int dd=Integer.parseInt(arr[0]);
                                                                    String time =arr[3];
                                                                    t.saveMap(taskt,txtDate+"-"+txtTime);
                                                                    Log.e("savibnggggg","saved");
                                                                    AlarmReceiver am = new AlarmReceiver();
                                                                    String dte=""+dd+"-"+mm+"-"+yy;
                                                                    Log.e("ffff",dte+"  ;  "+time);
                                                                    Intent intent= new Intent("ALARM");
                                                                    String arr2[]={taskt,dte,time};
                                                                    intent.putExtra("TaskDetail",arr2);
                                                                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                                                                    //new AlarmReceiver().onReceive(getContext(),intent);
                                                                    addEditText.setText("");
                                                                    Toast.makeText(getContext(),"Task saved successfully",Toast.LENGTH_SHORT).show();
                                                                    Activity ac =getActivity();
                                                                    ac.finish();
                                                                    startActivity(ac.getIntent());

                                                                }
                                                            }, mHour, mMinute, false);
                                                    timePickerDialog.show();

                                                }
                                            }, mYear, mMonth, mDay);
                                    datePickerDialog.show();
                                }
                                /*if (v == btnTimePicker) {

                                    // Get Current Time
                                    final MyCalendar c = MyCalendar.getInstance();
                                    mHour = c.get(MyCalendar.HOUR_OF_DAY);
                                    mMinute = c.get(MyCalendar.MINUTE);

                                    // Launch Time Picker Dialog
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                                            new TimePickerDialog.OnTimeSetListener() {

                                                @Override
                                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                                      int minute) {

                                                    txtTime.setText(hourOfDay + ":" + minute);
                                                }
                                            }, mHour, mMinute, false);
                                    timePickerDialog.show();
                                }
                            }*/

                        }
                    });
                    scrollView.setVisibility(View.VISIBLE);
                }else{
                    add.setBackgroundResource(R.drawable.add_item);
                    scrollView.setVisibility(View.GONE);
                }

            }
        });
        addEditText.clearFocus();

        addToday = view.findViewById(R.id.addToday);
        addToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddActivity.class));
            }
        });

        addTomorrow = view.findViewById(R.id.addTomorrow);
        addTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddActivity.class));
            }
        });

        addUpcoming = view.findViewById(R.id.addUpcoming);
        addUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddActivity.class));
            }
        });


        laterToday = view.findViewById(R.id.laterTody);
        laterToday.setOnClickListener(this);
        tomorrowMorning = view.findViewById(R.id.tomorrowMorning);
        tomorrowMorning.setOnClickListener(this);
        thisEvening = view.findViewById(R.id.thisEvening);
        thisEvening.setOnClickListener(this);
        nextWeek = view.findViewById(R.id.nextWeek);
        nextWeek.setOnClickListener(this);
        Someday = view.findViewById(R.id.Someday);
        Someday.setOnClickListener(this);
        Custom = view.findViewById(R.id.Custom);
        Custom.setOnClickListener(this);


        return view;
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.laterTody:
                    laterToday.setBackgroundResource(R.drawable.text_background_selected);
                    laterToday.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.thisEvening:

                thisEvening.setBackgroundResource(R.drawable.text_background_selected);
                thisEvening.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.tomorrowMorning:
                tomorrowMorning.setBackgroundResource(R.drawable.text_background_selected);
                tomorrowMorning.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.nextWeek:
                nextWeek.setBackgroundResource(R.drawable.text_background_selected);
                nextWeek.setTextColor(getResources().getColor(R.color.white));

                break;

            case R.id.Someday:
                Someday.setBackgroundResource(R.drawable.text_background_selected);
                Someday.setTextColor(getResources().getColor(R.color.white));

                break;

            case R.id.Custom:
                Custom.setBackgroundResource(R.drawable.text_background_selected);
                Custom.setTextColor(getResources().getColor(R.color.white));

                break;
        }
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}