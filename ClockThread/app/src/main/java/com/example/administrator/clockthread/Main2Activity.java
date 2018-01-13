package com.example.administrator.clockthread;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    boolean RUN_FLAG = true;
    int deviceHeight;
    int deviceWidth;
    int center_x, center_y;
    int LINE = 0;

    CustomView stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stage = new CustomView(getBaseContext());
        setContentView(stage);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceHeight = metrics.heightPixels;
        deviceWidth = metrics.widthPixels;
        center_x = deviceWidth / 2;
        center_y = deviceHeight / 2;
        LINE = center_x - 50;

        Hand hand = new Hand(20, center_x, center_y, LINE, 100);
        stage.addHand(hand);

        new DrawStage().start();
    }

    class Hand extends Thread {
        Paint paint;
        float start_x, start_y;

        double angle, line;
        double end_x, end_y;

        int interval;

        public Hand(int stroke, int start_x, int start_y, int length, int interval){
            paint = new Paint();
            paint.setStrokeWidth(stroke);
            paint.setColor(Color.BLUE);
            this.start_x = start_x;
            this.start_y = start_y;
            line = length;
            this.interval = interval;
        }

        @Override
        public void run() {
            super.run();
            while(RUN_FLAG) {
                // 이게 밖으로 나가면 안되나?
                angle = angle + 1;
                double angle_temp = angle - 90;
                end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x; // x좌표 구하는 식
                end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y; // y좌표 구하는 식

                if(interval > 0){
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class CustomView extends View {

        List<Hand> hands = new ArrayList<>();

        public CustomView(Context context) {
            super(context);
        }

        public void addHand(Hand hand){
            hands.add(hand);
            hand.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for( Hand hand : hands){
                canvas.drawLine(hand.start_x, hand.start_y, (float)hand.end_x, (float)hand.end_y, hand.paint);
            }
        }
    }

    class DrawStage extends Thread {
        @Override
        public void run() {
//            super.run();
            while(true){
                stage.postInvalidate();
            }
        }
    }

    public void onPause(){
        super.onPause();
        RUN_FLAG = false;
    }

}
