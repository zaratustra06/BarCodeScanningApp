package com.example.jasmin.barcodescanningapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NavUtils;



 public class BarCodeScanning extends AppCompatActivity implements OnClickListener {
        private Button scanBtn;
        private Button stanjeBtn;
        private TextView formatTxt, contentTxt;
        private EditText contentBarkod;
//      private String server = "";
        public  String barkod = "";
        public String scanContent = "";
       public final static String PROSLIJEDI_BARKOD = "";
       String BingoBela ="";

        SharedPreferences preferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bar_code_scanning);

            scanBtn = (Button) findViewById(R.id.scan_button);
            stanjeBtn = (Button) findViewById(R.id.stanje);
            formatTxt = (TextView) findViewById(R.id.scan_format);
            contentTxt = (TextView) findViewById(R.id.scan_content);
            contentBarkod = (EditText) findViewById(R.id.editBarKod);

            Intent intent = getIntent();
//          server = intent.getStringExtra(MainActivity.PROSLIJEDI_SERVER);

            scanBtn.setOnClickListener(this);
            stanjeBtn.setOnClickListener(this);

            final Switch sw = findViewById(R.id.switch2);
            if (sw != null) {
                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String msg = isChecked ? "Bela" : "Bingo";
                        Toast.makeText(BarCodeScanning.this, msg, Toast.LENGTH_SHORT).show();
                        sw.setText(msg);
                        BingoBela = isChecked ?  "Bela" : "Bingo";
                    }
                });
            }

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_bar_code_scanning, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
        public void onClick(View v){
        //respond to clicks
            if(v.getId()==R.id.scan_button){
             //   barkod = "";
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //scan
                scanIntegrator.initiateScan();
            }
                if (v.getId()==R.id.stanje){
                barkod = contentBarkod.getText().toString();

                preferences = getSharedPreferences("text", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("barkod", scanContent);
                editor.putString("BiB", BingoBela);
                editor.commit();

                Intent intent = new Intent(this, com.example.jasmin.barcodescanningapp.StanjeProvjera.class);

                    intent.putExtra(PROSLIJEDI_BARKOD,barkod);
                startActivity(intent);
            }
        }


        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            //retrieve result of scanning - instantiate ZXing object
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            //check we have a valid result
            if (scanningResult != null) {
                //get content from Intent Result
   //             barkod = "";
                scanContent = scanningResult.getContents();
    //            barkod = scanContent;
                //get format name of data scanned
                String scanFormat = scanningResult.getFormatName();
                //output to UI
                formatTxt.setText("FORMAT: "+scanFormat);
                contentTxt.setText(""+scanContent);
                contentBarkod.setText(""+scanContent);

            }
            else{
                //invalid scan data or scan canceled
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }


    }

