package edu.fje.roulette;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Chip {
    float x, y;
    private final int color;
    private final int value;
    private int betNumber = 0;
    private int specialBet = -1;
    private final float initialX, initialY;

    public Chip(float x, float y, int color, int value) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.value = value;
        this.betNumber = betNumber;
        this.specialBet = specialBet;
        this.initialX = x;
        this.initialY = y;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, 30, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        String text = String.valueOf(value);
        canvas.drawText(text, x - 15, y + 10, paint);
    }

    public boolean isTouched(float touchX, float touchY) {
        float radius = 50;
        float dist = (float) Math.sqrt(Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2));
        return dist <= 30;
    }

    public void resetPosition() {
        this.x = initialX;
        this.y = initialY;
    }

    public int getBetNumber(){
        return betNumber;
    }

    public int getValue(){
        return value;
    }

    public int getSpecialBet(){
        return specialBet;
    }

    public void setSpecialBet(int specialBet){
        this.specialBet = specialBet;
    }
}