package com.example.administrator.thread_practice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int SET_NUM = 10;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textVIew);

        BackThread backThread = new BackThread(mHandler);
        backThread.start();

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case SET_NUM:
                    textView.setText(msg.arg1+"");
                    break;
            }
        }

    };

}


class BackThread extends Thread {

    int count;
    Handler handler;

    public BackThread(Handler handler){
        this.handler = handler;
    }

//    @Override
//    public void run() {
//        while(count < 40) {
//            count++;
//            Message msg = new Message();
//            msg.what = MainActivity.SET_NUM;
//            msg.arg1 = count;
//            handler.sendMessage(msg);
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void run() {
        while(count < 40) {
            count++;
            Message msg = new Message();
            msg.what = MainActivity.SET_NUM;
            msg.arg1 = count;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 여기서 메인 UI 를 변경하려면 위젯을 받아야 하기 때문에 조금 불편하군. 핸들러를 넘겨 받는 것이 가볍다.
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
