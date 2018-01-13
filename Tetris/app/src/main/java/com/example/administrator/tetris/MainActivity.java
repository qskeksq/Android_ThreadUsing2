package com.example.administrator.tetris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final int ROWS    = 10;  // 가로
    private static final int COLUMNS = 10;  // 세로
    private static Setting setting;         // 세팅값

    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 0. 게임 세팅
        setGame();
        // 1. 그림판을 준비
        initView();



    }

    private void setGame(){
        // 0. 화면의 사이즈를 구해서 게임판에 넘긴다
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float height = metrics.heightPixels;
        float width = metrics.widthPixels;
        setting = new Setting(width, height, ROWS, COLUMNS);
    }

    private void initView(){
        container = (FrameLayout) findViewById(R.id.container);
        // 게임판을 그리는 클래스를 생성하고
        Stage stage = new Stage(this, setting);
        // 뭐가 그릴 것들을 준비한다

        // 화면에 표시한다
        container.addView(stage);
    }
}
