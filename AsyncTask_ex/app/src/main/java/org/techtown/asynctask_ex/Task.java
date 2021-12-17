package org.techtown.asynctask_ex;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Task extends AsyncTas  k<string, void, string> {

    String clientKey = "AW4C5aGoQveejXIPxRkC1JqrIZz8fATRuilN0xaoqVOkhtyzOhWYX+MNBTFV1c/dxDqo6Q4vS2HAoYeomKAlFA==";;
    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params){
        URL url = null;
        try{
            url = new URL("https://api.odcloud.kr/api/15077603/v1/uddi:9fdd2474-38ab-4cd6-b438-a145b1e2848e?page=1&perPage=10&serviceKey=AW4C5aGoQveejXIPxRkC1JqrIZz8fATRuilN0xaoqVOkhtyzOhWYX%2BMNBTFV1c%2FdxDqo6Q4vS2HAoYeomKAlFA%3D%3D");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("x-waple-authorization", clientKey);

            if(conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while((str = reader.readLine()) != null){
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg :", receiveMsg);

                reader.close();
            } else{
                Log.i("통신 결과", conn.getResponseCode()+ "에러");
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return receiveMsg;
    }
}
