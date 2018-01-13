package com.veryworks.android.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 전체 그려지는 모양은 총 3개이다. 1.board 2.preview 3.block 각각 canvas 에서 그리는 행위는 자신 클래스 안에서 한다(크기를 자신밖에 모르기 때문)
 * 다만 board -> 자기 자신을 그리고, block 이 자신을 그리도록 호출 / preview -> 자기 자신을 그리고, block 이 자신을 그리도록 호출
 *
 * 1. 새로운 블럭 생성                         newBlock()
 * 2. preview 에 생성한 블럭 넣어주기           addBlockToPreview()
 * 3. preview 에서 꺼내서 board 에 넣어주기     addBlockToBoardFromPreview()
 * 4. invalidate() -> onDraw() 에서 preview, board 의 onDraw() 호출 -> block 의 onDraw() 호출
 *    즉, 원본 Stage 의 Canvas 를 두 그리는 영역에 넘겨주고, 그 그리는 영역은 또 block 에게 Canvas 를 넘겨 스스로를 그리도록 한다.
 */
public class Stage extends View {

    Setting setting;

    Board board;
    Preview preview;

    boolean RUNNING = true;

    public Stage(Context context, Setting setting) {
        super(context);

        this.setting = setting;
        // 보드와 프리뷰를 생성한다
        board = new Board(1, 1, 11, 16, setting.unit);
        preview = new Preview(13, 1, 4, 4, setting.unit);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        preview.onDraw(canvas);
        board.onDraw(canvas);
    }

    // 0. 화면을 최초에 그리기 전에 기본세팅
    public void init(){
        addBlockToPreview();
        addBlockToBoardFromPreview();
        addBlockToPreview();
    }

    // 1. 블럭을 생성하는 함수
    public Block newBlock(){
        Property property = new Property();
        property.x = 0;
        property.y = 0;
        property.paint = new Paint();
        property.paint.setColor(Color.RED);
        property.unit = setting.unit;
        return new Block(property);
    }
    // 2. 블럭을 preview 에 담는 함수
    public void addBlockToPreview(){
        Block block = newBlock();
        preview.addBlock(block);
    }
    // 3. 블럭을 preview 에서 Board로 옴기는 함수
    public void addBlockToBoardFromPreview(){
        Block block = preview.popBlock();
        board.addBlock(block);
    }

    // 키 패드 동작
    public void up() {
        board.up();
        invalidate();
    }
    public void down() {
        boolean check = board.down();
        // 다운시 충돌되면 map에 현재 블럭을 삽입하고
        // 새로운 블럭을 Preview에서 가져와서 담는다.
        if(!check) {
            board.addBlockToMap();
            // board 에 있는 map을 한줄씩 전체줄을 체크해서 꽉 차있으면 삭제후
            // 한줄씩 아래로 이동
            board.lineCheckAndRemove();
            addBlockToBoardFromPreview();
            addBlockToPreview();
        }
        postInvalidate();
    }
    public void left() {
        board.left();
        invalidate();
    }
    public void right() {
        board.right();
        invalidate();
    }
    // 1초에 한번씩 board에 있는 블럭을 아래로 이동시킨다.
    public void runBlock() {
        RUNNING = true;
        new Thread(){
            public void run(){
                while(RUNNING) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    down();
                }
            }
        }.start();
    }
    public void stopBlock(){
        RUNNING = false;
    }
}
