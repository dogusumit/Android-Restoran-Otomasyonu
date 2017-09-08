package com.proje.onlinesiparis;

import android.app.Activity;
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

public class online_3 extends Activity
{
    EditText ed[]=new EditText[5];
    Button btn1;
    String yaz;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_3);

        ed[0] = (EditText) findViewById(R.id.editText1);
        ed[1] = (EditText) findViewById(R.id.editText2);
        ed[2] = (EditText) findViewById(R.id.editText3);
        ed[3] = (EditText) findViewById(R.id.editText4);
        ed[4] = (EditText) findViewById(R.id.editText5);
        btn1 = (Button) findViewById(R.id.button1);

        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bos=false;
                for(int i=0;i<5;i++)
                {
                    if(ed[i].getText().toString().trim().equals(""))
                    bos=true;
                }
                if(!bos)
                {
                    new mysql_insert().execute(ed[0].getText().toString(),ed[1].getText().toString(),ed[2].getText().toString(),ed[3].getText().toString(),ed[4].getText().toString());
                }
                else
                    Toast.makeText(getBaseContext(), "Lutfen Butun Alanlari Doldurun !",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class mysql_insert extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("isim",params[0]));
                parametreler.add(new BasicNameValuePair("telno",params[1]));
                parametreler.add(new BasicNameValuePair("sifre",params[2]));
                parametreler.add(new BasicNameValuePair("email",params[3]));
                parametreler.add(new BasicNameValuePair("adres",params[4]));
            
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu)+"/online_3.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                isr = entity.getContent();
            
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                isr.close();
                result=sb.toString();
                String s="";
                JSONArray jArray = new JSONArray(result);
                JSONObject json = jArray.getJSONObject(0);
                s =json.getString("isim");
                yaz=s;
                return null;
            }
            catch (ClientProtocolException e)
            {
                yaz="Baglanti Problemi = "+e.getMessage();
                return null;
            }
            catch (IOException e)
            {
                yaz="Input Problemi = "+e.getMessage();
                return null;
            }
            catch (Exception e)
            {
                yaz=e.getMessage();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if(yaz.equals("OK"))
                Toast.makeText(getBaseContext(),"Kayit Basarili !",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(),yaz,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}