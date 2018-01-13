package com.example.administrator.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 게임 전체를 그려주는 클래스
 * 1. 블럭을 생성하고
 * 2. 프리뷰에 담아주고
 * 3. 보드에 옮져군다
 */
public class Stage extends View {

    Setting setting;
    Board board;
    Preview preview;

    public Stage(Context context, Setting setting) {
        super(context);
        this.setting = setting;
        // 보드와 프리뷰 생성
        board = new Board();
        preview = new Preview(setting.unit, 13, 1, 4, 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        preview.onDraw(canvas);
    }

    // 0. 화면을 최초에 그리기 전에 기본 세팅
    public void init(){
        // 첫 블럭
        addBlockToPreview();
        // 보드로 옮기고
        addBlockToBoardFromPreview();
        // 다시 preview 에 설정
        addBlockToPreview();
    }

    // 1. 블럭을 생성
    public Block newBlock(){
        Property property = new Property();
        property.x = 0;
        property.y = 0;
        property.paint = new Paint();
        property.paint.setColor(Color.CYAN);
        property.unit = setting.unit;
        return new Block(property);
    }

    // 2. 생성한 블럭을 Preview 에 담아주고
    public void addBlockToPreview(){
        Block block = newBlock();
        preview.addBlock(block);
    }

    // 3. Preview 에서 꺼낸 다음
    private Block popBlock() {
        Block block = preview.popBlock();
        return block;
    }

    // 4. Board 로 옮겨준다
    public void addBlockToBoardFromPreview(){
        Block block = popBlock();
        board.addBlock(block);
    }

}
