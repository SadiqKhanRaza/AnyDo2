package sadiq.raza.anydo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import sun.bob.mcalendarview.vo.DateData;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private HashMap<String,String > hm;
    public MyListAdapter(HashMap<String,String > hm) {
        this.hm = hm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ArrayList<MyDs> myListData= new ArrayList<MyDs>();
        for(Map.Entry<String,String> e : hm.entrySet())
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
            myListData.add(new MyDs(Task,time,yy,mm,dd));
            Log.e("date",yy+","+mm+","+dd);

        }
        myListData.sort(Comparator.comparing(MyDs::getYear).thenComparing(MyDs::getMonth).thenComparing(MyDs::getDay));
        holder.textView.setText(myListData.get(position).getDay()+" -> "+ myListData.get(position).getTask()+" At : "+myListData.get(position).getTime());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: ",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return hm.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}