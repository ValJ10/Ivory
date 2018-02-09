package com.iter.ivory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class VaccineInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccine_info_layout);

        Intent i = getIntent();
        TextView vaccination = findViewById(R.id.vac);
        vaccination.setText(i.getStringExtra("name"));

        Button buttonLink = findViewById(R.id.button2);


    }

}
