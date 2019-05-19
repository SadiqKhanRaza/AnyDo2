package sadiq.raza.anydo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import sadiq.raza.anydo.Adapter.AddListAdapter;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;

    public String[] text= {
            "Call",
            "Check",
            "Get",
            "Email",
            "Buy",
            "Meet/Schedule",
            "Clean",
            "Take",
            "Send",
            "Pay",
            "Make",
            "Pick",
            "Do",
            "Read",
            "Print",
            "Finish",
            "Study",
            "Ask",
    } ;

    public int[] icons = {
            R.drawable.ic_call,
            R.drawable.ic_check,
            R.drawable.ic_get,
            R.drawable.ic_mail,
            R.drawable.ic_buy,
            R.drawable.ic_meeting,
            R.drawable.ic_clean


    };

    ArrayList<GetterSetter> list = new ArrayList<>();
    private EditText iWantTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        toolbar = findViewById(R.id.toolbar);
        toolbar.startAnimation(aniSlide);
        setSupportActionBar(toolbar);
        overridePendingTransition(R.anim.slide_left,R.anim.nothing);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddActivity.this,MainActivity.class));
                Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
                toolbar.startAnimation(aniSlide);
                Animation listAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_list);
                listView.startAnimation(listAni);
                overridePendingTransition(R.anim.nothing,R.anim.slide_right);
//                finish();
            }
        });

        listView = findViewById(R.id.lsitView);
        Animation listAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_list);
        listView.startAnimation(listAni);

        for (int i=0;i<icons.length;i++){
            GetterSetter getterSetter = new GetterSetter();
            getterSetter.setIcon(icons[i]);
            getterSetter.setText(text[i]);
            list.add(getterSetter);

        }

        listView.setAdapter(new AddListAdapter(this,list));


        iWantTo = findViewById(R.id.iWantTo);

        iWantTo.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.mic:

                Toast.makeText(this, "Add Mic Functionality", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        toolbar.startAnimation(aniSlide);
        Animation listAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_list);
        listView.startAnimation(listAni);
        overridePendingTransition(R.anim.nothing,R.anim.slide_right);
    }
}
