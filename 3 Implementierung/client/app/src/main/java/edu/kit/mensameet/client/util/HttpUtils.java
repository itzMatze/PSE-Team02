package edu.kit.mensameet.client.util;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    public static String get (String urlStr) throws MalformedURLException {
        String result  = "";
        BufferedReader br = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlStr);
            //get connection
            connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
            //TODO: Not sure if UTF-8 suitable
            connection.setRequestProperty("encoding","UTF-8");
            connection.setConnectTimeout(3*1000);//3 seconds
            connection.setUseCaches(false);

            //request success
            if(connection.getResponseCode() == 200){
                //use input stream to get relevant data
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = br.readLine();
                while(line != null){
                    result+=line + "/n";
                    line = br.readLine();
                }
            }else{
                Log.e("internet", "request failed");
            }
        }catch (Exception e){
            Log.e("internet", e.getMessage());
        }
        //[START] close stream
        finally {
            if(br != null) {
                try{
                    br.close();
                }catch (Exception e){
                    Log.e("internet", e.getMessage());
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        //[END] Stream
        //todo convert
        return result;
    }

    //todo Map or json? Map<String, String>params
    public static String post(String urlStr, JSONObject jsonObject){
        PrintWriter printWriter = null;
        String result  = "";
        HttpURLConnection connection = null;
        BufferedReader br = null;

        try {
            URL url = new URL(urlStr);
            //get connection
            connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            //TODO: Not sure if UTF-8 suitable
            connection.setRequestProperty("encoding","UTF-8");
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","KEEP-ALIVE");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            //get out put stream
            printWriter = new PrintWriter(connection.getOutputStream());

            printWriter.write(jsonObject.toString());
            printWriter.flush();

            //request success
            if(connection.getResponseCode() == 200){
                //use input stream to get relevant data
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = br.readLine();
                while(line != null){
                    result+=line + "/n";
                    line = br.readLine();
                }
            }else{
                Log.e("internet", "request failed");
            }
        }catch (Exception e){
            Log.e("internet", e.getMessage());
        }
        //[START] close stream
        finally {
            try {
                if(printWriter != null){
                    printWriter.close();
                }
                if (br != null) {

                    br.close();
                }
            }catch (Exception e){
                Log.e("internet", e.getMessage());

            }
            if(connection != null){
                connection.disconnect();
            }
        }
        //[CLOSE] Stream
        //todo convert
        return result;
    }
}
