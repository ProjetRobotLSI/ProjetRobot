package com.kercar;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
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
                    HttpPost post = new HttpPost("https://android.googleapis.com/gcm/send");
                    StringEntity se = new StringEntity("{ \"registration_ids\": [\"APA91bG97Stu75wGy12RUlrVwhSmo0J0bw7CueaX717Lvx4pmiEu9QoIsyg1IXFSny-cNmfYY7jmvNxdAuKwu92yIAYptBqVMr1mKpJOaIV1GTl1I9zlWS3YLA9Exv7-hw9HO7sCVjLZFu7RrXxvZRs0X8UMAwejpA\"],\"data\": {\"url\": \"https://apprtc.appspot.com/?r=51711279\"}}", "UTF8");  
                    post.addHeader(new BasicHeader("Content-Type","application/json"));
                    post.addHeader(new BasicHeader("Authorization","key=AIzaSyCiGt1dn3RdyMjKUaNxtlVdKJShLVoTshk"));
                
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
