package com.example.cat_water;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("고양이랑 같이 물 마시라옹");


        TabHost tabhost = (TabHost) findViewById(R.id.Tabhost1);
        tabhost.setup();

        TabHost.TabSpec ts1 = tabhost.newTabSpec("cat");
        ts1.setContent(R.id.cat);
        ts1.setIndicator("cat");
        tabhost.addTab(ts1);

        TabHost.TabSpec ts2 = tabhost.newTabSpec("human");
        ts2.setContent(R.id.human);
        ts2.setIndicator("human");
        tabhost.addTab(ts2);

        TabHost.TabSpec ts3 = tabhost.newTabSpec("data");
        ts3.setContent(R.id.data);
        ts3.setIndicator("data");
        tabhost.addTab(ts3);


    }
}