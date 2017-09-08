package com.proje.restoran;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;
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

public class kasa_2 extends Activity
{
    ListView lv1;
    TextView tv1;
    Button btn1;
    double tutar;
    String[] str1;
    String musteri;
    final Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kasa_2);

        tutar = 0;
        lv1 = (ListView) findViewById(R.id.listView1);
        tv1 = (TextView) findViewById(R.id.textView1);
        btn1 = (Button) findViewById(R.id.button1);
        musteri = getIntent().getExtras().getString("musteri");

        new mysql_select().execute(musteri);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), str1[position], Toast.LENGTH_LONG).show();
            }
        });
        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder( this);
        confirmDialog.setTitle("Sipariş Silme Onayı !");
        confirmDialog.setMessage("Ödeme alındı mı?");
        confirmDialog.setPositiveButton("Evet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new mysql_delete().execute(musteri);
                        dialog.cancel();
                        finish();
                    }
                });
        confirmDialog.setNegativeButton("Hayır",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.show();

            }
        });
    }



    public class mysql_select extends AsyncTask<String, Void, String> {
        private String[] s;
        @Override
        protected String doInBackground(String... params) {
            try {
                tutar = 0;
                String result = "";
                InputStream isr = null;
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("musteri", params[0]));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/kasa_2_1.php");
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
                s = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s[i] = json.getString("adet") +" adet "+ json.getString("urun") + "\t" + json.getString("tutar")+"TL";
                    tutar += json.getDouble("tutar");
                }
                str1=s;
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
        protected void onPostExecute(String result){
            if (s != null) {
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, s);
                lv1.setAdapter(adapter2);
                tv1.setText("Toplam = " + tutar + " TL");
            } else {
                String[] tmp1 = {"Masaya Kayitli Siparis Yok !"};
                str1=tmp1;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, tmp1);
                lv1.setAdapter(adapter2);
            }
        }
    }



    public class mysql_delete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("musteri", params[0]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/kasa_2_2.php");
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