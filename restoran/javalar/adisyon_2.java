package com.proje.restoran;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;
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

public class adisyon_2 extends Activity {
    ListView lv1;
    TextView tv1;
    TextView tv2;
    Spinner spn1;
    Spinner spn2;
    Button btn1;
    Button btn2;
    String tutar;
    String[] fiyatlar;
    String[] siparisler;
    String[] idler;
    String masano;
    final Context context=this;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adisyon_2);

        lv1 = (ListView) findViewById(R.id.listView1);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        spn1 = (Spinner) findViewById(R.id.spinner1);
        spn2 = (Spinner) findViewById(R.id.spinner2);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        masano = getIntent().getExtras().getString("masano");
        tv1.setText("MASA = "+masano+"\nURUN          ADET     TUTAR");
        tutar="0";
        tv2.setText(tutar + " TL");

        new urunleri_cek(this).execute();
        new siparis_cek(getApplicationContext()).execute(masano);


        String[] tmp1 = new String[8];
        for (int i = 0; i < 8; i++) 
        {
            tmp1[i] = (i + 1) + "";
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tmp1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn2.setAdapter(adapter1);
        spn2.setPrompt("Adet");

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                try {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Sipariş Detayı");

                    alertDialogBuilder
                            .setMessage(siparisler[position] + "\nSipariş Silinsin mi?")
                            .setCancelable(false)
                            .setPositiveButton("Evet",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    new mysql_delete().execute(idler[position]);
                                    new siparis_cek(getApplicationContext()).execute(masano);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
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


        spn1.setPrompt("Urun");
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tutar = fiyatlar[position];
                tv2.setText(tutar + " TL");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tutar = "0";
            }
        });


        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new insert().execute(masano, spn1.getSelectedItem().toString(), spn2.getSelectedItem().toString(), tutar);
                new siparis_cek(getApplicationContext()).execute(masano);
            }
        });

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public class siparis_cek extends AsyncTask<String, Void, String> {
        private Context context;
        private String sorun;
        private String[] s;
        public siparis_cek(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("masa", params[0]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/adisyon_2_1.php");
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
                String[] s2 = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s[i] = json.getString("urun") + "          " + json.getString("adet") + "     " + json.getString("tutar");
                    s2[i]= json.getString("id");
                }
                siparisler=s;
                idler=s2;
                return null;
            }
                catch(ClientProtocolException e)
                {
                    sorun=e.getMessage();
                    return null;
                }
                catch(IOException e)
                {
                    sorun=e.getMessage();
                    return null;
                }
                catch(Exception e)
                {
                    sorun=e.getMessage();
                    return null;
                }
            }
        @Override
        protected void onPostExecute(String result){
            if (s != null) {
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, s);
                lv1.setAdapter(adapter2);
            } else {
                String[] tmp1={ "Masaya Kayitli Siparis Yok !"};
                siparisler = tmp1;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, siparisler);
                lv1.setAdapter(adapter2);
            }
        }
        }



    public class urunleri_cek extends AsyncTask<String, Void, String> {
        private Context context;
        private String sorun;
        private String[] s1;
        public urunleri_cek(Context context) {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String result = "";
                InputStream isr = null;
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/adisyon_2_2.php");
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
                String[] s2 = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    s1[i] = json.getString("ad");
                    s2[i] = json.getString("fiyat");
                }
                fiyatlar=s2;
                return null;
            }
            catch(ClientProtocolException e)
            {
                sorun=e.getMessage();
                return null;
            }
            catch(IOException e)
            {
                sorun=e.getMessage();
                return null;
            }
            catch(Exception e)
            {
                sorun=e.getMessage();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result){
            if (s1 != null) {
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, s1);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn1.setAdapter(adapter3);
            }
            else{
                String[] tmp1 = {"Urun Yok"};
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tmp1);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn1.setAdapter(adapter3);
            }
        }
    }


    public class insert extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("musteri", params[0]));
                parametreler.add(new BasicNameValuePair("urun",params[1]));
                parametreler.add(new BasicNameValuePair("adet", params[2]));
                double a1, a2;
                a1 = Double.parseDouble(params[2]);
                a2 = Double.parseDouble(params[3]);
                parametreler.add(new BasicNameValuePair("tutar", (a1 * a2) + ""));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/adisyon_2_3.php");
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
    }

    public class mysql_delete extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                ArrayList<NameValuePair> parametreler = new ArrayList<NameValuePair>();
                parametreler.add(new BasicNameValuePair("id", params[0]));
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(getString(R.string.sunucu) + "/adisyon_2_4.php");
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