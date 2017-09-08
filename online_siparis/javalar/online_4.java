package com.proje.onlinesiparis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class online_4 extends Activity
{
    EditText ed[]=new EditText[5];
    Button btn1;
    String telno;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_4);

        ed[0] = (EditText) findViewById(R.id.editText1);
        ed[1] = (EditText) findViewById(R.id.editText2);
        ed[2] = (EditText) findViewById(R.id.editText3);
        ed[3] = (EditText) findViewById(R.id.editText4);
        ed[4] = (EditText) findViewById(R.id.editText5);
        btn1 = (Button) findViewById(R.id.button1);
        telno = getIntent().getExtras().getString("tel");

        new bilgi_cek().execute(telno);

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
                    new bilgi_cek().execute(telno);
                }
                else
                    Toast.makeText(getBaseContext(), "Lutfen Butun Alanlari Doldurun !",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class mysql_insert extends AsyncTask<String,Void,String>
    {
        private String s1;
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("isim",params[0]));
                parametreler.add(new BasicNameValuePair("telno",params[1]));
                parametreler.add(new BasicNameValuePair("sifre",params[2]));
                parametreler.add(new BasicNameValuePair("email",params[3]));
                parametreler.add(new BasicNameValuePair("adres", params[4]));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu)+"/online_4_2.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                s1="OK";
                return null;
            }
            catch (ClientProtocolException e)
            {
                s1="Baglanti Problemi = "+e.getMessage();
                return null;
            }
            catch (IOException e)
            {
                s1="Input Problemi = "+e.getMessage();
                return null;
            }
            catch (Exception e)
            {
                s1=e.getMessage();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if(s1.equals("OK"))
                Toast.makeText(getBaseContext(),"Guncelleme Basarili !",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getBaseContext(),s1,Toast.LENGTH_LONG).show();
        }
    }


    public class bilgi_cek extends AsyncTask<String, Void, String> {
        private String[] s1=new String[5];
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("telno",params[0]));
                String result = "";
                InputStream isr = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/online_4_1.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
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
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s1[0] = json.getString("isim");
                    s1[1] = json.getString("telno");
                    s1[2] = json.getString("sifre");
                    s1[3] = json.getString("email");
                    s1[4] = json.getString("adres");
                }

                return null;

            } catch (ClientProtocolException e) {
                for(int i=0;i<5;i++)
                {s1[i]=e.getMessage();}
                return null;
            } catch (IOException e) {
                for(int i=0;i<5;i++)
                {s1[i]=e.getMessage();}
                return null;
            } catch (Exception e) {
                for(int i=0;i<5;i++)
                {s1[i]=e.getMessage();}
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            if (s1 != null) {
                for(int i=0;i<5;i++)
                {
                    ed[i].setText(s1[i]);
                }
            }
            else{
                for(int i=0;i<5;i++)
                {
                    ed[i].setText("Baglanti Problemi!");
                }
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}