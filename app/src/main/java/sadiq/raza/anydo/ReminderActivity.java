package sadiq.raza.anydo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import sadiq.raza.anydo.Adapter.SearchItem;
import sadiq.raza.anydo.Adapter.SearchViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sadiq.raza.anydo.Adapter.SearchItem;
import sadiq.raza.anydo.Adapter.SearchViewAdapter;
import sadiq.raza.anydo.Receivers.AlarmReceiver;

public class ReminderActivity extends AppCompatActivity  implements SearchViewAdapter.OnItemClicked{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SearchViewAdapter adapter;
    private List<SearchItem> searchList;
    private ScrollView scrollView;

    private String txtDate, txtTime,edit;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button button;
    TextView textView;
    EditText editText;

    private LinearLayout vehicleDocuments,event,personalDocuments,payments,insurance,main;

    private LinearLayout expand,expand1,expand2,expand3,expand4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        fillSearchList();
        setUpRecyclerView();

        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        toolbar = findViewById(R.id.toolbar);
        toolbar.startAnimation(aniSlide);
        setSupportActionBar(toolbar);
        overridePendingTransition(R.anim.slide_left,R.anim.nothing);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ReminderActivity.this,MainActivity.class));
                Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
                toolbar.startAnimation(aniSlide);
                Animation listAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_list);
                recyclerView.startAnimation(listAni);
                overridePendingTransition(R.anim.nothing,R.anim.slide_right);
//                finish();
            }
        });
       // main = findViewById(R.id.main);


//        vehicleDocuments = findViewById(R.id.vehicleDocuments);
//        event=findViewById(R.id.events);
//        personalDocuments=findViewById(R.id.personalDocuments);
//        payments=findViewById(R.id.payments);
//        insurance=findViewById(R.id.insurance);
//        scrollView=findViewById(R.id.scrollview);
//
//
//
//        expand = findViewById(R.id.expand);
//        expand1 = findViewById(R.id.expand1);
//        expand2 = findViewById(R.id.expand2);
//        expand3 = findViewById(R.id.expand3);
//        expand4 = findViewById(R.id.expand4);
//
//        vehicleDocuments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(expand.getVisibility() == View.VISIBLE) {
//                    expand.setVisibility(View.GONE);
//                }else {
//                    expand.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(expand4.getVisibility() == View.VISIBLE) {
//                    expand4.setVisibility(View.GONE);
//                }else {
//                    expand4.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        payments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(expand3.getVisibility() == View.VISIBLE) {
//                    expand3.setVisibility(View.GONE);
//                }else {
//                    expand3.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        insurance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(expand2.getVisibility() == View.VISIBLE) {
//                    expand2.setVisibility(View.GONE);
//                }else {
//                    expand2.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        personalDocuments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(expand1.getVisibility() == View.VISIBLE) {
//                    expand1.setVisibility(View.GONE);
//                }else {
//                    expand1.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }


    private void fillSearchList() {
        searchList = new ArrayList<>();
//        searchList.add(new SearchItem(R.drawable.ic_car_insurance, "Vehicle Documents"));
//        searchList.add(new SearchItem(R.drawable.ic_writing, "Personal Documents"));
//        searchList.add(new SearchItem(R.drawable.ic_cv, "Insurance"));
//        searchList.add(new SearchItem(R.drawable.ic_wallet, "Payments"));
//        searchList.add(new SearchItem(R.drawable.ic_fireworks, "Events"));
        searchList.add(new SearchItem(R.drawable.ic_driving_license, "Driving License"));
        searchList.add(new SearchItem(R.drawable.ic_contract, "Pollution Documents"));
        searchList.add(new SearchItem(R.drawable.ic_clipboards, "Others"));
        searchList.add(new SearchItem(R.drawable.ic_id_card, "Pan Card"));
        searchList.add(new SearchItem(R.drawable.ic_visa, "Visa"));
        searchList.add(new SearchItem(R.drawable.ic_passport, "Passport"));
        searchList.add(new SearchItem(R.drawable.ic_bike, "Two Wheeler"));
        searchList.add(new SearchItem(R.drawable.ic_car, "Four Wheeler"));
        searchList.add(new SearchItem(R.drawable.ic_medicine, "Health"));
        searchList.add(new SearchItem(R.drawable.ic_bill, "Bill Payments"));
        searchList.add(new SearchItem(R.drawable.ic_loan, "Loan"));
        searchList.add(new SearchItem(R.drawable.ic_debit_card, "Credit Card"));
        searchList.add(new SearchItem(R.drawable.ic_balloons, "Birthday"));
        searchList.add(new SearchItem(R.drawable.ic_gift, "Anniversary"));
        searchList.add(new SearchItem(R.drawable.ic_meeting, "Meeting"));
    }
//
    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycleview);
        Animation listAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_list);
        recyclerView.startAnimation(listAni);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new SearchViewAdapter(searchList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(ReminderActivity.this);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_item_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
////        searchView.setOnSearchClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if(searchView.getVisibility()==View.VISIBLE){
////                //if(recyclerView.getVisibility() == View.VISIBLE) {
////                    recyclerView.setVisibility(View.GONE);
////                    scrollView.setVisibility(View.VISIBLE);
////                }
////                else{
////                    Toast.makeText(ReminderActivity.this, "toast", Toast.LENGTH_SHORT).show();
////                    setUpRecyclerView();
////                    recyclerView.setVisibility(View.VISIBLE);
////                    scrollView.setVisibility(View.GONE);
////                }
////            }
////        });
//
//        searchView.setOnQueryTextListener(new OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_item_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        switch (id){
//            case R.id.action_search:
//                if(recyclerView.getVisibility() == View.VISIBLE) {
//                    recyclerView.setVisibility(View.GONE);
//                    scrollView.setVisibility(View.VISIBLE);
//                }else {
//                    recyclerView.setVisibility(View.VISIBLE);
//                    scrollView.setVisibility(View.GONE);
//                }
//                setUpRecyclerView();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        toolbar.startAnimation(aniSlide);
        Animation listAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down_list);
        recyclerView.startAnimation(listAni);
        overridePendingTransition(R.anim.nothing,R.anim.slide_right);
    }

    @Override
    public void onItemClick(int position,View v) {
        //Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        Bundle bundle = getIntent().getExtras();
        String getKey=bundle.getString("message");
        //txtDate = bundle.getString("message");
        edit=searchList.get(position).getText1();
        Toast.makeText(this, ""+getKey+edit, Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_rest,null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        textView=view.findViewById(R.id.date);
        editText=view.findViewById(R.id.task);
        button=view.findViewById(R.id.addTask);
        //editText.setText(edit,TextView.BufferType.EDITABLE);
        editText.append(edit+" ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit=editText.getText().toString();
                setNextTask(textView,getKey);
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }


//    public void setAnyTask()
//    {
//        final Calendar c = Calendar.getInstance();
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {
//
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay,
//                                                          int minute) {
//
//                //txtDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
//                mHour = c.get(Calendar.HOUR_OF_DAY);
//                mMinute = c.get(Calendar.MINUTE);
//                txtTime = hourOfDay + ":" + minute;
//                Task t = new Task(getApplicationContext());
//                String arr[]=(txtDate+"-"+txtTime).split("-");
//                int yy=Integer.parseInt(arr[2]);
//                int mm=Integer.parseInt(arr[1]);
//                int dd=Integer.parseInt(arr[0]);
//                String time =arr[3];
//                t.saveMap(edit,txtDate+"-"+txtTime);
//                Log.e("savibnggggg","saved");
//                AlarmReceiver am = new AlarmReceiver();
//                String dte=""+dd+"-"+mm+"-"+yy;
//                Log.e("ffff",dte+"  ;  "+time);
//                Intent intent= new Intent("ALARM");
//                String arr2[]={edit,dte,time};
//                intent.putExtra("TaskDetail",arr2);
//                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//                                        //new AlarmReceiver().onReceive(getContext(),intent);
//                editText.setText("");
//                Toast.makeText(getApplicationContext(),"Task saved successfully",Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(ReminderActivity.this, MainActivity.class);
//                startActivity(i);
//
//            }
//            }, mHour, mMinute, false);
//                        timePickerDialog.show();
//
//    }
//
    public void setNextTask(TextView textView,String getKey)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.DialogTheme,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        Toast.makeText(getApplicationContext(), txtDate, Toast.LENGTH_SHORT).show();
                        final Calendar c = Calendar.getInstance();
                        mHour = c.get(Calendar.HOUR_OF_DAY);
                        mMinute = c.get(Calendar.MINUTE);

                        // Launch Time Picker Dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderActivity.this,
                                R.style.DialogTheme,new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {

                                        txtTime = hourOfDay + ":" + minute;
                                        Toast.makeText(getApplicationContext(), txtTime, Toast.LENGTH_SHORT).show();
                                        //HashMap<String,String >hm = new HashMap<>();
                                        //hm.put(taskt,txtDate+txtTime);
                                        Task t = new Task(getApplicationContext());
                                        String arr[]=(txtDate+"-"+txtTime).split("-");
                                        int yy=Integer.parseInt(arr[2]);
                                        int mm=Integer.parseInt(arr[1]);
                                        int dd=Integer.parseInt(arr[0]);
                                        String time =arr[3];
                                        t.saveMap(edit+"-"+getKey,txtDate+"-"+txtTime);
                                        textView.setText(txtDate);
                                        Log.e("savibnggggg","saved");
                                        AlarmReceiver am = new AlarmReceiver();
                                        String dte=""+dd+"-"+mm+"-"+yy;
                                        Log.e("ffff",dte+"  ;  "+time);
                                        Intent intent= new Intent("ALARM");
                                        String arr2[]={edit,dte,time};
                                        intent.putExtra("TaskDetail",arr2);
                                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                                        //new AlarmReceiver().onReceive(getContext(),intent);
                                        //editText.setText("");
                                        Toast.makeText(getApplicationContext(),"Task saved successfully",Toast.LENGTH_SHORT).show();
//                                        Activity ac=getActivity();
//                                        ac.finish();
//                                        startActivity(ac.getIntent());
                                        //sameDaySaveMethod();
                                        Intent i = new Intent(ReminderActivity.this, MainActivity.class);
                                        startActivity(i);

                                    }
                                }, mHour, mMinute, false);
                        timePickerDialog.show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
