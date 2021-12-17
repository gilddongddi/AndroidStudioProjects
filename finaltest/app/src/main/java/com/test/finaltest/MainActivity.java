package com.test.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.List;
import java.util.Locale;

public class MainActivity<mMap> extends AppCompatActivity {




    GoogleMap Map;

    {
    //    Map = (GoogleMap) findViewById(java.MapsActivity.Map);
    }

    private ListView lv;

    String cname, ctype, bname, nom, postal, manager, address;

    private static String JSON_URL = "https://api.odcloud.kr/api/15077603/v1/uddi:9fdd2474-38ab-4cd6-b438-a145b1e2848e?page=1&perPage=10&serviceKey=AW4C5aGoQveejXIPxRkC1JqrIZz8fATRuilN0xaoqVOkhtyzOhWYX%2BMNBTFV1c%2FdxDqo6Q4vS2HAoYeomKAlFA%3D%3D";

    ArrayList<HashMap<String,String>> covid19List;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("FinalTest:18932040 배청명");



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

                    cname = jsonObject1.getString("센터명");
                    ctype = jsonObject1.getString("센터유형");
                    bname = jsonObject1.getString("시설명");
                    nom = jsonObject1.getString("연번");
                    postal = jsonObject1.getString("우편번호");
                    manager = jsonObject1.getString("운영기관");
                    address = jsonObject1.getString("주소");



                    // Hashmap
                    HashMap<String, String> covid19 = new HashMap<>();

                    covid19.put("센터명", cname);
                    covid19.put("센터유형", ctype);
                    covid19.put("시설명", bname);
                    covid19.put("연번", nom);
                    covid19.put("우편번호", postal);
                    covid19.put("운영기관", manager);
                    covid19.put("주소", address);

                    covid19List.add(covid19);


                }

        } catch (JSONException e) {
                e.printStackTrace();
            }


// @Override
//     private void geoLocate(View v)  {
//
//         String locationName = JSONObject jsonObjec1.getString();
//         Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//         try {
//             List<Address> addressList = geocoder.getFromLacationname(locationName, 1);
//
//             if (addressList.size() > 0) {
//                 Address addr = addressList.get(0);
//
//                 gotoLocation(addr.getLatitude(), addr.getLatitude());
//
//                 Map.addMarker(new MarkerOptions().position(new LatLng(addr.getLatitude(), addr.getLongitude())));
//
//             }
//         } catch (IOException e) {
//         }
//     }


            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    covid19List,
                    R.layout.row_layout,
                    new String[] {"센터명", "센터유형", "시설명", "연번", "우편번호", "운영기관", "주소"},
                    new int[]{R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7});

        lv.setAdapter(adapter);






        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
          //          Map.addMarker(new MarkerOptions().position(new LatLng(addr.getLatitude(), addr.getLongitude())));

                }else if (position == 1){
                    startActivity(new Intent(MainActivity.this, MapsActivity2.class));


                }else if (position == 2){
                    startActivity(new Intent(MainActivity.this, MapsActivity3.class));

                }else if (position ==3){
                    startActivity(new Intent(MainActivity.this, MapsActivity4.class));

                }else {
                    startActivity(new Intent(MainActivity.this, MapsActivity5.class));


                }


            }
        });

        }
    }






}