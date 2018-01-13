package com.veryworks.android.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;


/**
 * 1. 자신의 크기, 모양, 방향 -  Block(Property property)
 * 2. 자기 자신이 그려질 모양 -  int blocks[][][][]
 * 3. 자신이 그려질 부모 좌표 -  setParent(Parent parent)
 * 4. 자기 자신 그리기       -  onDraw(Canvas canvas)
 */
public class Block {
    float x,y, unit;
    Paint paint;
    Parent parent = null;
    int number = 0;
    int rotation = 0;
    int current[][];

    int colors[] ={
      Color.RED, Color.BLACK, Color.GREEN
            , Color.MAGENTA, Color.CYAN, Color.BLUE
            , Color.GRAY
    };

    int blocks[][][][] =
        {
            {
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
                }
            },
            {
                {
                        {0, 2, 0, 0},
                        {2, 2, 2, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 2, 0, 0},
                        {0, 2, 2, 0},
                        {0, 2, 0, 0},
                        {0, 0, 0, 0},
                },
                {
                        {0, 0, 0, 0},
                        {2, 2, 2, 0},
                        {0, 2, 0, 0},
                        {0, 0, 0, 0},
                },
                {
                        {0, 2, 0, 0},
                        {2, 2, 0, 0},
                        {0, 2, 0, 0},
                        {0, 0, 0, 0},
                }
            },
            {
                {
                        {0, 3, 3, 0},
                        {0, 3, 3, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                }
            },
            {
                {
                        {0, 4, 0, 0},
                        {4, 4, 0, 0},
                        {4, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {4, 4, 0, 0},
                        {0, 4, 4, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                }
            },
            {
                {
                        {5, 0, 0, 0},
                        {5, 5, 0, 0},
                        {0, 5, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 5, 5, 0},
                        {5, 5, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0},
                }
            },
            {
                {
                        {0, 6, 0, 0},
                        {0, 6, 6, 6},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 6, 6, 0},
                        {0, 6, 0, 0},
                        {0, 6, 0, 0},
                        {0, 0, 0, 0},
                },
                {
                        {0, 6, 6, 6},
                        {0, 0, 0, 6},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 0, 6, 0},
                        {0, 0, 6, 0},
                        {0, 6, 6, 0},
                        {0, 0, 0, 0},
                }
            },
            {
                {
                        {0, 0, 7, 0},
                        {7, 7, 7, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 7, 0, 0},
                        {0, 7, 0, 0},
                        {0, 7, 7, 0},
                        {0, 0, 0, 0},
                },
                {
                        {7, 7, 7, 0},
                        {7, 0, 0, 0},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                },
                {
                        {0, 7, 7, 0},
                        {0, 0, 7, 0},
                        {0, 0, 7, 0},
                        {0, 0, 0, 0},
                }
            }
        };

    public Block(Property property){
        x= property.x;
        y= property.y;
        unit= property.unit;
        paint= property.paint;

        Random random = new Random();
        number = random.nextInt(7);
        rotation = 0;
        Log.d("Block","Number="+number+", Rotation="+rotation);
    }

    // 나를 그리는 함수
    public void onDraw(Canvas canvas){
        // 블럭 종류와 회전값으로 현재 블럭을 조회해온다
        current = blocks[number][rotation];
        paint.setColor(colors[number]);
        //                  y        x
        // 2차원 배열로 만들어진 블럭을 반복문을 돌면서
        for(int v=0 ; v<current.length ; v++){
            for(int h=0 ; h<current[0].length ; h++){
                // 배열의 값이 0보다 큰 곳만 그려준다.
                if(current[v][h] > 0) {
                    canvas.drawRect(
                            parent.getX() + ((x + h) * unit),
                            parent.getY() + ((y + v) * unit),
                            parent.getX() + ((x + h + 1) * unit),
                            parent.getY() + ((y + v + 1) * unit),
                            paint);
                }
            }
        }
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void rotate() {
        rotation = (rotation + 1) % blocks[number].length;
    }

    public int[][] getNext() {
        int nextRotation = (rotation + 1) % blocks[number].length;
        int next[][] = blocks[number][nextRotation];
        return next;
    }

    public interface Parent {
        float getX();
        float getY();
    }
}
