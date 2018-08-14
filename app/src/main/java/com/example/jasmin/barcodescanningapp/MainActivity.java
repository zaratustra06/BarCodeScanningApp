package com.example.jasmin.barcodescanningapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

//import com.example.spinugost.*;

public class MainActivity extends Activity {
	
	public final static String PROSLIJEDI_SERVER = "";
//	public final static String PROSLIJEDI_FIRMU = "";

	
	private EditText server;
	
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		server = (EditText)findViewById(R.id.editTextServer);
//		firma = (EditText)findViewById(R.id.editTextFirma);
//		godina = (EditText)findViewById(R.id.editTextGodina);
		
//		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		
		preferences = getSharedPreferences("text", 0);
		String value = preferences.getString("srv",null);
//		String valueFirma = preferences.getString("frm",null);
//		String valueGodina = preferences.getString("god", null);
		
		if (value == null) {
			
			server.setHint("Unesite naziv servera");
			
		    // the key does not exist
		} else {
		    server.setText(value);
		}
		
//		if (valueFirma == null) {
//
//			firma.setHint("Unesite firmu/objekat");
//
//		    // the key does not exist
//		} else {
//		    firma.setText(valueFirma);
//		}
		
//		if (valueGodina == null) {
//
//			godina.setHint("Unesite radnu godinu");
//
//		    // the key does not exist
//		} else {
//		    godina.setText(valueGodina);
//		}
		
	}
	
	public void run(View view){
	      String s  = server.getText().toString();
//	      String f  = firma.getText().toString();
//	      String g  = godina.getText().toString();
	      
	      Editor editor = preferences.edit();
	      editor.putString("srv", s);
//	      editor.putString("frm", f);
//	      editor.putString("god", g);

	      editor.commit(); 
	      
	      Intent intent = new Intent(this, com.example.jasmin.barcodescanningapp.BarCodeScanning.class);
	      intent.putExtra(PROSLIJEDI_SERVER, s);
	      startActivity(intent);

	   }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_bar_code_scanning, menu);
		return true;
	}
	

}
