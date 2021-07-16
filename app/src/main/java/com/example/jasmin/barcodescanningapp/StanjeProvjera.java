package com.example.jasmin.barcodescanningapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StanjeProvjera extends Activity {
	
	private String url1 = "";
	private String server = "";
	private String BarKod = "";
	private String BingoIliBela = "";
//	private String Firma = "";
//	private String Konobar = "";
	private HandleJSONStanjeProvjera obj;
	SharedPreferences preferences;
	
	//proba
	ArrayList<stavkeStanjeProvjera> stavkeList;
	
	
//	private EditText PrikazStola;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stanjeprovjera);
		
		stavkeList = new ArrayList<stavkeStanjeProvjera>();
		BarKod = getIntent().getExtras().getString(BarCodeScanning.PROSLIJEDI_BARKOD);
		
		Intent intent = getIntent();
//		String Sto = intent.getStringExtra(Stolovi.Sto);
		
//		PrikazStola = (EditText)findViewById(R.id.ProbaNarucivanje);
//		PrikazStola.setText(Sto);
		
		preferences = getSharedPreferences("text", 0);
//		Firma = preferences.getString("frm",null);
		server = preferences.getString("srv",null);
		BingoIliBela = preferences.getString("BiB","Bingo");
//		BarKod = preferences.getString("barkod",null);




       if (BingoIliBela == "Bela")
       {
           url1 = "http://"+ server +"/test.aspx?query=SELECT%20*%20FROM%20OPENDATASOURCE(%27SQLNCLI%27,%27Data%20Source=80.65.74.50;user%20id=spin;password=SulciC21%27).Spin.dbo.StanjeJasmin%20WHERE%20ArKod%20=%20%27"+BarKod+"%27%20order%20by%20Obj";

       }
       else {
          url1 = "http://"+ server +":8001/test.aspx?query=SELECT%20*%20FROM%20OPENDATASOURCE(%27SQLNCLI%27,%27Data%20Source=192.168.1.150;user%20id=spin;password=SulciC21%27).Spin.dbo.Stanje%20WHERE%20ArKod%20=%20%27" + BarKod + "%27%20order%20by%20Obj";
      }

        obj = new HandleJSONStanjeProvjera(url1);
	    obj.fetchJSON();
	    
	    try{
	    while(obj.parsingComplete);
	    JSONArray NarucivanjeArray = (obj.getArtikli());
	    JSONObject jObj = new JSONObject();
	    
	    int length = NarucivanjeArray.length();
	    List<String> listContents = new ArrayList<String>(length);

	      for (int i = 0; i < length; i++)
	      {
	    	jObj = NarucivanjeArray.getJSONObject(i);
//	    	listContents.add(jObj.getString("ObIme"));
	    	listContents.add(jObj.getString("ArSif")+" - "+jObj.getString("ArNa1")+" Stanje: "+jObj.getString("ArStaObj")+" Cijena: "+jObj.getString("ArMPC")+" Objekat: "+jObj.getString("Obj")+" - "+jObj.getString("ObN"));

//	        listContents.add(StoloviArray.getString(i));
	      }
	      ListView myListView = (ListView) findViewById(R.id.leftListViewNarucivanje);
	      
		  myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContents));
		  //proba
		  //adapter = new ActorAdapter(getApplicationContext(), R.layout.row, stavkeList);

		  
		  myListView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position,
										long id) {
	                Intent intent = new Intent(StanjeProvjera.this, StanjeProvjera.class);
	                String message = Integer.toString(position + 1);
//	                intent.putExtra(Sto, message);
//	                startActivity(intent);

	            }
	        });
		  
	    }
	    catch (Exception e) {
	          e.printStackTrace();
	    }
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_bar_code_scanning, menu);
		return true;
	}
	


}
