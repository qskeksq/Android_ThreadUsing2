package com.example.administrator.thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 실행되고 있을 때 다른 버튼을 누르는 실험을 해보자!!

        // 1. 버튼을 클릭하면 1부터 10만까지 출력하는 함수를 실행
        findViewById(R.id.run10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print100t("MainThread ");
            }
        });

        // 2. Thread 클래스에서 1부터 10만까지 출력하는 함수를 실행


        new Thread(){
            @Override
            public void run() {
                print100t("SubThread ");
            }
        }.start();


        // 3. 위의 Thread 클래스의 실행순서를 1번과 바꿔서 실행

    }

    // 이 메소드는 스레드를 생성하지 않고 실행했기 때문에 메인에서 돌아가고, 여러번 클릭하면 메인 스레드 큐에 쌓일 수밖에 없다.
    public void print100t(String caller){
        for(int i=0; i<100; i++){
            Log.i("100T", caller + "current number ==================== "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void logThread(){

    }


}
