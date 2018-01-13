package com.veryworks.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 0. 자신이 그려질 데이터 설정 float x, y / int columns, rows / float unit
 * 1. 자기 자신 그리기 - onDraw
 * 2. block 이 Board 자신 안에서 그리도록 자신의 크기 설정 - addBlock(Block block)
 * 3. block 이 block 자신을 그리도록 block.onDraw(canvas) 호출
 */

public class Board implements Block.Parent{

    // 크기단위
    float unit;
    // 좌표
    float x;
    float y;
    // 가로 세로 사이즈
    int columns;  // pixel = columns * unit
    int rows;     // pixel = rows * unit

    // 현재 보드에서 동작하는 블럭
    Block block;

    // 페인트
    Paint paint;

    int colors[] ={
            Color.RED, Color.BLACK, Color.GREEN
            , Color.MAGENTA, Color.CYAN, Color.BLUE
            , Color.GRAY , 0
            , Color.DKGRAY
    };

    int map[][] =
    {
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,0,0,0,0,0,0,0,0,0,9},
            {9,9,9,9,9,9,9,9,9,9,9},
    };

    public Board(float x, float y, int columns, int rows, float unit){
        this.unit = unit;
        this.x = x;
        this.y = y;
        this.columns = columns;
        this.rows = rows;

        this.paint = new Paint();
        paint.setColor(Color.rgb(100,100,255));
    }

    public void addBlock(Block block) {
        this.block = block;
        block.x = 4;
        block.setParent(this);
    }

    public void onDraw(Canvas canvas){
        // 자기 자신 그리기
        for(int v=0; v<map.length ; v++){
            for(int h=0 ; h<map[0].length ; h++){
                int cellValue = map[v][h];
                if(cellValue > 0){
                    paint.setColor(colors[cellValue-1]);
                    canvas.drawRect(
                        ((x + h) * unit),
                        ((y + v) * unit),
                        ((x + h + 1) * unit),
                        ((y + v + 1) * unit),
                        paint);
                }
            }
        }
        // 가지고 있는 유닛 그리기
        block.onDraw(canvas);
    }

    @Override
    public float getX() {
        return x * unit;
    }

    @Override
    public float getY() {
        return y * unit;
    }

    public void up() {
        if(checkCollision("r",0)) {
            block.rotate();
        }
    }
    // 다운키를 눌렀는데 충돌이면
    // 현재 블럭을 map으로 이전하고
    // 새로운 블럭을 가져와야 된다.
    public boolean down() {
        if(checkCollision("y", 1) ) {
            block.y += 1;
            return true;
        }
        return false;
    }
    // 현재 블럭의 셀 값을 추출해서
    // map 에 입력해준다.
    public void addBlockToMap(){
        for(int v=0 ; v<4 ; v++){
            for(int h=0 ; h<4 ; h++){
                int blockCell = block.current[v][h];  // 블럭의 셀 값
                if(blockCell > 0){
                    map[(int)block.y +v][(int)block.x + h] = blockCell;
                }
            }
        }
    }
    public void left() {
        if(checkCollision("x", -1) ) {
            block.x -= 1;
        }
    }
    public void right() {
        if(checkCollision("x", 1) ) {
            block.x += 1;
        }
    }

    // 블럭 충돌검사
    private boolean checkCollision(String command, int value){
        boolean result = true;

        // 현재 map 배열과
        // 안에 들어있는 블럭의 배열을 비교
        int checkBlock[][] = block.current;
        if("r".equals(command)){
            // 회전 에서는 현재 블럭이 아닌 다음 블럭을 가져와야 하고
            // 충돌시에는 실제 회전이 일어나면 안되기 때문에
            // 임시로 checkBlock에 저장해서 사용한다.
            checkBlock = block.getNext();
        }

        for(int v=0 ; v<4 ; v++){
            for(int h=0 ; h<4 ; h++){
                int blockCell = checkBlock[v][h];  // 블럭의 셀 값

                int tempX = (int)block.x + h;
                if("x".equals(command)) tempX += value;
                int tempY = (int)block.y + v;
                if("y".equals(command)) tempY += value;

                // 체크하고자 하는 범위가 map의 크기를 넘어가면 continue
                if(tempY >= map.length || tempX >= map[0].length
                        || tempY < 0 || tempX < 0){
                    continue;
                }

                int mapCell   = map[ tempY][ tempX]; // map의 셀 값
                if(blockCell > 0 && mapCell >0){
                    return false;
                }
            }
        }
        return result;
    }
    // map 을 가로축으로 검사해서 셀이 꽉차 있으면 해당 줄을 지우고
    // 지워진 줄만큼 아래로 이동시킨다.
    public void lineCheckAndRemove() {

    }
}
