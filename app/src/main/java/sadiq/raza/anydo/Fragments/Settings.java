package sadiq.raza.anydo.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import sadiq.raza.anydo.R;

public class Settings extends Fragment {


    private RecyclerView recyclerView;
    private Button button;

    public Settings() {
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
        View view = inflater.inflate(R.layout.settings, container, false);
        button=(Button)view.findViewById(R.id.s);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GoogleSignInPage g=new GoogleSignInPage();
//                g.signOut();
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
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