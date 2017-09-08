package com.proje.restoran;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Spinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class kasa_1 extends Activity
{
    Spinner spn1;
    Button btn1;
    String[] masalar;
    final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kasa_1);

        spn1 = (Spinner) findViewById(R.id.spinner1);
        btn1 = (Button) findViewById(R.id.button1);


        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), kasa_2.class);
                intent.putExtra("musteri", spn1.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        TimerTask listdoldur = new TimerTask() {
            @Override

            public void run() {
                handler.post(new Runnable() {
                                 public void run() {
                                     new masa_cek().execute();
                                 }
                             }

                );
            }
        };

        Timer timer = new Timer();
        timer.schedule(listdoldur, 500, 5000);

    }



    public class masa_cek extends AsyncTask<String, Void, String> {
        private String[] s;
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/kasa_1.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                isr = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                isr.close();
                result = sb.toString();
                JSONArray jArray = new JSONArray(result);
                s = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s[i] = json.getString("musteri");
                }
                masalar=s;
                return null;
            } catch (ClientProtocolException e) {
                return null;
            } catch (IOException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if(s!=null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, s);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn1.setAdapter(adapter);
            }
            else
            {
                String[] tmp1={"Kayıtlı Sipariş Yok !"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, tmp1);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn1.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}