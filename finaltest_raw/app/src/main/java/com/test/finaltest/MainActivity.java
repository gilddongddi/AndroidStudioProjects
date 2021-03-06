package com.test.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private ListView lv;

    String cname, ctype, bname, nom, postal, manager, address;

    private static String JSON_URL = "https://api.odcloud.kr/api/15077603/v1/uddi:9fdd2474-38ab-4cd6-b438-a145b1e2848e?page=1&perPage=10&serviceKey=AW4C5aGoQveejXIPxRkC1JqrIZz8fATRuilN0xaoqVOkhtyzOhWYX%2BMNBTFV1c%2FdxDqo6Q4vS2HAoYeomKAlFA%3D%3D";

    ArrayList<HashMap<String,String>> covid19List;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        covid19List = new ArrayList<>();
        lv = findViewById(R.id.listview);


        GetData getData = new GetData();
        getData.execute();


    }




    public class GetData extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;


                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);


                    int data = isr.read();
                    while (data != -1) {

                        current += (char) data;
                        data = isr.read();
                    }
                    return current;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();

            }

                return current;

            }


        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0 ; i< jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    cname = jsonObject1.getString("?????????");
                    ctype = jsonObject1.getString("????????????");
                    bname = jsonObject1.getString("?????????");
                    nom = jsonObject1.getString("??????");
                    postal = jsonObject1.getString("????????????");
                    manager = jsonObject1.getString("????????????");
                    address = jsonObject1.getString("??????");



                    // Hashmap
                    HashMap<String, String> covid19 = new HashMap<>();

                    covid19.put("?????????", cname);
                    covid19.put("????????????", ctype);
                    covid19.put("?????????", bname);
                    covid19.put("??????", nom);
                    covid19.put("????????????", postal);
                    covid19.put("????????????", manager);
                    covid19.put("??????", address);

                    covid19List.add(covid19);


                }

        } catch (JSONException e) {
                e.printStackTrace();
            }


            //Displaying the results
            //All source codes are available on UDEMY COURSE REsources under each lesson
            //also on Master COding App found on playstore

        ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    covid19List,
                    R.layout.row_layout,
                    new String[] {"?????????", "????????????", "?????????", "??????", "????????????", "????????????", "??????"},
                    new int[]{R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7});

        lv.setAdapter(adapter);

        }
    }






}