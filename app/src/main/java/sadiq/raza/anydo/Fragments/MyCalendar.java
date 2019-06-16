package sadiq.raza.anydo.Fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;


import sadiq.raza.anydo.Adapter.CustomAdapter;
import sadiq.raza.anydo.MainActivity;
import sadiq.raza.anydo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sadiq.raza.anydo.MyDs;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;

public class MyCalendar extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<MyDs> myDs;
    TextView textView1,textView2;
    CheckBox checkBox;
    ImageButton imageButton;
    String pk;

    public MyCalendar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calendar, container, false);
        MCalendarView mCalendarView = view.findViewById(R.id.calendarView);
        Log.e("map", "" + MainActivity.hm);
        HashMap<String,String> map = MainActivity.hm;
        MarkedDates m = mCalendarView.getMarkedDates();
        m.removeAdd();
        Log.e("MdDs map ",""+map);

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
            myDs.add(new MyDs(Task,time,yy,mm,dd));
            Log.e("date",yy+","+mm+","+dd);
            mCalendarView.markDate(new DateData(yy, mm, dd));
            //mCalendarView.setMarkStyle(MarkStyle.BACKGROUND);
        }
        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                ArrayList<MyDs> start=new ArrayList<>();
                boolean flag=false;
                if(myDs==null)
                    return;
                for(MyDs d : myDs)
                {
                    if(d.getDayString().equals(date.getDayString()) && d.getMonthString().equals(date.getMonthString()) && d.getYear()==date.getYear()) {
                        start.add(d);
                    }
                }

                view = LayoutInflater.from(getContext()).inflate(R.layout.calander_child_recyclerview,null);
                BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(view);
                dialog.show();
                recyclerView=view.findViewById(R.id.child_child_recyclerview);
                textView1=view.findViewById(R.id.text);
                textView1.setText(date.getDayString()+"/"+date.getMonthString()+"/"+date.getYear());
                CustomAdapter customAdapter = new CustomAdapter(getContext(), start);
                recyclerView.setAdapter(customAdapter);
                RecyclerView.LayoutManager layoutManager =
                        new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
            }
        });
        //mCalendarView.markDate(2019,5,15);

        HashMap<String,ArrayList<MyDs> > make_map=new HashMap<>();

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
            ArrayList<MyDs> arrayList=new ArrayList<>();

            String datex=dd+"/"+mm+"/"+yy;
            if(!(make_map.containsKey(datex))) {
                for (Map.Entry<String, String> mk : map.entrySet()) {
                    String Task1 = "";
                    String time1 = "";
                    int yy1 = 0, mm1 = 0, dd1 = 0;
                    Task1 = e.getKey();
                    String arr1[] = e.getValue().split("-");
                    yy1 = Integer.parseInt(arr[2]);
                    mm1 = Integer.parseInt(arr[1]);
                    dd1 = Integer.parseInt(arr[0]);
                    time1 = arr[3];

                    if(dd==dd1 && mm==mm1 && yy==yy1)
                        arrayList.add(new MyDs(Task1,time1,yy1,mm1,dd1));

                }
                make_map.put(datex,arrayList);
                arrayList.clear();
            }
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ArrayList<String> date=new ArrayList<>();
        ArrayList<ArrayList<MyDs> > allTask=new ArrayList<>();

       // for(Map.Entry<String,ArrayList<MyDs> > d: make_map.entrySet()) {
//            date.add(d.getKey());
//            allTask.add(d.getValue());
//            CalendarCustomAdapter adapter=new CalendarCustomAdapter(getContext(),d.getKey(),d.getValue());
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            recyclerView.setAdapter(adapter);
        //}
//        CalendarCustomAdapter adapter=new CalendarCustomAdapter(getContext(),date,allTask);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);



        return view;
    }

    class AllTasksAdapter extends RecyclerView.Adapter<AllTasksAdapter.MyViewHolder> {
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View view) {
                super(view);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}