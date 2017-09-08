package com.proje.restoran;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Timer;
import java.util.TimerTask;

public class mutfak extends Activity
{
    ListView lv1;
    String[] str1;
    String[] idler;
    final Handler handler = new Handler();
    final Context context=this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mutfak);

        lv1 = (ListView) findViewById(R.id.listView1);


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Sipariş Detayı");
                    alertDialogBuilder
                            .setMessage(str1[position] + "\nSipariş Silinsin mi?")
                            .setCancelable(false)
                            .setPositiveButton("Evet",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    new mysql_delete().execute(idler[position]);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

            TimerTask listdoldur = new TimerTask() {
                @Override

                public void run() {
                    handler.post(new Runnable() {
                                     public void run() {
                                         try {
                                             new mysql_cek().execute(); }
                                         catch (Exception e){     }
                                     }
                                 }

                    );
                }
            };

            Timer timer = new Timer();
            timer.schedule(listdoldur, 500, 5000);

    }


    public class mysql_cek extends AsyncTask<String, Void, String> {
        String[] s;
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/mutfak_1.php");
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
                String[] s2 = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++)
                {
                    JSONObject json = jArray.getJSONObject(i);
                    s[i] = json.getString("adet")+"\t" + json.getString("urun") + "\t   Musteri=" + json.getString("musteri");
                    s2[i] = json.getString("id");
                }
                str1=s;
                idler=s2;
                return null;
            }
            catch(ClientProtocolException e)
            {
                return null;
            }
            catch(IOException e)
            {
                return null;
            }
            catch(Exception e)
            {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            if (s != null) {
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, s);
                lv1.setAdapter(adapter2);
            } else {
                String[] tmp1={ "Kayitli Siparis Yok !"};
                str1 = tmp1;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, str1);
                lv1.setAdapter(adapter2);
            }
        }
    }
    public class mysql_delete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("id", params[0]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/mutfak_2.php");
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