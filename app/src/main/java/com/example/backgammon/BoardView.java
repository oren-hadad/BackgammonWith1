package com.example.backgammon;

import static com.example.backgammon.AppConsts.*;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class BoardView extends View {

    GameManager gameManager;
    Board board;
    public BoardView(Context context) {
        super(context);

        board = new Board();
        gameManager = new GameManager(this, context,board);


    }


    // here ew draw the board
    // background - image

    private float[] positionArrayX = new float[24];
    private float[] positionArrayY = new float[24];
    private float deltaY;
  //  private float radius;

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        createPositionArrayY(canvas); // עשינו
        createPositionArrayX(canvas);
        drawBackground(canvas);
        drawWhite(canvas);
        // draw the soldioes
        //createSoldier(canvas,3,-1,5 );


        drawSoldiers(canvas);

    }



    public void createPositionArrayX(Canvas canvas) {

        float shoreX = canvas.getWidth()/13;
        float deltax = canvas.getWidth()/10 + canvas.getWidth()/22-shoreX;
        float centralShoreX = canvas.getWidth()/8 + canvas.getWidth()/25;
        for (int i = 0; i< positionArrayX.length/2; i++){
            if (i< 6){
                positionArrayX[11-i] = shoreX + i*deltax;
                positionArrayX[23-i] = shoreX + i*deltax;
            }
            else {
                positionArrayX[11-i] = centralShoreX + i*deltax;
                positionArrayX[23-i] = centralShoreX + i*deltax;
            }
        }
    }
    public void createPositionArrayY(Canvas canvas){
        float shoreY =canvas.getHeight()/10;
        float deltay = canvas.getHeight()/11;
        positionArrayY[0] = shoreY;
        positionArrayY[1] = canvas.getHeight()/10;
        for (int i = 0; i< positionArrayY.length/2; i++){
            positionArrayY[i] = shoreY;
            positionArrayY[i+12] = canvas.getHeight() - shoreY;
        }
        radius = canvas.getHeight()/25;
    }


    private void drawWhite(Canvas canvas) {
        // map the location white, eaten white to  visual locations

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);  // צבע העיגול
        paint.setStyle(Paint.Style.FILL);
       // this.x = x;
      //  this.y = y;





    }

    private void drawBackground(Canvas canvas) {
        // background
        Drawable d = getResources().getDrawable(R.drawable.back, null);

        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());


        d.draw(canvas);
    }





    private void drawSoldiers(Canvas canvas) {
        // map the location white, eaten white to  visual locations
        // map the location black, eaten black to  visual locations
        for(int i = 0; i<board.getLocWhite().length; i++){
            createSoldier(canvas,i,  Color.WHITE,Color.BLACK,board.getLocWhite()[i]);
        }
        for(int i = 0; i<board.getLocBlack().length; i++){
            createSoldier(canvas,i,  Color.BLACK,Color.WHITE,board.getLocBlack()[i]);
        }

    }

    public  void createSoldier(Canvas canvas, int index, int color,int borderColor,int count){
        float j = 0;
        for (int i = 0; i <count; i++){
            if(i< MAX_SOLDIERS_IN_COL) {
                Soldier soldier = new Soldier(getContext(), positionArrayX[index], positionArrayY[index] + i * radius * DISTANCE_BETWEEN_SOLDIERS, radius, color,borderColor);
                soldier.draw(canvas);
            }
            else{
                Soldier soldier = new Soldier(getContext(), positionArrayX[index] + 20, positionArrayY[index] + j * radius * DISTANCE_BETWEEN_SOLDIERS, radius, color,borderColor);
                soldier.draw(canvas);
                j++;
            }

        }
    }

    public  float[] getPositionArrayX() {
        return positionArrayX;
    }
    public  float[] getPositionArrayY() {
        return positionArrayY;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
