package com.kercar;

import java.util.LinkedList;

import com.kercar.AsyncTask.*;
import com.kercar.osmandroid.OSMAndroid;

import kercar.android.ComAndroid;
import kercar.android.IComAndroid;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import com.kercar.AsyncTask.AsyncAvancer;
import com.kercar.AsyncTask.AsyncDroite;
import com.kercar.AsyncTask.AsyncGauche;
import com.kercar.AsyncTask.AsyncPrendrePhoto;
import com.kercar.AsyncTask.AsyncReculer;
import com.kercar.AsyncTask.AsyncStop;
import com.kercar.AsyncTask.ThreadMap;
import com.kercar.osmandroid.OSMAndroid;

public class ControlManuel extends Activity{
	//Attributs
	private Button avance;
	private Button recule;
	private Button gauche;
	private Button droite;
	private Button photo;
	private Button video;
	private SeekBar vitesse;
	private OSMAndroid OSM;
	
	private String url;
	private IComAndroid com;
	
	private LinkedList<Integer> list;
	private Thread threadMap;
	private int i;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //ContentView
        setContentView(R.layout.controle_manuel);
             
		//Initialisation des attributs
		avance = (Button)findViewById(R.id.buttonAvance);
		recule = (Button)findViewById(R.id.buttonRecule);
		gauche = (Button)findViewById(R.id.buttonGauche);
		droite = (Button)findViewById(R.id.buttonDroite);
		photo = (Button)findViewById(R.id.buttonPhoto);
		vitesse = (SeekBar)findViewById(R.id.barVitesse);
		OSM = (OSMAndroid)findViewById(R.id.OSM);
		video = (Button)findViewById(R.id.buttonVideo);
		
		i=500;
		url = "http://"+IP.ip+":8080/KerCarCommunication/";
		
		com = ComAndroid.getManager();
		com.setURL(url);
		
		list = new LinkedList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		
		threadMap = new ThreadMap(OSM, com);
		threadMap.start();
		
		avance.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncAvancer(vitesse.getProgress(), com).execute();
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	                break;
	            }
				return false;
			}
		});
		
		recule.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncReculer(vitesse.getProgress(), com).execute();
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	                break;
	            }
				return false;
			}
		});
		
		gauche.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncGauche(com).execute();
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	                break;
	            }
				return false;
			}
		});
		
		droite.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	            	new AsyncDroite(com).execute();
	                break;
	            case MotionEvent.ACTION_UP:
	            	new AsyncStop(com).execute();
	                break;
	            }
				return false;
			}
		});
		
		photo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new AsyncPrendrePhoto("quentin.de.gr@gmail.com", com);
			}
		});
		
		video.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AsyncSendJSON sj = new AsyncSendJSON();
				
				String url = "https://apprtc.appspot.com/?r=";
				test t = new test();
				t.sendJson(url+i);
				
				
				Log.d("HINT ", ""+i);
				
				i++;
				
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse(url+i)); 
				startActivity(intent);
				
			}
		});
    }

    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onDestroy() {
    	
    	super.onDestroy();

    	new AsyncStop(com).execute();
    	
    	threadMap.interrupt();
    	threadMap = null;
    }
    
    @Override
    protected void onResume() {
    	
    	super.onResume();
    	
    	test t = new test();
		t.sendJson("http://www.google.fr");

    }
}