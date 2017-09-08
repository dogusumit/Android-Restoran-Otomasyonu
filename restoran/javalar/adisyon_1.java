package com.proje.restoran;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Spinner;

public class adisyon_1 extends Activity
{
    int masa_say;
    Spinner spn1;
    Button btn1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adisyon_1);

        masa_say=Integer.parseInt(getString(R.string.masa_adet));

        spn1 = (Spinner) findViewById(R.id.spinner1);
        btn1 = (Button) findViewById(R.id.button1);

        String[] s = new String[masa_say];
        for (int i = 0; i < masa_say; i++) {
            s[i] = (i + 1) + "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, s);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(adapter);
        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adisyon_2.class);
                intent.putExtra("masano", spn1.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

}