package sadiq.raza.anydo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sadiq.raza.anydo.GetterSetter;
import sadiq.raza.anydo.R;

import java.util.ArrayList;

public class AddListAdapter extends BaseAdapter {

    Context context;
    private TextView textView;
    private ImageView imageView;
    ArrayList<GetterSetter> list = new ArrayList<>();

    public AddListAdapter(Context context, ArrayList<GetterSetter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.add_item_list,parent,false);

        imageView = view.findViewById(R.id.icon);
        textView = view.findViewById(R.id.text);

        for(int i=0;i<list.size();i++)
        {
            GetterSetter getterSetter = list.get(position);
            imageView.setImageResource(getterSetter.getIcon());
            textView.setText(getterSetter.getText());
        }

        return view;
    }
}
