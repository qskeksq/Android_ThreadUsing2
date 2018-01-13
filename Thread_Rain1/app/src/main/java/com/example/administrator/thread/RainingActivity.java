package com.example.administrator.thread;

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

public class RainingActivity extends AppCompatActivity {

    FrameLayout ground;
    Stage stage;

    int deviceWidth;
    int deviceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raining);

        ground = (FrameLayout) findViewById(R.id.stage);

        // 디바이스 가로 세로 값 구하는 방법
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;



        // 커스텀 뷰를 생성하고
        stage = new Stage(getBaseContext());
        // 레이아웃에 담아주면 화면에 커스텀뷰의 내용이 출력된다.
        ground.addView(stage);

//        // 빗방울 하나를 생성해서 stage 에 더해준다.
//        RainDrop rainDrop = new RainDrop();
//        rainDrop.radius = 30f; // 픽셀 단위
//        rainDrop.x = 200f;
//        rainDrop.y = 0;
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        rainDrop.paint = paint;

//        stage.addrainDrop(rainDrop);
//
//        for(int i=0; i<1000; i++){
//            rainDrop.y = rainDrop.speed*i;
//            stage.invalidate(); // 이거 왜 하는거라고?
//
//            try{
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

    public void raintask(){

        // 빗방울을 1초마다 1개씩 랜덤하게
        Rain rain = new Rain();
        rain.start();

        // 화면을 1초마나 한번씩 갱신

    }

    // 화면을 1초에 한번ㅁ씩 그려주는 (onDraw 호출)
    class DrawCanvas extends Thread {

    }

//    빗방울을 만들어 주는 클래스
    class Rain extends Thread {
    @Override
    public void run() {
        // 특정 범위의 숫자를 랜덤하게 생성할 때 사용
        Random random = new Random();

        // 빗방울 하나를 생성해서 stage 에 더해준다.
        RainDrop rainDrop = new RainDrop();
        rainDrop.radius = 30f; // 픽셀 단위
        rainDrop.x = random.nextInt(deviceWidth); // 디바이스 가로 사이즈 0~ 디바이스 가로 사이즈 사이
        rainDrop.y = 0;
        rainDrop.speed = random.nextInt(10) + 5;
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        rainDrop.paint = paint;

        // 생성한 빗방울을 stage 에 더해준다.
        stage.addrainDrop(rainDrop);


        for(int i=0; i<1000; i++){
            rainDrop.y = rainDrop.speed*i;
//            stage.invalidate(); // 이거 왜 하는거라고? // invalidate 는 메인 함수에서 작동하는 메소드라서 서브에서는 좀 다르다.
            stage.postInvalidate();
            try{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


    // 뷰를 상속하면 뭔가 그릴 수 있는 조건이 주어진다.
    class Stage extends View {

        Paint color;
//        RainDrop rainDrop;
        List<RainDrop> rainDrops = new ArrayList<>();

        public Stage(Context context){
            super(context);
            color = new Paint();
            color.setColor(Color.BLUE);
        }

        // 화면에 로드되는 순간 호출되는 함수
        // 시스템에서 호출하는 메소드
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //               x좌표 y좌표 반지름 붓속성
//            if(rainDrop != null){
//                canvas.drawCircle(rainDrop.x,  rainDrop.y, rainDrop.radius, rainDrop.paint);
//            }
            for( RainDrop rainDrop : rainDrops){
                canvas.drawCircle(rainDrop.x,  rainDrop.y, rainDrop.radius, rainDrop.paint);
            }


        }

//        public void addrainDrop(RainDrop raindrop){
//            this.rainDrop = raindrop;
//        }
        public void addRainDrop(RainDrop rainDrop){
            rainDrops.add(rainDrop);
        }
    }


    // 빗방울을 하나 만드는 클래스 빗방울의 속성이 저장된 클래스
    class RainDrop extends Thread {

        float radius;
        Paint paint;
        int speed;
        float x; //왜 float 인지 안 궁금하냐
        float y;

        boolean run = true;

        @Override
        public void run() {
            int count = 0;
            while(y <deviceHeight) {
                count++;
                y = count * speed;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        run = false;
    }
}
