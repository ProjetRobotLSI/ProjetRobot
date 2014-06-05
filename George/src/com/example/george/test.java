package com.example.george;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Looper;
import android.util.Log;

public class test {
	
    protected void sendJson(final String url) {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                
                try {
//                    HttpPost post = new HttpPost("https://android.googleapis.com/gcm/send");
                    HttpPost post = new HttpPost("192.168.0.1");
                    StringEntity se = new StringEntity("{ \"registration_ids\": [\"APA91bG97Stu75wGy12RUlrVwhSmo0J0bw7CueaX717Lvx4pmiEu9QoIsyg1IXFSny-cNmfYY7jmvNxdAuKwu92yIAYptBqVMr1mKpJOaIV1GTl1I9zlWS3YLA9Exv7-hw9HO7sCVjLZFu7RrXxvZRs0X8UMAwejpA\"],\"data\": {\"url\": \"https://apprtc.appspot.com/?r=51711279\"}}", "UTF8");  
//                    StringEntity se = new StringEntity("registration_id=APA91bExUi86DBoN3SfKv2pTKtvhYAm-lZjQ8KRCG2Hs_oN-oHlaUkjtIXsQn0X0LDBP5tMXbT3jr5fLsQYM1WCHPQ3LZn00Bs_eq5BnERY7vcAQwUn0_f-YEMtHLOYxcRhM1AepjR-61hTmD02cXkiO0iMfIyKrvA", "UTF8");
                    se.setContentType(new BasicHeader("Content-type", "application/json"));
                    se.setContentType(new BasicHeader("Host", "android.googleapis.com"));
                    se.setContentType(new BasicHeader("Authorization", "key=AIzaSyCySleTd87kHnLVgug3TnSYxrj4cEPhbNU"));
                    Log.i("ttt", "");
                    Log.i("ttt", convertStreamToString(se.getContent()));
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        Log.i("ttt", convertStreamToString(in));
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();      
    }
    
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
