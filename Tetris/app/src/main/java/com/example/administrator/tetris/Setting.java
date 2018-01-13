package com.example.administrator.tetris;

/**
 * Created by Administrator on 2017-09-29.
 */

public class Setting {

    float width;
    float height;

    int rows;
    int columns;

    float unit;

    public Setting(float width, float height, int rows, int columns) {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        unit = width/rows;
    }
}
