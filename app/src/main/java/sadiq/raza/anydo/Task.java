package sadiq.raza.anydo;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public  class Task {

    public Context context;
    public Task(Context context)
    {
        this.context=context;
    }
    public void saveMap(String key,String value){
        SharedPreferences pSharedPref = context.getSharedPreferences("db", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            HashMap<String ,String> hm =loadMap();
            hm.put(key,value);
            JSONObject jsonObject = new JSONObject(hm);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").apply();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }

    HashMap<String,String> loadMap(){
        HashMap<String,String> outputMap = new HashMap<String,String>();
        SharedPreferences pSharedPref = context.getSharedPreferences("db", Context.MODE_PRIVATE);
        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString("My_map", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }
}