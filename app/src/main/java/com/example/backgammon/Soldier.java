package com.example.backgammon;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Soldier extends View{
    private Paint paint;
    private float x;
    private float y;
    private float r;
    private int color;

    public Soldier(Context context, float x, float y, float r, int color) {
        super(context);

        paint = new Paint();
        paint.setColor(color);  // צבע העיגול
        paint.setStyle(Paint.Style.FILL);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override


    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle(x, y, r, paint);
    }


}
