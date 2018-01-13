package com.example.administrator.thread2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class RainActivity2 extends AppCompatActivity {

    FrameLayout frame;
    int deviceWidth, deviceHeight;
    List<RainDrop> rainDrops;
    DrawRains drawRains;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain2);

        frame = (FrameLayout) findViewById(R.id.canvas2);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;

        drawRains = new DrawRains(getBaseContext());
        frame.addView(drawRains);

        findViewById(R.id.btn_start2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    // 1. 물방울 클래스 만들기
    class RainDrop extends Thread{

        float x;
        float y;
        float radius;
        int speed;

        Paint paint;
        int count = 0;

        // 물방울 만들고 start 해 주면 시간이 지나면서 스스로 x, y 값이 바뀐다.
        @Override
        public void run() {
            while(y < deviceHeight) {
                count++;
                y = count*speed;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 2. 물방울 객체 생성하기
    class Rains extends Thread {
        Random random = new Random();

        @Override
        public void run() {
            rainDrops = new ArrayList<>();
            for(int i=0; i<1000; i++){
                RainDrop rainDrop = new RainDrop();
                rainDrop.x = random.nextInt(deviceWidth);
                rainDrop.y = 0f;
                rainDrop.speed = random.nextInt(5) + 10;
                rainDrop.radius = random.nextInt(25) + 5;

                // 난수 생성을 통해 색이 변하는 물방울
                int randomColor = 0;
                switch (random.nextInt(5)){
                    case 0 : randomColor = Color.BLUE; break;
                    case 1 : randomColor = Color.GREEN; break;
                    case 2 : randomColor = Color.YELLOW; break;
                    case 3 : randomColor = Color.MAGENTA; break;
                    case 4 : randomColor = Color.RED; break;
                    case 5 : randomColor = Color.CYAN; break;
                }
                Paint paint = new Paint();
                paint.setColor(randomColor);
                rainDrop.paint = paint;

//                rainDrops.add(rainDrop);
                rainDrops.add(rainDrop);
                Log.i("check", "==================="+i);

                // 생성된 직후부터 y 값이 변한다.
                rainDrop.start();

                try {
                    Thread.sleep(100); // TODO 이것을 10으로 바꾸면 왜 자꾸 멈추는 것인가
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 3. 그려주기
    class DrawRains extends View {

        public DrawRains(Context context){
            super(context);
        }

        public void onDraw(Canvas canvas){
            if(rainDrops != null){
                for(RainDrop rainDrop : rainDrops){
                    canvas.drawCircle(rainDrop.x, rainDrop.y, rainDrop.radius, rainDrop.paint);
                }
            }
        }

    }

    // 4. 새로 그려주기
    class RenewCanvas extends Thread{

        @Override
        public void run() {
            while(true) { // 이것도 계속 반복해서 해주지 않으면
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                drawRains.postInvalidate();
            }
        }
    }

    // 4. 시작
    public void start(){
        Rains rains = new Rains();
        rains.start();

        RenewCanvas renewCanvas = new RenewCanvas();
        renewCanvas.start();
    }

}
