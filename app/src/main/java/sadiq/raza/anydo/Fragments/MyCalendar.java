package sadiq.raza.anydo.Fragments;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sadiq.raza.anydo.MainActivity;
import sadiq.raza.anydo.MyDs;
import sadiq.raza.anydo.MyListAdapter;
import sadiq.raza.anydo.R;
import sadiq.raza.anydo.Task;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;

public class MyCalendar extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MyDs> myDs;

    public MyCalendar() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        }
        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                StringBuilder sb = new StringBuilder("");
                if(myDs==null)
                    return;
                for(MyDs d : myDs)
                {
                    if(d.getDayString().equals(date.getDayString())) {
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
                final PopupWindow pw = new PopupWindow(layout, (int)density*240, (int)density*285, true);
                (layout.findViewById(R.id.close)).setOnClickListener(v -> pw.dismiss());
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
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(map);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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