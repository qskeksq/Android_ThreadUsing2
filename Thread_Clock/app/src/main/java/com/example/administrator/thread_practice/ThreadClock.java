package com.example.administrator.thread_practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;


public class ThreadClock extends AppCompatActivity {

    int deviceWidth, deviceHeight;
    float center_x, center_y;
    double LINE;

    CustomClock customClock;
    Hand second_hand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customClock = new CustomClock(getBaseContext());
        setContentView(customClock);


        DisplayMetrics metrics = new DisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;

        center_x = deviceWidth/2;
        center_y = deviceHeight/2;
        LINE = center_x - 50;

        second_hand = new Hand(10, center_x, center_y, LINE, 1000);
        second_hand.start();

        new DrawClock().start();

    }

    class Hand extends Thread {

        Paint paint;

        float start_x, start_y;
        double line, end_x, end_y;

        int angle;
        int interval;

        public Hand(int stroke, float start_x, float start_y, double line, int interval){
            paint = new Paint();
            paint.setStrokeWidth(stroke);
            paint.setColor(Color.BLUE);

            this.start_x = start_x;
            this.start_y = start_y;
            this.line = line;
            this.interval = interval;
        }

        // 자체적으로 각도를 계속 변화시킨다.
        @Override
        public void run() {
            angle = angle + 6;
            double angle_temp = angle - 90;
            end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x;
            end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y;

            // 받아온 시간 간격으로 각도 변화.
            if(interval > 0) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 액티비티에 올릴 뷰, 침이 그려지는 곳은 이곳이다.
    class CustomClock extends View {

        public CustomClock(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawLine(second_hand.start_x, second_hand.start_y, (float)second_hand.end_x, (float)second_hand.end_y, second_hand.paint);
        }
    }

    boolean run = true;
    class DrawClock extends Thread {
        @Override
        public void run() {
            while(run) {
                customClock.postInvalidate();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        run = false;
    }
}
