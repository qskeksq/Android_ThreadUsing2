package com.example.administrator.clockthread;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int deviceHeight, deviceWidth;
    int center_x, center_y;
    int LINE;
    double angle;

    double end_x;
    double end_y;

    CustomClock stage;
    ClockMinute minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stage = new CustomClock(getBaseContext());
        setContentView(stage); // 이거 오버로딩 되어서 여러 인자를 받을 수 있다.

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceHeight = metrics.heightPixels;
        deviceWidth = metrics.widthPixels;
        center_x = deviceWidth/2;
        center_y = deviceHeight/2;

        LINE = center_x - 50; // 단위가 없으면 pixel 이군


        new DrawClock().start();

        minute = new ClockMinute();
        minute.start(); // 일단 onDraw 에서 하면 절대 안 된다./ 커스텀뷰 생성자에서도 안 됨.

        // 스레드를 죽이는 방법 1. setDaemon  2. 플래그로 죽이기

    }

    class ClockSecond extends Thread {

        double angle;
        double angle_temp = angle - 90;

        double end_x;
        double end_y;

        @Override
        public void run() {
            while(true) {
                angle = angle + 3.6;
                end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x;
                end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ClockMinute extends Thread {

        double angle = 0;
//        double angle_temp = angle - 90;
//        double end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x;
//        double end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y;
//        double angle_temp = angle - 90;
//        double end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x;;
//        double end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y;

        double end_x;
        double end_y;

        @Override
        public void run() {
            super.run();
            while(RUN_FLAG) {
                angle = angle + 6;    // 이것을 밖으로 빼내면 왜 안 되는가
                double angle_temp = angle - 90;
                end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x;
                end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 스레드 : 코드가 있는 실제 클래스이기 때문에 실행할 수 있다. 구현되어 있는 구현체이다.
    // 러너블 : 껍데기만 있는 인터페이스로 new 로 메모리에 로드가 안 된다.  무조건 코드가 없으면 {  } 가 없으면 아예 안 된다.
    //           함수 인자에 던져주면 콜백이 되는 것이다. 인터페이스에 코드를 채워서 던져주면 콜백 메소드가 되는 것.
    //          View.OnClickListener listener =  new View.OnClickListener() 이런 원리와 비슷하다.  이것 러너블과 비교해서

    // 이름으로 공부하면 이름만 바껴도 모르지만 동작 방식을 공부하면 그 흐름을 알기 때문에 금방 알 수 있다.

    class CustomClock extends View {
        Paint paint;

        // 화면에 보인다는 의미는 자원을 사용한다는 의미이군.
        // 뷰에서 getContext 를 하면 view 가 참조하고 있는 컨텍스트를 가져다 쓰는 것이로군.
        // 여전히 왜 필요한지 모르겠군.
        public CustomClock(Context context){
            super(context);
            paint = new Paint();
//            minute.start(); 여기도 안 됨.
        }

        @Override
        public void onDraw(Canvas canvas){
            paint.setStrokeWidth(10);
            paint.setColor(Color.BLUE);
            canvas.drawLine(center_x, center_y, (float)minute.end_x, (float)minute.end_y, paint);
        }
    }

    class DrawClock extends Thread {
        @Override
        public void run() {
            super.run();
            while(true){
                stage.postInvalidate();
            }
        }

    }


    boolean RUN_FLAG = true;
    public void onPause(){
        super.onPause();
        RUN_FLAG = false;
    }
}
