package sadiq.raza.anydo.Adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sadiq.raza.anydo.R;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder> implements Filterable {
    private List<SearchItem> searchList;
    private List<SearchItem> searchListFull;
    private OnItemClicked onClick;
    private View v;

    public interface OnItemClicked {
        void onItemClick(int position, View v);
    }


    class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        LinearLayout layout;

        SearchViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            layout=itemView.findViewById(R.id.linearLayout);
        }
    }

    public SearchViewAdapter(List<SearchItem> searchList) {
        this.searchList = searchList;
        searchListFull = new ArrayList<>(searchList);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_list,
                parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchItem currentItem = searchList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SearchItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                Log.d("string",filterPattern);

                for (SearchItem item : searchListFull) {
                    if (item.getText1().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            searchList.clear();
            //searchList.addAll((List<SearchItem>)results.values);
            notifyDataSetChanged();
        }
    };

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
        //this.v=v;
    }
}
