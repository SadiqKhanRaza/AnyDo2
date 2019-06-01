package sadiq.raza.anydo.Fragments;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sadiq.raza.anydo.MainActivity;
import sadiq.raza.anydo.MyDs;
import sadiq.raza.anydo.R;

public class Settings extends Fragment {

    FloatingActionButton del;

    private RecyclerView recyclerView;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.settings, container, false);
         view.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Delete Task")
                        .setMessage("Are you sure you want to delete all taks ?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences pSharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences("db", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pSharedPref.edit();
                                editor.clear();
                                editor.apply();
                                Toast.makeText(getContext(), "All Tasks deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

         view.findViewById(R.id.del2).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 HashMap<String,String> map = MainActivity.hm;
                 if(map!=null)
                 {
                        String test="01-06-201915:30"; //Delete task on this test dateTime
                        boolean success=delTask(map,test);
                        if(!success)
                            Toast.makeText(getContext(), "No Task availabe at this date and time", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                 }
             }
         });

        return view;
    }

    class AllTasksAdapter extends RecyclerView.Adapter<AllTasksAdapter.MyViewHolder> {
        private Context context;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View view) {
                super(view);
            }
        }

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

    boolean delTask(HashMap<String,String> hashMap,String dateTime) //format for dateTime argument dd-MM-yyyyhh:mm
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
        SharedPreferences pSharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("db", Context.MODE_PRIVATE);
        if(pSharedPref!=null)
        {
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").apply();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
        return flag;

    }
}