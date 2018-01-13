package com.example.administrator.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ThreadBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic);

//--------------------------------------------------------------------------------------------------

                // 1. 스레드 생성
        Thread thread1 = new Thread(){ //익명 객체 아님!! 소스코드량이 적을 경우 1, 2 다만 스레드로 쓴다는 말은
                                        // 코드가 분리가 된다는 말이기 때문
            @Override
            public void run(){
                Log.i("ThreadEx start", "Hello world1");
            }

        };

        // 2. 스레드 실행
        thread1.start(); // run() 함수를 실행시켜 준다.

//--------------------------------------------------------------------------------------------------

        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                Log.i("ThreadEx start", "Hello world2");
            }
        };

        new Thread(thread2).start();

//--------------------------------------------------------------------------------------------------

        CustomThread thread3 = new CustomThread();
        thread3.start();


//--------------------------------------------------------------------------------------------------
        Thread thread4 = new Thread(new CustomRunnable());
        thread4.start();



//       실행해 보면 알겠지만 스레드는 순서대로 호출되지 않는다. 다시 말하면 순차적으로 진행되는 것에 대해서는 스레드를 쓰지 않는다.

    }
}


// 실제로는 이렇게 객체를 만들고 스레드를 상속받아 사용함
class CustomThread extends Thread  { // 보통은 이렇게 클래스로 상속 받아서 사용
    @Override
    public void run(){
        Log.i("ThreadEx start", "Hello world3");
    }

}

class CustomRunnable implements Runnable { // 스레드가 많을 경우 이렇게 사용
    @Override
    public void run() {
        Log.i("ThreadEx start", "Hello world4");
    }
}
