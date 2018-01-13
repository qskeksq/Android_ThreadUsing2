package com.example.administrator.tetris;

import android.graphics.Canvas;

/**
 * Created by Administrator on 2017-09-29.
 */

public class Preview {

    float unit;
    float x;
    float y;
    int rows;
    int columns;

    // 현재 프리뷰에 있는 블럭
    Block block;

    public Preview(float unit, float x, float y, int rows, int columns) {
        this.unit = unit;
        this.x = x;
        this.y = y;
        this.rows = rows;
        this.columns = columns;
    }

    public void addBlock(Block block){
        this.block = block;
    }

    public Block popBlock(){
        return block;
    }

    public void onDraw(Canvas canvas){
        block.onDraw(canvas);
    }

}
