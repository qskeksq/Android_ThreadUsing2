package com.example.administrator.tetris;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

/**
 * Created by Administrator on 2017-09-29.
 */

public class Block {

    /**
     * 객체지향 설계
     * 속성에 대한 설계 변경 여지가 있는 경우 따로 속성 클래스를 빼낸다.
     */

    Property p;
    int number;
    int rotation;
    int current[][];

    int blocks[][][][] = {
            { // ㅗ
                    {
                            {0, 0, 0},
                            {0, 1, 0},
                            {1, 1, 1}
                    },
                    {
                            {1, 0, 0},
                            {1, 1, 0},
                            {1, 0, 0}
                    },
                    {
                            {0, 1, 0},
                            {1, 1, 0},
                            {0, 0, 0}
                    },
                    {
                            {0, 1, 0},
                            {1, 1, 0},
                            {0, 1, 0}
                    },
            },
            { // ㅡ
                    {
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                    },
                    {
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0},
                            {0, 1, 0, 0}
                    },
                    {
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                    },
                    {
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                    },
            },

    };


    public Block(Property property) {
        this.p = property;
        Random random = new Random();
        number = random.nextInt(2);
        rotation = 0;
        Log.d("Block", "Number="+number+", Rotation="+rotation);
    }

    public void onDraw(Canvas canvas){
        // 블럭 종류와 회전값으로 현재 블럭을 조회해온다
        current = blocks[number][rotation];
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[0].length; j++) {
                if(current[i][j] > 0){
                    // 그린다
                }
            }
        }
        canvas.drawRect(p.x, p.y, p.x+p.unit, p.y+p.unit, p.paint);
    }


    public void up(){
        p.y = p.y + p.unit;
    }

    public void down(){
        p.y = p.y - p.unit;
    }

    public void left(){
        p.x = p.x + p.unit;
    }

    public void right(){
        p.x = p.x + p.unit;
    }

    public void addBlock(Block block) {
    }
}
