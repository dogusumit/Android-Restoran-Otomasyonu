package com.proje.onlinesiparis;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class online_1 extends Activity
{
    EditText et1,et2;
    Button btn1,btn2;
    String adres;
    String sorun;
    String s1,s2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_1);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);


        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), online_3.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Form Gecisinde Sorun!", Toast.LENGTH_LONG).show();
                }
            }
        });


        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = et1.getText().toString();
                s2 = et2.getText().toString();
                if (s1.length() < 1 || s2.length() < 1)
                    Toast.makeText(getBaseContext(), "Butun Alanlari Doldurun!", Toast.LENGTH_LONG).show();
                else {

                    new mysql_select().execute(s1, s2);
                }
            }
        });

    }

    public class mysql_select extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("kul", params[0]));
                parametreler.add(new BasicNameValuePair("sfr", params[1]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/online_1.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                isr = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                isr.close();
                result = sb.toString();
                String s = "";
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s = json.getString("adres");
                }
                adres=s;
                return s;
            }
            catch (ClientProtocolException e)
            {
                sorun="Baglanti Problemi = "+e.getMessage();
                adres=null;
                return null;
            }
            catch (IOException e)
            {
                sorun="Input Problemi = "+e.getMessage();
                adres=null;
                return null;
            }
            catch (Exception e)
            {
                sorun="Hatali Giris !";
                adres=null;
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            if (adres!=null) {
                Intent i = new Intent(getApplicationContext(), online_2.class);
                i.putExtra("tel", s1);
                i.putExtra("adres",adres);
                startActivity(i);
            }
            else
            {
                Toast.makeText(getApplicationContext(),sorun, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}