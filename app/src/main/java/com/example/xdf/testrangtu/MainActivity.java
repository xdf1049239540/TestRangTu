package com.example.xdf.testrangtu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    RangTuView rangTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rangTu= (RangTuView) findViewById(R.id.rangTu);
    }
    public void start(View v){
        rangTu.startAnim();
    }
}
