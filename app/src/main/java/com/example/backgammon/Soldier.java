package com.example.backgammon;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Soldier extends View{
    private Paint paint;
    private Paint borderPaint;

    private float x;
    private float y;
    private float r;
    private int color;

    public Soldier(Context context, float x, float y, float r, int color,int borderColor) {
        super(context);

        paint = new Paint();
        paint.setColor(color);  // צבע העיגול
        paint.setStyle(Paint.Style.FILL);

        // Initialize the paint objects
        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE); // Use STROKE for a border
        borderPaint.setStrokeWidth(5); // Set the border width

        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override


    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle(x, y, r, paint);
        // Draw the border (stroke)
        canvas.drawCircle(x, y, r, borderPaint);
    }


}
