package com.proje.restoran;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
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

public class main extends Activity {
    EditText et1, et2;
    Button btn1;
    String yetki;
    final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        btn1 = (Button) findViewById(R.id.button1);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1, s2;
                s1 = et1.getText().toString();
                s2 = et2.getText().toString();
                if (s1.length() < 1 || s2.length() < 1)
                    Toast.makeText(getBaseContext(), "Butun Alanlari Doldurun!", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        new mysql_select().execute(s1, s2);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
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
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/main.php");
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
                String s = new String();
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s = json.getString("yetki");
                }
                yetki=s;
                return s;
            }
            catch (ClientProtocolException e)
            {
                yetki="Baglanti Problemi = "+e.getMessage();
                return null;
            }
            catch (IOException e)
            {
                yetki="Input Problemi = "+e.getMessage();
                return null;
            }
            catch (Exception e)
            {
                yetki="Hatalı Giriş";
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if ("admin".equals(yetki)) {
                try {
                    Intent i = new Intent(getApplicationContext(), admin.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Form Gecisinde Sorun!", Toast.LENGTH_LONG).show();
                }
            } else if ("mutfak".equals(yetki)) {
                try {
                    Intent i = new Intent(getApplicationContext(), mutfak.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Form Gecisinde Sorun!", Toast.LENGTH_LONG).show();
                }
            } else if ("adisyon".equals(yetki)) {
                try {
                    Intent i = new Intent(getApplicationContext(), adisyon_1.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Form Gecisinde Sorun!", Toast.LENGTH_LONG).show();
                }
            } else if ("kasa".equals(yetki)) {
                try {
                    Intent i = new Intent(getApplicationContext(), kasa_1.class);
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Form Gecisinde Sorun!", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),yetki, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}
