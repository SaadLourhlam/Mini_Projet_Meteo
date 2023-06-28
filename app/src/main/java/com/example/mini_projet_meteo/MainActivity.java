package com.example.mini_projet_meteo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mini_projet_meteo.Data_Base.DataBase;
import com.example.mini_projet_meteo.Model.Ville;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    ListView lv,info;
    DataBase db;
    List<HashMap<String,String>> villes=new ArrayList<>();
    String data="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(
                ()->{
                    data=getData();
                    System.out.println(parseData().toString());
        }
        ).start();

        lv=findViewById(R.id.lv);
        info=findViewById(R.id.info);
        db=new DataBase(this);
        villes=db.getVilles();

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,villes,R.layout.item_ville,new String[]{"nom"},new int[]{R.id.nomville});
        lv.setAdapter(simpleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<HashMap<String,String>> mm=new ArrayList<>();
                String[] title={};
                String[] vv={};
                for (int i=0;i<title.length;i++){
                    HashMap<String,String> hm=new HashMap<>();
                    hm.put("title",title[i]);
                    hm.put("value",vv[i]);
                    mm.add(hm);
                }
                SimpleAdapter simpleAdapter1=new SimpleAdapter(view.getContext(),mm,R.layout.item_affiche,new String[]{"title","value"},new int[]{R.id.title,R.id.value});
                info.setAdapter(simpleAdapter1);
                ImageView show=view.findViewById(R.id.icon);
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<HashMap<String,String>> mm=new ArrayList<>();
                        String[] title={"Temperature :","Humidité :","Prssion :"};
                        String[] vv={"23°c","50%","1009 hPa"};
                        for (int i=0;i<title.length;i++){
                            HashMap<String,String> hm=new HashMap<>();
                            hm.put("title",title[i]);
                            hm.put("value",vv[i]);
                            mm.add(hm);
                        }
                        SimpleAdapter simpleAdapter1=new SimpleAdapter(view.getContext(),mm,R.layout.item_affiche,new String[]{"title","value"},new int[]{R.id.title,R.id.value});
                        info.setAdapter(simpleAdapter1);
                    }
                });
                ImageView delete=view.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> hm= (HashMap<String, String>) parent.getItemAtPosition(position);
                        alertsupprimer(hm);
                    }
                });
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //----------------------------------------Menu-------------------------------------//
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
//                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                showInputDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //---------------------------------------------------------------------------------//

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Saisir le nom de la ville :");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                db.ajouterVille(text);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void alertsupprimer(HashMap<String,String> hm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("tu veux le supprimer :");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.delete(hm.get("id"));
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    public String getData(){
        String jsonString="";
        try{
            URL url=new URL("http://api.weatherstack.com/current?access_key=9768bc45b672ef3195083db28917e2f0&query=Safi");
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream=httpURLConnection.getInputStream();
            try{
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String ligne="";
                while((ligne=bufferedReader.readLine())!=null){
                    jsonString+=ligne;
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonString;
    }

    public Ville parseData() {
        Ville ville = new Ville();
        try {
            JSONArray jsonArray=new JSONArray(data);
            JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
            JSONObject currentObject = jsonObject.getJSONObject("current");

            // Extract temperature
            int temperature = currentObject.getInt("temperature");
            ville.setTemperature(temperature);

            // Extract weather description
            String weatherDescription = currentObject.getString("weather_descriptions");
            ville.setWeatherDescription(weatherDescription);

            // Extract pressure
            int pressure = currentObject.getInt("pressure");
            ville.setPressure(pressure);

            // Extract humidity
            int humidity = currentObject.getInt("humidity");
            ville.setHumidity(humidity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ville;
    }


}





