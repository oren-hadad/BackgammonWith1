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

    public Soldier(Context context, float x, float y, float r) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.BLUE);  // צבע העיגול
        paint.setStyle(Paint.Style.FILL);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, r, paint);
    }


    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle(x, y, r, paint);
    }

}
