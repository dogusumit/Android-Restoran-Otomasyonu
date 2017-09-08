package com.proje.restoran;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
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

public class admin extends Activity
{
    TextView tv1;
    TextView tv2;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    EditText ed4;
    EditText ed5;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Spinner spn1;
    Spinner spn2;
    String[] kul_idler;
    String[] urun_idler;
    String[] kullanicilar;
    String[] urunler;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);
        ed5 = (EditText) findViewById(R.id.editText5);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        spn1 = (Spinner) findViewById(R.id.spinner1);
        spn2 = (Spinner) findViewById(R.id.spinner2);


        new kullanici_cek().execute();
        new urun_cek().execute();


        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new kullanici_ekle().execute(ed1.getText().toString(),ed2.getText().toString(),ed3.getText().toString());
                new kullanici_cek().execute();
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new urun_ekle().execute(ed4.getText().toString(), ed5.getText().toString());
                new urun_cek().execute();
            }
        });

        btn3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    new kullanici_sil().execute(kul_idler[spn1.getSelectedItemPosition()]);
                    new kullanici_cek().execute();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btn4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    new urun_sil().execute(urun_idler[spn2.getSelectedItemPosition()]);
                    new urun_cek().execute();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    public class kullanici_ekle extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();

                parametreler.add(new BasicNameValuePair("user", params[0]));
                parametreler.add(new BasicNameValuePair("sifre", params[1]));
                parametreler.add(new BasicNameValuePair("yetki", params[2]));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/admin_1.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
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
            Toast.makeText(getBaseContext(), "Kayıt Basarili !", Toast.LENGTH_LONG).show();
        }
    }

    public class urun_ekle extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("ad", params[0]));
                parametreler.add(new BasicNameValuePair("fiyat", params[1]));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/admin_2.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
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
            Toast.makeText(getBaseContext(), "Kayıt Basarili !", Toast.LENGTH_LONG).show();
        }
    }


    public class kullanici_cek extends AsyncTask<String, Void, String> {
        private String[] s1;
        private String[] s2;
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/admin_3.php");
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
                s1 = new String[jArray.length()];
                s2 = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s2[i]=json.getString("id");
                    s1[i] = json.getString("user")+"\t"+json.getString("yetki");
                }
                kullanicilar=s1;
                kul_idler=s2;
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
            if(s1!=null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, s1);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn1.setAdapter(adapter);
            }
            else
            {
                String[] tmp1={"Kayıtlı Kullanici Yok !"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, tmp1);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn1.setAdapter(adapter);
            }
        }
    }


    public class urun_cek extends AsyncTask<String, Void, String> {
        private String[] s1;
        private String[] s2;
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/admin_4.php");
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
                s1 = new String[jArray.length()];
                s2 = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s2[i]=json.getString("id");
                    s1[i] = json.getString("ad")+"\t"+json.getString("fiyat");
                }
                urunler=s1;
                urun_idler=s2;
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
            if(s1!=null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, s1);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn2.setAdapter(adapter);
            }
            else
            {
                String[] tmp1={"Kayıtlı Urun Yok !"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, tmp1);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn2.setAdapter(adapter);
            }
        }
    }

    public class kullanici_sil extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("id", params[0]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/admin_5.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                return null;
            }
            catch (ClientProtocolException e) {
                return null;
            } catch (IOException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public class urun_sil extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("id", params[0]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/admin_6.php");
                httppost.setEntity(new UrlEncodedFormEntity(parametreler));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                return null;
            }
            catch (ClientProtocolException e) {
                return null;
            } catch (IOException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}