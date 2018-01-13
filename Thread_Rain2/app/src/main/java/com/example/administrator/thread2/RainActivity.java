package com.example.administrator.thread2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainActivity extends AppCompatActivity {

    int deviceWidth;
    int deviceHeight;
    Paint paint;
    FrameLayout canvas;
    Stage stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        // 현재 디바이스의 가로세로 크기 구하기
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;

        // 그림이 그려지는 곳. 도화지와 같은 곳이다
        stage = new Stage(getBaseContext());
        // 레이아웃이며, 이곳은 액자 같은 곳이다. 그린 그림을 이곳에 끼워 넣어 준다.
        canvas = (FrameLayout) findViewById(R.id.canvas);
        // 넣는 행위
        canvas.addView(stage);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRain();
            }
        });
    }

    // 1, 물방울 클래스 만들기
    class RainDrop extends Thread {
        float x;  // x좌표, drawCircle 값으로 float 가 들어가기 때문에 float 타입으로 선언했다.
        float y;  // y좌표
        float radius;  // 반지름
        int speed;     // 스피드

        Paint paint;   // 색

        int count = 0;
        @Override
        public void run() {
            while(y < deviceHeight) { // 아하! 순환문을 쓰지 않으면 count 값이 한 번만 증가해서 계속 제자리에 있군
                count++;
                y = speed * count;
                try {
                    Thread.sleep(10); // 스스로 0.1초에 한 번씩 스스로 움직이는 물방울이다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 2. 실제 물방울 만들기
    class Rains extends Thread {
        Random random = new Random();

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                RainDrop rainDrop = new RainDrop();
                rainDrop.x = random.nextInt(deviceWidth);  // x 좌표
                rainDrop.y = 0f;                           // y 좌표
                rainDrop.radius = random.nextInt(20) + 10; // 반지름
                rainDrop.speed = random.nextInt(5) + 20;   // 속도

                paint.setColor(Color.BLUE);                //색
                rainDrop.paint = paint;

                stage.addRainDrops(rainDrop); // 만들어진 만큼 계속 그려줘야 하기 때문에 배열로 해준다.

                rainDrop.start(); // 객체가 만들어 지면 움직이기 시작한다.

                try {
                    Thread.sleep(10); // 0.1 초에 한 개씩 만든다는 의미
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 3. 만들었으면 그려 넣어줘야 한다.
    class Stage extends View {

        List<RainDrop> rainDrops = new ArrayList<>();

        public Stage(Context context) {
            super(context);
            paint = new Paint();  // TODO 이것을 왜 여기에 넣은 것인가
            paint.setColor(Color.BLUE);
        }

        // invalidate 한 번 할 때마다 배열에 있는 모든 물방울 객체를 그려준다. 참고로 그 물방울 객체는 계속 y 값이 변하는 물방울이다.
        @Override
        protected void onDraw(Canvas canvas) {
            for( RainDrop rainDrop : rainDrops ){
                canvas.drawCircle(rainDrop.x, rainDrop.y, rainDrop.radius, rainDrop.paint);
            }
        }

        public void addRainDrops(RainDrop rainDrop){
            rainDrops.add(rainDrop);
        }
    }

    // 4. 화면 갱신 함수 -- onDraw 를 한 번 하면 더 이상 그려지지 않는다. 따라서 변화된 모습을 계속 그려줘야 하는데, 바로 invalidate 가 필요하다
    class DrawCanvas extends Thread{

        public void run(){
            while(true) {
                try {
                    Thread.sleep(100); // 영화를 보는 것과 같이 프레임 단위가 정교할수록 더 실제같은 느낌을 줄 수 있다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.postInvalidate(); // 스레드는 post 를 붙인 invalidate 를 사용한다.
            }
        }
    }

    // 5. 실행
    public void startRain(){

        Rains rains = new Rains(); // 물방울 만들기
        rains.start();

        DrawCanvas drawCanvas = new DrawCanvas();  // 그려주기
        drawCanvas.start();

    }

}
