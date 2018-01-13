package com.example.administrator.thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ThreadEx extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        findViewById(R.id.btn_basic).setOnClickListener(this);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_rain).setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.btn_basic:
                intent = new Intent(this, ThreadBasicActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_test:
                intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_rain:
                intent = new Intent(this, RainingActivity.class);
                startActivity(intent);
                break;
        }
    }

}
