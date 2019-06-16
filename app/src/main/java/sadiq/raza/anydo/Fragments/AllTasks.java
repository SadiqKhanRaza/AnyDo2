package sadiq.raza.anydo.Fragments;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;



import org.w3c.dom.Text;

import sadiq.raza.anydo.Adapter.CustomAdapter;
import sadiq.raza.anydo.AddActivity;
import sadiq.raza.anydo.MainActivity;
import sadiq.raza.anydo.MyDs;
import sadiq.raza.anydo.OnBackPressed;
import sadiq.raza.anydo.R;
import sadiq.raza.anydo.Receivers.AlarmReceiver;
import sadiq.raza.anydo.ReminderActivity;
import sadiq.raza.anydo.Task;
import sadiq.raza.anydo.RemainderActivity;
import sadiq.raza.anydo.Utils.AlarmUtils;

public class AllTasks extends Fragment implements OnBackPressed, View.OnClickListener {

    private RecyclerView recyclerViewToday,recyclerViewTomorrow,recyclerViewUpComing,recyclerViewRemainder;
    SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private EditText addEditText;
    private ImageButton add,addToday,addTomorrow,addUpcoming,addRemainder;
    private TextView laterToday,thisEvening,tomorrowMorning,nextWeek,Someday,Custom,todayTv,tomorrowTv,upComingTv,remainderTv;
    private HorizontalScrollView scrollView;
    private ArrayList<MyDs> myDs,myDs1,myDs2,myDs4;
    //private boolean todayTvbool=true,tomorrowTvbool=true,upComingTvbool=true;
   // CustomAdapter customAdapter;

    public LinearLayout layout;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.all_task, container, false);
        addEditText = view.findViewById(R.id.add_edittext);

        layout = view.findViewById(R.id.layout);
//        view.findViewById(R.id.scroll).setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Toast.makeText(getContext(), ""+scrollY, Toast.LENGTH_SHORT).show();
//                if(scrollY>oldScrollY) {
////                    Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_edit);
////                    scrollView.startAnimation(aniSlide);
////                    layout.startAnimation(aniSlide);
//
//                    layout.setVisibility(View.GONE);
//                }else{
////                    Animation aniSlide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_edit);
////                    scrollView.startAnimation(aniSlide);
//
//                    layout.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        toolbar = view.findViewById(R.id.toolbar);

        /*toolbar.inflateMenu(R.menu.all_task_menu_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        toolbar.setOverflowIcon(ContextCompat.getDrawable(getContext(),R.drawable.menu));*/

        todayTv=(TextView)view.findViewById(R.id.todayTv);
        tomorrowTv=(TextView)view.findViewById(R.id.tomorrowTv);
        upComingTv=(TextView)view.findViewById(R.id.upcomingTv);
        remainderTv=(TextView)view.findViewById(R.id.remainderTv);
        recyclerViewToday=(RecyclerView)view.findViewById(R.id.recycler_view_today);
        recyclerViewTomorrow=(RecyclerView)view.findViewById(R.id.recycler_view_tomorrow);
        recyclerViewUpComing=(RecyclerView)view.findViewById(R.id.recycler_view_upcoming);
        recyclerViewRemainder=(RecyclerView)view.findViewById(R.id.recycler_view_remainder);

        todayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewToday.getVisibility() == View.VISIBLE){
                    recyclerViewToday.setVisibility(View.GONE);
                }else {
                    recyclerViewToday.setVisibility(View.VISIBLE);
                }

            }
        });

        tomorrowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewTomorrow.getVisibility() == View.VISIBLE){
                    recyclerViewTomorrow.setVisibility(View.GONE);
                }else {
                    recyclerViewTomorrow.setVisibility(View.VISIBLE);
                }

            }
        });

        upComingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewUpComing.getVisibility() == View.VISIBLE){
                    recyclerViewUpComing.setVisibility(View.GONE);
                }else {
                    recyclerViewUpComing.setVisibility(View.VISIBLE);
                }

            }
        });

        remainderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                if(recyclerViewRemainder.getVisibility() == View.VISIBLE){
                    recyclerViewRemainder.setVisibility(View.GONE);
                }else {
                    recyclerViewRemainder.setVisibility(View.VISIBLE);
                }

            }
        });

          checkAllList();

        sharedPreferences=getActivity().getSharedPreferences("db",Context.MODE_PRIVATE);



        add = view.findViewById(R.id.addButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("message","halo");
                startActivity(intent);
            }
        });
        scrollView = view.findViewById(R.id.horizontalScroll);

        addEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    Animation btnchng = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out_button);
                    add.startAnimation(btnchng);
                    add.setBackgroundResource(R.drawable.up);
//                    Animation aniSlide = AnimationUtils.loadAnimation(getContext(),R.anim.slide_up_list);
//                    scrollView.startAnimation(aniSlide);
//                    scrollView.setVisibility(View.VISIBLE);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getContext(), "dhfkjskdfhkjdsf", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                            final String taskt = addEditText.getText().toString();
                            if(taskt.length()<2)
                                Toast.makeText(getContext(), "Please add a valid task detail", Toast.LENGTH_SHORT).show();
                            else {
                                setAnyTask(taskt);
                               // checkAllList();
                            }
                        }
                    });

                }else{
                    add.setBackgroundResource(R.drawable.add_item);
//                    Animation aniSlide = AnimationUtils.loadAnimation(getContext(),R.anim.slide_down_list);
//                    scrollView.startAnimation(aniSlide);
//                    scrollView.setVisibility(View.GONE);
                }

            }
        });
        addEditText.clearFocus();

        addToday = view.findViewById(R.id.addToday);
        addToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveMethod();
                Date c=Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c);
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("message", formattedDate);
                startActivity(intent);
            }
        });

        addTomorrow = view.findViewById(R.id.addTomorrow);
        addTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 1);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c.getTime());
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("message", formattedDate);
                startActivity(intent);
            }
        });

        addUpcoming = view.findViewById(R.id.addUpcoming);
        addUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddActivity.class);
                intent.putExtra("message","halo");
                startActivity(intent);
            }
        });

        addRemainder=view.findViewById(R.id.addremainder);
        addRemainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ReminderActivity.class);
                intent.putExtra("message","f9a8b1cff3f4479e6fce6cdae3c62017");
                startActivity(intent);
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
        Animation btnchng = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in_button);
        add.startAnimation(btnchng);
        add.setBackgroundResource(R.drawable.add_item);
        Animation aniSlide = AnimationUtils.loadAnimation(getContext(),R.anim.slide_down_list);
        scrollView.startAnimation(aniSlide);
        scrollView.setVisibility(View.GONE);

    }

    public void checkAllList() {
        sameDaySaveMethod();
        customDaySaveMethod();
        nextDaySaveMethod();
        remainderDaySaveMethod();
    }

    public void sameDaySaveMethod()
    {
        final HashMap<String ,String > map=MainActivity.hm;
        myDs= new ArrayList<MyDs>();
        for(Map.Entry<String,String> e : map.entrySet())
        {
            String Task="";
            String time="";
            int yy=0,mm=0,dd=0;
            Task=e.getKey();
            boolean isFound=Task.indexOf("-f9a8b1cff3f4479e6fce6cdae3c62017")!=-1?true:false;
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
                if(dd<10)
                    ddd="0"+dd+"-0"+mm+"-"+yy;
                else
                    ddd=""+dd+"-0"+mm+"-"+yy;
            else
                if(dd<10)
                    ddd="0"+dd+"-"+mm+"-"+yy;
                else
                    ddd=""+dd+"-"+mm+"-"+yy;

            if(formattedDate.equals(ddd) &&  !isFound) {
                myDs.add(new MyDs(Task, time, yy, mm, dd));
                Log.e("date",""+myDs.get(myDs.size()-1));
               // Toast.makeText(getContext(), Task+" "+time, Toast.LENGTH_SHORT).show();
            }
            Log.e("date",formattedDate+" : "+ddd+" : "+time);

        }

        if(myDs.size()>0) {
            CustomAdapter customAdapter = new CustomAdapter(getContext(), myDs);
            customAdapter.notifyDataSetChanged();
            recyclerViewToday.setAdapter(customAdapter);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getContext());
            recyclerViewToday.setLayoutManager(layoutManager);
            recyclerViewToday.setHasFixedSize(true);

        }
//
    }

    private void customDaySaveMethod() {
        final HashMap<String, String> map = MainActivity.hm;
        myDs1 = new ArrayList<MyDs>();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String Task = "";
            String time = "";
            int yy = 0, mm = 0, dd = 0;
            Task = e.getKey();
            boolean isFound=Task.indexOf("-f9a8b1cff3f4479e6fce6cdae3c62017")!=-1?true:false;
            String arr[] = e.getValue().split("-");
            yy = Integer.parseInt(arr[2]);
            mm = Integer.parseInt(arr[1]);
            dd = Integer.parseInt(arr[0]);
            time = arr[3];
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c.getTime());
            String arr1[]=formattedDate.split("-");
            int mm1=Integer.parseInt(arr1[1]);
            int yy1=Integer.parseInt(arr1[2]);
            int dd1=Integer.parseInt(arr1[0]);

            if((dd1<dd || mm1<mm || yy1<yy) &&  !isFound) {
                myDs1.add(new MyDs(Task, time, yy, mm, dd));
                Log.e("date",dd1+" : "+dd+" : "+time);
            }
            Log.e("date22", formattedDate);

        }

        if(myDs1.size()>0) {
            CustomAdapter customAdapter = new CustomAdapter(getContext(), myDs1);
            recyclerViewUpComing.setAdapter(customAdapter);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getContext());
            recyclerViewUpComing.setLayoutManager(layoutManager);
            recyclerViewUpComing.setHasFixedSize(true);
        }
    }

    private void nextDaySaveMethod()
    {
        final HashMap<String, String> map = MainActivity.hm;
        myDs2 = new ArrayList<MyDs>();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String Task = "";
            String time = "";
            int yy = 0, mm = 0, dd = 0;
            Task = e.getKey();
            boolean isFound=Task.indexOf("-f9a8b1cff3f4479e6fce6cdae3c62017")!=-1?true:false;
            String arr[] = e.getValue().split("-");
            yy = Integer.parseInt(arr[2]);
            mm = Integer.parseInt(arr[1]);
            dd = Integer.parseInt(arr[0]);
            time = arr[3];
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = df.format(c.getTime());
            String ddd = "";
            if (mm < 10 && dd >= 10)
                ddd = "" + dd + "-0" + mm + "-" + yy;
            else if (mm < 10)
                ddd = "0" + dd + "-0" + mm + "-" + yy;
            else if (dd < 10)
                ddd = "0" + dd + "-" + mm + "-" + yy;
            else
                ddd = "" + dd + "-" + mm + "-" + yy;
            if (formattedDate.equals(ddd) &&  !isFound)
                myDs2.add(new MyDs(Task, time, yy, mm, dd));
            Log.e("date22", formattedDate + " : " + ddd);

        }

        if(myDs2.size()>0) {
            CustomAdapter customAdapter = new CustomAdapter(getContext(), myDs2);
            recyclerViewTomorrow.setAdapter(customAdapter);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getContext());
            recyclerViewTomorrow.setLayoutManager(layoutManager);
            recyclerViewTomorrow.setHasFixedSize(true);
        }
    }

    private void remainderDaySaveMethod()
    {
        final HashMap<String, String> map = MainActivity.hm;
        myDs4 = new ArrayList<MyDs>();
        for (Map.Entry<String, String> e : map.entrySet()) {
            String Task = "";
            String time = "";
            int yy = 0, mm = 0, dd = 0;
            Task = e.getKey();
            boolean isFound=Task.indexOf("-f9a8b1cff3f4479e6fce6cdae3c62017")!=-1?true:false;
            String arr[] = e.getValue().split("-");
            yy = Integer.parseInt(arr[2]);
            mm = Integer.parseInt(arr[1]);
            dd = Integer.parseInt(arr[0]);
            time = arr[3];
//            Calendar c = Calendar.getInstance();
//            c.add(Calendar.DATE, 1);
//            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//            String formattedDate = df.format(c.getTime());
//            String ddd = "";
//            if (mm < 10 && dd >= 10)
//                ddd = "" + dd + "-0" + mm + "-" + yy;
//            else if (mm < 10)
//                ddd = "0" + dd + "-0" + mm + "-" + yy;
//            else if (dd < 10)
//                ddd = "0" + dd + "-" + mm + "-" + yy;
//            else
//                ddd = "" + dd + "-" + mm + "-" + yy;
//            Toast.makeText(getContext(), ""+isFound, Toast.LENGTH_SHORT).show();
            if (isFound){
                String tk[]=Task.split("-");
                Task=tk[0];
                //Toast.makeText(getContext(), ""+Task, Toast.LENGTH_SHORT).show();
                myDs4.add(new MyDs(Task, time, yy, mm, dd));
            }
           // Log.e("date22", formattedDate + " : " + ddd);

        }

        Toast.makeText(getContext(), ""+myDs4.size(), Toast.LENGTH_SHORT).show();
        if(myDs4.size()>0) {
            CustomAdapter customAdapter = new CustomAdapter(getContext(), myDs4);
            recyclerViewRemainder.setAdapter(customAdapter);
            RecyclerView.LayoutManager layoutManager =
                    new LinearLayoutManager(getContext());
            recyclerViewRemainder.setLayoutManager(layoutManager);
            recyclerViewRemainder.setHasFixedSize(true);
        }
    }


    public void setAnyTask(final String taskt)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {

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
                                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view1, int hourOfDay,
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

                                        Calendar cal =Calendar.getInstance();
                                        cal.add(Calendar.MINUTE,-5);
                                        callAlarm(cal,view,view1,"You have a task to do", taskt);

                                        String dte=""+dd+"-"+mm+"-"+yy;
                                        Log.e("ffff",dte+"  ;  "+time);
                                        Intent intent= new Intent("ALARM");
                                        String arr2[]={taskt,dte,time};
                                        intent.putExtra("TaskDetail",arr2);
                                        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                                        //new AlarmReceiver().onReceive(getContext(),intent);
                                        addEditText.setText("");
                                        Toast.makeText(getContext(),"Task saved successfully",Toast.LENGTH_SHORT).show();
//                                        Activity ac=getActivity();
//                                        ac.finish();
//                                        startActivity(ac.getIntent());
                                        //sameDaySaveMethod();
                                        Intent i = new Intent(getContext(), MainActivity.class);
                                        i.putExtra("frgToLoad", 2);

                                        // Now start your activity
                                        startActivity(i);

                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.laterTody:
                thisEvening.setBackgroundResource(R.drawable.text_background);
                thisEvening.setTextColor(getResources().getColor(R.color.textColor));
                tomorrowMorning.setBackgroundResource(R.drawable.text_background);
                tomorrowMorning.setTextColor(getResources().getColor(R.color.textColor));
                nextWeek.setBackgroundResource(R.drawable.text_background);
                nextWeek.setTextColor(getResources().getColor(R.color.textColor));
                Someday.setBackgroundResource(R.drawable.text_background);
                Someday.setTextColor(getResources().getColor(R.color.textColor));
                    laterToday.setBackgroundResource(R.drawable.text_background_selected);
                    laterToday.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.thisEvening:
                laterToday.setBackgroundResource(R.drawable.text_background);
                laterToday.setTextColor(getResources().getColor(R.color.textColor));
                tomorrowMorning.setBackgroundResource(R.drawable.text_background);
                tomorrowMorning.setTextColor(getResources().getColor(R.color.textColor));
                nextWeek.setBackgroundResource(R.drawable.text_background);
                nextWeek.setTextColor(getResources().getColor(R.color.textColor));
                Someday.setBackgroundResource(R.drawable.text_background);
                Someday.setTextColor(getResources().getColor(R.color.textColor));
                thisEvening.setBackgroundResource(R.drawable.text_background_selected);
                thisEvening.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.tomorrowMorning:
                laterToday.setBackgroundResource(R.drawable.text_background);
                laterToday.setTextColor(getResources().getColor(R.color.textColor));
                thisEvening.setBackgroundResource(R.drawable.text_background);
                thisEvening.setTextColor(getResources().getColor(R.color.textColor));
                nextWeek.setBackgroundResource(R.drawable.text_background);
                nextWeek.setTextColor(getResources().getColor(R.color.textColor));
                Someday.setBackgroundResource(R.drawable.text_background);
                Someday.setTextColor(getResources().getColor(R.color.textColor));
                tomorrowMorning.setBackgroundResource(R.drawable.text_background_selected);
                tomorrowMorning.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.nextWeek:
                laterToday.setBackgroundResource(R.drawable.text_background);
                laterToday.setTextColor(getResources().getColor(R.color.textColor));
                tomorrowMorning.setBackgroundResource(R.drawable.text_background);
                tomorrowMorning.setTextColor(getResources().getColor(R.color.textColor));
                thisEvening.setBackgroundResource(R.drawable.text_background);
                thisEvening.setTextColor(getResources().getColor(R.color.textColor));
                Someday.setBackgroundResource(R.drawable.text_background);
                Someday.setTextColor(getResources().getColor(R.color.textColor));
                nextWeek.setBackgroundResource(R.drawable.text_background_selected);
                nextWeek.setTextColor(getResources().getColor(R.color.white));

                break;

            case R.id.Someday:
                laterToday.setBackgroundResource(R.drawable.text_background);
                laterToday.setTextColor(getResources().getColor(R.color.textColor));
                tomorrowMorning.setBackgroundResource(R.drawable.text_background);
                tomorrowMorning.setTextColor(getResources().getColor(R.color.textColor));
                nextWeek.setBackgroundResource(R.drawable.text_background);
                nextWeek.setTextColor(getResources().getColor(R.color.textColor));
                thisEvening.setBackgroundResource(R.drawable.text_background);
                thisEvening.setTextColor(getResources().getColor(R.color.textColor));
                Someday.setBackgroundResource(R.drawable.text_background_selected);
                Someday.setTextColor(getResources().getColor(R.color.white));

                break;

            case R.id.Custom:
                Custom.setBackgroundResource(R.drawable.text_background_selected);
                Custom.setTextColor(getResources().getColor(R.color.white));

                break;
        }
    }

    private void callAlarm(Calendar cal, DatePicker datePicker,TimePicker timePicker,String title, String description){
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        int timeMin=timePicker.getMinute();
        int timeHr=timePicker.getHour();
        /*if(timeMin>=5)
            timeMin-=5;
        else
        {
            timeMin=55;
            if(timeHr>=1)
                timeHr-=-1;
            else
                timeHr=23;
        }*/
        cal.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),timeHr,timeMin,0);
        int r = new Random().nextInt(1000);

        AlarmUtils.setAlarm(Objects.requireNonNull(getContext()),intent,r,cal);
    }
}