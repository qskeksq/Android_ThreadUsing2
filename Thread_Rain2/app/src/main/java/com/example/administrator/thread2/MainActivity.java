package com.example.administrator.thread2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_rain).setOnClickListener(this);
        findViewById(R.id.btn_rain2).setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_test:
                intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_rain:
                intent = new Intent(this, RainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_rain2:
                intent = new Intent(this, RainActivity2.class);
                startActivity(intent);
                break;
        }
    }
}
