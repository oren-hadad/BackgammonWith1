package com.example.backgammon;

import static com.example.backgammon.AppConsts.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class BoardView extends View {

    private GameManager gameManager;
    private Board board;

    public BoardView(Context context) {
        super(context);

        board = new Board();
        gameManager = new GameManager(this, context,board);
    }

    private float[] positionArrayX = new float[24];
    private float[] positionArrayY = new float[24];
    private float Canvas_size_Y = 0;

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        createPositionArrayY(canvas);
        createPositionArrayX(canvas);
        drawBackground(canvas);
        drawWhite(canvas);
        drawSoldiers(canvas);
        drawHighlight(canvas);

        
    }

    private void drawHighlight(Canvas canvas) {
        int[] slots= board.getHighlightedSlot();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] ==1) {
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

    }




    public void createPositionArrayY(Canvas canvas) {
        float heightCanvas = canvas.getHeight();
        float shoreY = heightCanvas / 10;
        float deltay = heightCanvas / 11;
        for (int i = 0; i < positionArrayY.length / 2; i++) {
            positionArrayY[i] = shoreY;
            positionArrayY[i + 12] = canvas.getHeight() - shoreY;
        }
        radius = heightCanvas / 25;
        Canvas_size_Y = heightCanvas/2;
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
        for (int i = 0; i < board.getLocWhite().length; i++) {
            createSoldier(canvas, i, Color.WHITE, Color.BLACK, board.getLocWhite()[23 - i]);
        }
        for (int i = 0; i < board.getLocBlack().length; i++) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int soldierIndex = getSoldierTouch(event);

            // if first click - pass to game manager and get possible options
            gameManager.sourceSelected(soldierIndex);

            // two consecutive clicks - if first click  and second click are the same
            // this means it isthe destination column




            if (soldierIndex != -1) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private int getSoldierTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        for (int i = 0; i < 12; i++) {
            if (x > positionArrayX[i] - radius && x < positionArrayX[i] + radius && Canvas_size_Y / 2 > y ) {
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

}