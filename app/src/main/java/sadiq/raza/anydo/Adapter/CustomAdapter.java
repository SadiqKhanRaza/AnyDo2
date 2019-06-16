package sadiq.raza.anydo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sadiq.raza.anydo.MainActivity;
import sadiq.raza.anydo.MyDs;
import sadiq.raza.anydo.R;

import static java.security.AccessController.getContext;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private ArrayList<MyDs> myDs = new ArrayList<>();
    private Context mContext;
    private Activity activity;

    public CustomAdapter(Context context, ArrayList<MyDs> myDs) {
        mContext = context;
        this.myDs = myDs;
       // this.activity=activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_recyclerview, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindView(myDs.get(i));
    }

    @Override
    public int getItemCount() {
        return myDs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView notes,time;
        ImageButton imageButton;
        LinearLayout linearLayout;
        //private Context mContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.radio);
            notes=(TextView)itemView.findViewById(R.id.notes);
            time=(TextView)itemView.findViewById(R.id.time);
            imageButton=(ImageButton)itemView.findViewById(R.id.image_button);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linearLayout);
            //ButterKnife.bind(this, itemView);
            //mContext = itemView.getContext();
        }

        public void bindView(MyDs ds) {
            notes.setText(ds.getTask());
            String timex=ds.getTime();
            String arr[]=timex.split(":");
            int hour=0,min=0;
            String mhour="",mmin="";
            hour=Integer.parseInt(arr[0]);
            min=Integer.parseInt(arr[1]);
            if(hour<10)
                mhour="0"+hour;
            else
                mhour=""+hour;
            if(min<10)
                mmin="0"+min;
            else
                mmin=""+min;
            time.setText(mhour+":"+mmin);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageButton.getVisibility() == View.VISIBLE){
                        imageButton.setVisibility(View.GONE);
                        notes.setPaintFlags(notes.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        time.setPaintFlags(time.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        //imageButton.setLayerPaint(imageButton.getPaint.STRIKE_THRU_TEXT_FLAG);
                    }else {
                        imageButton.setVisibility(View.VISIBLE);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String,String> map = MainActivity.hm;
                                if(map!=null)
                                {
                                    String test="01-06-201915:30"; //Delete task on this test dateTime
                                    String day,month;
                                    if(ds.getDay()<10)
                                        day="0"+ds.getDay();
                                    else
                                        day=""+ds.getDay();

                                    if(ds.getMonth()<10)
                                        month="0"+ds.getMonth();
                                    else
                                        month=""+ds.getMonth();

                                    String data=day+"-"+month+"-"+ds.getYear()+ds.getTime();
                                    boolean success=delTask(map,data);
                                    if(!success)
                                        Toast.makeText(mContext, "No Task availabe at this date and time"+" "+ds.getTime()+" "+ds.getMonth(), Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(mContext, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                }
                                Intent startMenuActivity = new Intent(mContext, MainActivity.class);
                                mContext.startActivity(startMenuActivity);

                            }
                        });
                        notes.setPaintFlags(notes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        time.setPaintFlags(time.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_layout,null);
                    BottomSheetDialog dialog = new BottomSheetDialog(mContext);
                    LinearLayout layout1=view.findViewById(R.id.call);
                    LinearLayout layout2=view.findViewById(R.id.message);
                    TextView textView1=view.findViewById(R.id.date);
                    EditText addText=view.findViewById(R.id.editText);
                    EditText takeNotes=view.findViewById(R.id.addNote);
                    Button done=view.findViewById(R.id.done);
                    Button delete=view.findViewById(R.id.delete);
                    textView1.setText(ds.getDayString()+"/"+ds.getMonthString()+"/"+ds.getYear());
                    //addText.setText(ds.getTask());
                    addText.append(ds.getTask()+" ");

                    HashMap<String, String> map = MainActivity.hm;
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (map != null) {
                                String test = "01-06-201915:30"; //Delete task on this test dateTime
                                String day, month;
                                if (ds.getDay() < 10)
                                    day = "0" + ds.getDay();
                                else
                                    day = "" + ds.getDay();

                                if (ds.getMonth() < 10)
                                    month = "0" + ds.getMonth();
                                else
                                    month = "" + ds.getMonth();

                                String data = day + "-" + month + "-" + ds.getYear() +"-"+ ds.getTime();
                                //String data = ds.getDay() + "-" + ds.getMonth() + "-" + ds.getYear() +"-"+ ds.getTime();
                                Log.d("String",data);
                                //Toast.makeText(mContext, ""+data, Toast.LENGTH_SHORT).show();
                                editTask(map,data,addText.getText().toString());
                            }
                            Intent startMenuActivity = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(startMenuActivity);
                        }

                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(map!=null)
                            {
                                String test="01-06-201915:30"; //Delete task on this test dateTime
                                String day,month;
                                if(ds.getDay()<10)
                                    day="0"+ds.getDay();
                                else
                                    day=""+ds.getDay();

                                if(ds.getMonth()<10)
                                    month="0"+ds.getMonth();
                                else
                                    month=""+ds.getMonth();

                                String data=day+"-"+month+"-"+ds.getYear()+ds.getTime();
                                boolean success=delTask(map,data);
                                if(!success)
                                    Toast.makeText(mContext, "No Task availabe at this date and time"+" "+ds.getTime()+" "+ds.getMonth(), Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(mContext, "Successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                            Intent startMenuActivity = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(startMenuActivity);

                        }
                    });
                    dialog.setContentView(view);
                    dialog.show();
                }
            });
        }
    }

    boolean delTask(HashMap<String,String> hashMap, String dateTime) //format for dateTime argument dd-MM-yyyyhh:mm
    {
        String keyToRemove="";
        boolean flag=false;
        for(Map.Entry<String,String> e : hashMap.entrySet())
        {
            String time="";
            int yy=0,mm=0,dd=0;
            String arr[]=e.getValue().split("-");
            yy=Integer.parseInt(arr[2]);
            mm=Integer.parseInt(arr[1]);
            dd=Integer.parseInt(arr[0]);
            time=arr[3];
            String ddd="";
            if(mm<10 && dd>=10)
                ddd=""+dd+"-0"+mm+"-"+yy;
            else if(mm<10 )
                ddd="0"+dd+"-0"+mm+"-"+yy;
            else if( dd < 10)
                ddd="0"+dd+"-"+mm+"-"+yy;
            else
                ddd=""+dd+"-"+mm+"-"+yy;

            ddd+=time;
            Log.e("delete",dateTime+" "+ddd);
            if(dateTime.equals(ddd))
            {
                keyToRemove=e.getKey();
                hashMap.remove(keyToRemove);
                flag=true;
                break;
            }
        }
        //hashMap.put(key,value);
        JSONObject jsonObject = new JSONObject(hashMap);
        String jsonString = jsonObject.toString();
        SharedPreferences pSharedPref = Objects.requireNonNull(mContext.getSharedPreferences("db", Context.MODE_PRIVATE));
        if(pSharedPref!=null)
        {
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").apply();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
        return flag;

    }

    void editTask(HashMap<String,String> hashMap,String dateTime,String newTask)//Eg dateTime in fromat "28-6-2019-23:54"
    {
        String arr[]=dateTime.split("-");
        String time="";
        int yy=0,mm=0,dd=0;
        yy=Integer.parseInt(arr[2]);
        mm=Integer.parseInt(arr[1]);
        dd=Integer.parseInt(arr[0]);
        time=arr[3];

        String day="",month="";
        if(dd<10)
            day="0"+dd;
        else
            day=""+dd;
        if(mm<10)
            month="0"+mm;
        else
            month=""+mm;
        String dDate=""+day+"-"+month+"-"+yy+time;
        Toast.makeText(mContext, ""+dDate, Toast.LENGTH_SHORT).show();
        delTask(hashMap,dDate);
        SharedPreferences pSharedPref = mContext.getSharedPreferences("db", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            hashMap.put(newTask,dateTime);
            JSONObject jsonObject = new JSONObject(hashMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").apply();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }
}
