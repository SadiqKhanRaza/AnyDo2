package sadiq.raza.anydo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import sadiq.raza.anydo.MyDs;
import sadiq.raza.anydo.R;

public class CalendarCustomAdapter extends RecyclerView.Adapter<CalendarCustomAdapter.MyViewHolder> {


    private ArrayList<MyDs>  myDs ;
    private Context mContext;
    private String dates="";

    public CalendarCustomAdapter(Context context,String dates,ArrayList<MyDs> myDs) {
        mContext = context;
        this.dates=dates;
        this.myDs=myDs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calander_child_recyclerview, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        //final ArrayList<MyDs> myListData= new ArrayList<MyDs>();
        //myListData.sort(Comparator.comparing(MyDs::getYear).thenComparing(MyDs::getMonth).thenComparing(MyDs::getDay));
        myViewHolder.textView.setText(dates);
        CustomAdapter adapter=new CustomAdapter(mContext,myDs);
        myViewHolder.recyclerView.setHasFixedSize(true);
        myViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        myViewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return myDs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RecyclerView recyclerView;
        //private Context mContext;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
            recyclerView=itemView.findViewById(R.id.child_child_recyclerview);
        }
    }
}