package com.example.backgammon;

import static com.example.backgammon.AppConsts.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class BoardView extends View

{

    private GameManager gameManager;
    private Board board;
    public BoardView(Context context, FrameLayout gameContainer) {
        super(context);

        board = new Board();
        gameManager = new GameManager(this, context,board, gameContainer);
    }

    private float[] positionArrayX = new float[24];
    private float[] positionArrayY = new float[24];
    private float Canvas_size_Y = 0;
    private int clickCounter = 0;
    private float eatenWhiteX, eatenWhiteY;
    private float eatenBlackX, eatenBlackY;
    private float exitedWhiteX, exitedWhiteY;
    private float exitedBlackX, exitedBlackY;



    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        createPositionArrayY(canvas);
        createPositionArrayX(canvas);
        drawBackground(canvas);
        drawWhite(canvas);
        drawSoldiers(canvas);
        drawEatenSoldiers(canvas);
        drawExitedSoldiers(canvas); // הוספת קריאה לציור החיילים שיצאו
        drawHighlight(canvas);

    }
    private void drawHighlight(Canvas canvas) {
        int[] slots = board.getHighlightedSlot();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == 1) {
                highlightSlot(canvas, i);
            }

        }
    }
    public void createPositionArrayX(Canvas canvas) {
        float shoreX = canvas.getWidth() / 13;
        float deltax = canvas.getWidth() / 10 + canvas.getWidth() / 22 - shoreX;
        float centralShoreX = canvas.getWidth() / 8 + canvas.getWidth() / 25;
        for (int i = 0; i < positionArrayX.length / 2; i++) {
            if (i < 6) {
                positionArrayX[11 - i] = shoreX + i * deltax;
                positionArrayX[12 + i] = shoreX + i * deltax;
            } else {
                positionArrayX[11 - i] = centralShoreX + i * deltax;
                positionArrayX[12 + i] = centralShoreX + i * deltax;
            }
        }
        eatenWhiteX = canvas.getWidth() /2;
        eatenBlackX = canvas.getWidth()/2 ;

        // הגדרת המיקומים של החיילים שיצאו - בקצוות הלוח
        exitedWhiteX = canvas.getWidth() - canvas.getWidth() / 50; // קצה ימין
        exitedBlackX = canvas.getWidth() / 50; // קצה שמאל

    }

    public void createPositionArrayY(Canvas canvas) {
        float shoreY = canvas.getHeight() / 10;
        float deltay = canvas.getHeight() / 11;
        eatenWhiteY = canvas.getHeight() / 2 + canvas.getHeight() / 8;
        eatenBlackY = canvas.getHeight() / 2 - canvas.getHeight() / 8;
        positionArrayY[0] = shoreY;
        positionArrayY[1] = canvas.getHeight() / 10;
        for (int i = 0; i < positionArrayY.length / 2; i++) {
            positionArrayY[i] = shoreY;
            positionArrayY[i + 12] = canvas.getHeight() - shoreY;
        }
        radius = canvas.getHeight() / 25;
        Canvas_size_Y = canvas.getHeight();

        // הגדרת מיקום Y עבור החיילים שיצאו - באמצע הגובה
        exitedWhiteY = canvas.getHeight() / 4;
        exitedBlackY = canvas.getHeight()- canvas.getHeight() / 4;
    }

    private void drawWhite(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawBackground(Canvas canvas) {
        Drawable d = getResources().getDrawable(R.drawable.back, null);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);
    }

    private void drawSoldiers(Canvas canvas) {
        for (int i = board.getLocWhite().length -1; i >= 0; i--) {
            createSoldier(canvas, i, Color.WHITE, Color.BLACK, board.getLocWhite()[i]);
            createSoldier(canvas, i, Color.BLACK, Color.WHITE, board.getLocBlack()[i]);
        }
    }

    public void createSoldier(Canvas canvas, int index, int color, int borderColor, int count) {
        float j = 0;
        for (int i = 0; i < count; i++) {
            if (index < 12) {
                if (i < MAX_SOLDIERS_IN_COL) {
                    Soldier soldier = new Soldier(getContext(), positionArrayX[index], positionArrayY[index] + i * radius * DISTANCE_BETWEEN_SOLDIERS, radius, color, borderColor);
                    soldier.draw(canvas);
                } else {
                    Soldier soldier = new Soldier(getContext(), positionArrayX[index] + 20, positionArrayY[index] + j * radius * DISTANCE_BETWEEN_SOLDIERS, radius, color, borderColor);
                    soldier.draw(canvas);
                    j++;
                }
            } else {
                if (i < MAX_SOLDIERS_IN_COL) {
                    Soldier soldier = new Soldier(getContext(), positionArrayX[index], positionArrayY[index] - i * radius * DISTANCE_BETWEEN_SOLDIERS, radius, color, borderColor);
                    soldier.draw(canvas);
                } else {
                    Soldier soldier = new Soldier(getContext(), positionArrayX[index] + 20, positionArrayY[index] - j * radius * DISTANCE_BETWEEN_SOLDIERS, radius, color, borderColor);
                    soldier.draw(canvas);
                    j++;
                }
            }
        }
    }

    public float[] getPositionArrayX() {
        return positionArrayX;
    }

    public float[] getPositionArrayY() {
        return positionArrayY;
    }




    // first click choose source
    // second click choose destination
    // update the board -  also the game manager moveCounter and highlight the new possible moves
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickCounter++;

            if(clickCounter%2==1) {
                int soldierIndex = getSoldierTouch(event);

                // if first click - pass to game manager and get possible options
                gameManager.sourceSelected(soldierIndex);

                // two consecutive clicks - if first click  and second click are the same
                // this means it isthe destination column


                if (soldierIndex != -1) {
                    return true;
                }
            }
            else // this means move
            // check if the destination is legal = higlighted in the game manager
            {
                // call game manager and give index of destination
                int soldierIndex = getSoldierTouch(event);
                gameManager.destinationSelected(soldierIndex);



            }
        }
        return super.onTouchEvent(event);
    }


    private int getSoldierTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        for (int i = 0; i < 12; i++) {
            if (x > positionArrayX[i] - radius && x < positionArrayX[i] + radius && Canvas_size_Y / 2 > y) {
                return i;
            } else if (x > positionArrayX[i] - radius && x < positionArrayX[i] + radius) {
                return 23 - i;
            }
        }
        return -1; // Return -1 if no soldier is clicked
    }
    public void highlightSlot(Canvas canvas, int slotIndex) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN); // Light blue color for highlighting
        paint.setStyle(Paint.Style.FILL);
//
        float x = positionArrayX[slotIndex];
        float y = 0;
        if (slotIndex > 11) {
            y = canvas.getHeight() - canvas.getHeight() / 25;
        } else {
            y = canvas.getHeight() / 25;
        }


        float rectWidth = radius * 1.5f;
        float rectHeight = radius * 0.3f;

        canvas.drawRect(x - rectWidth / 2, y, x + rectWidth / 2, y + rectHeight, paint); // Draw a small rectangle
    }
    // הוסף פונקציה חדשה ב-BoardView
    private void drawEatenSoldiers(Canvas canvas) {
        // צייר חיילים לבנים אכולים - בטור יורד (כיוון חיובי של Y)
        for (int i = 0; i < board.getEatenWhite(); i++) {
            Soldier soldier = new Soldier(getContext(), eatenWhiteX, eatenWhiteY + i * radius , radius * 0.8f, Color.WHITE, Color.RED);
            soldier.draw(canvas);
        }

        // צייר חיילים שחורים אכולים - בטור עולה (כיוון שלילי של Y)
        for (int i = 0; i < board.getEatenBlack(); i++) {
            Soldier soldier = new Soldier(getContext(), eatenBlackX, eatenBlackY - i * radius, radius * 0.8f, Color.BLACK, Color.RED);
            soldier.draw(canvas);
        }
    }

    // פונקציה חדשה לציור החיילים שיצאו מהמשחק
    private void drawExitedSoldiers(Canvas canvas) {
        // צייר חיילים לבנים שיצאו - בטור יורד (בקצה ימין של הלוח)
        for (int i = 0; i < board.getExitedWhite(); i++) {
            Soldier soldier = new Soldier(getContext(), exitedWhiteX, exitedWhiteY + i * radius +0.9f , radius * 0.7f, Color.WHITE, Color.GREEN);
            soldier.draw(canvas);
        }

        // צייר חיילים שחורים שיצאו - בטור עולה (בקצה שמאל של הלוח)
        for (int i = 0; i < board.getExitedBlack(); i++) {
            Soldier soldier = new Soldier(getContext(), exitedBlackX, exitedBlackY - i * radius *1f, radius * 0.7f, Color.BLACK, Color.GREEN);
            soldier.draw(canvas);
        }
    }

}