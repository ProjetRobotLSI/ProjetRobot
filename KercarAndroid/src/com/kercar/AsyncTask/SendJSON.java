package com.kercar.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class SendJSON extends AsyncTask<Object, Object, Object> {

	@Override
	protected Object doInBackground(Object... params) {

		String action = (String) params[0];
		int roomID = (Integer) params[1];
		URI url = null;
		try {
			if(action.equals("stop")){
				url = new URI(action);
			} else {
				url = new URI(action + roomID);
			}			
			
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		String text = null;
		JSONObject JSONUrl = new JSONObject();
		try {
			JSONUrl.put("url", url.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject datastreams = new JSONObject();
		try {
			datastreams.put("data", JSONUrl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			datastreams.put("registration_ids", "[APA91bG97Stu75wGy12RUlrVwhSmo0J0bw7CueaX717Lvx4pmiEu9QoIsyg1IXFSny-cNmfYY7jmvNxdAuKwu92yIAYptBqVMr1mKpJOaIV1GTl1I9zlWS3YLA9Exv7-hw9HO7sCVjLZFu7RrXxvZRs0X8UMAwejpA]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		StringEntity se = null;
		try {
			se = new StringEntity(datastreams.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		HttpPut put = new HttpPut("url");
		put.addHeader("Host", "android.googleapis.com");
		put.addHeader("Accept", "*/*");
		put.addHeader("Content-type", "application/json");
		put.addHeader("Authorization", "key=AIzaSyCySleTd87kHnLVgug3TnSYxrj4cEPhbNU");
		put.addHeader("Content-Length", "262");
		put.setEntity(se);
		
		String str = datastreams.toString();
		Log.d("MESSAGE ", str);

		URL object = null;
		try {
			object = new URL("https://android.googleapis.com/gcm/send");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setRequestMethod("POST");
			Log.d("POST CONNEXION ", con.toString());
			OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
			wr.write(datastreams.toString());
			wr.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
