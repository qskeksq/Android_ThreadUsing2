package com.example.administrator.thread2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 메인 스레드에서 실행된다. 100번 도는 것이 1회 할 일이기 때문에 버튼을 여러번 누르면 메인 스레드에 큐로 계속해서 쌓이게 된다.
        findViewById(R.id.btn_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainThread("main");
            }
        });

        // sub 수동 스레드 실행
        findViewById(R.id.btn_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomThread().start();
            }
        });

        // 스레드 생성 방법1 -- sub 스레드 자동 실행
        // sub 로 새롭게 만든 스레드이다.
        new Thread(){
            @Override
            public void run() {
                mainThread("sub");
            }
        }.start();

        // 스레드 생성 방법2 -- sub 스레드 자동 실행
        // runnable 은 Thread 안에서만 작동된다.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mainThread("runnable");
            }
        };
        new Thread(runnable).start();

        // 스레드 생성 방법3 -- sub 스레드 자동 실행
        CustomThread customThread = new CustomThread();
        customThread.start();

        // 스레드 생성 방법4 -- sub 스레드 자동 실행
        CustomRunnable customRunnable = new CustomRunnable();
        new Thread(customRunnable).start();
    }


    public void mainThread(String caller){
        for(int i=0; i<100; i++){
            Log.i("100T", caller + "================"+i);
            try {
                Thread.sleep(10); // 0.01초 멈추고 다음을 출력한다.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// 스레드 클래스를 상속받아 스레드 만드는 방법
class CustomThread extends Thread {
    @Override
    public void run() {
        for(int i=0; i<100; i++){
            Log.i("100T", "CustomThread"+ i +"================"+i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Runnable 인터페이스를 구현해서 스레드를 만드는 방법
class CustomRunnable implements Runnable{
    @Override
    public void run() {
        for(int i=0; i<100; i++){
            Log.i("100T", "CustomRunnable ================"+i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


