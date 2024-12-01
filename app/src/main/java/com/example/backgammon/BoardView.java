package com.example.backgammon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;

public class BoardView extends View {

    public BoardView(Context context) {
        super(context);


    }


    // here ew draw the board
    // background - image

    private float[] positionArray = new float[24];

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        createPositionArray(canvas);

        drawBackground(canvas);


        drawWhite(canvas);






    }

    private void createPositionArray(Canvas canvas) {
        for(int i =0;i<positionArray.length/2;i++)
        {
            if(i<6)
                positionArray[i] = canvas.getWidth()/13 + i*canvas.getWidth()/15;
            else if (i>6 && i<12)
                positionArray[i] = canvas.getWidth()/10 + i*canvas.getWidth()/15;
            positionArray[i+12] = canvas.getWidth()/14 + i*canvas.getWidth()/10;


        }

    }

    private void drawWhite(Canvas canvas) {
        // map the location white, eaten white to  visual locations

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);  // צבע העיגול
        paint.setStyle(Paint.Style.FILL);
       // this.x = x;
      //  this.y = y;
        Soldier  soldier1 = new Soldier(getContext(),positionArray[6],100,40);
        soldier1.draw(canvas);
        for (int i = 0; i< positionArray.length/4; i++){
            Soldier  soldier = new Soldier(getContext(),positionArray[i],100,40);


            soldier.draw(canvas);

        }

        //canvas.drawCircle(positionArray[0], 50, 40, paint);



    }

    private void drawBackground(Canvas canvas) {
        // background
        Drawable d = getResources().getDrawable(R.drawable.back, null);

        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());


        d.draw(canvas);
    }


}
