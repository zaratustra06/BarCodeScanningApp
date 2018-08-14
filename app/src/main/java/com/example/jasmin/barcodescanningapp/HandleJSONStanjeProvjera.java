package com.example.jasmin.barcodescanningapp;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleJSONStanjeProvjera {


//	   private String korisnik = "korisnik";
//	   private String lozinka = "lozinka";
	   private String urlString = null;

	   public volatile boolean parsingComplete = true;
	   public HandleJSONStanjeProvjera(String url){
	      this.urlString = url;
	   }
	   public JSONArray getArtikli(){
	      return jarray;
	   }


	   public JSONArray jarray;
	   @SuppressLint("NewApi")
	   public void readAndParseJSON(String in) {
	      try {
//	         JSONObject reader = new JSONObject(in);
	  	    try {
		        jarray = new JSONArray(in);
		        //System.out.println(""+jarray);
		    } catch (JSONException e) {
		        Log.e("JSON Parser", "Error parsing data " + e.toString());
		    }


        parsingComplete = false;

       } catch (Exception e) {

          e.printStackTrace();
       }

	   }

	   public void fetchJSON(){
		      Thread thread = new Thread(new Runnable(){
		         @Override
		         public void run() {
		         try {
		            URL url = new URL(urlString);
		            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		            conn.setReadTimeout(10000 /* milliseconds */);
		            conn.setConnectTimeout(15000 /* milliseconds */);
		            conn.setRequestMethod("GET");
		            conn.setDoInput(true);
		            // Starts the query
		            conn.connect();
		         InputStream stream = conn.getInputStream();

		      String data = convertStreamToString(stream);

		      readAndParseJSON(data);
		         stream.close();

		         } catch (Exception e) {
		            e.printStackTrace();
		         }
		         }
		      });

		       thread.start();
		   }
		   static String convertStreamToString(java.io.InputStream is) {
		      java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		      return s.hasNext() ? s.next() : "";
		   }
}
