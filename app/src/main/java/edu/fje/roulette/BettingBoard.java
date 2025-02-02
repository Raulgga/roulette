package edu.fje.roulette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BettingBoard extends View {
    private Paint paint;
    int[][] numbersRoulette = {
            {3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36},
            {2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35},
            {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34}
    };

    public BettingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(30);
        paint.setColor(Color.BLACK);
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        float cellWidth = getWidth() / 3f;
        float cellHeight = getHeight() / 12f;

        //Dibujar cuadricula
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        for (int i = 0; i <= 12; i++) {
            canvas.drawLine(0, i * cellHeight, getWidth(), i * cellHeight, paint);
        }
        for (int j = 0; j <= 3; j++) {
            canvas.drawLine(j * cellWidth, 0, j * cellWidth, getHeight(), paint);
        }

        //Dibujar números en el tablero
        paint.setStyle(Paint.Style.FILL);
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 12; row++) {
                float x = col * cellWidth + cellWidth / 2 - paint.measureText(String.valueOf(numbersRoulette[col][row])) / 2;
                float y = row * cellHeight + cellHeight / 2 + paint.getTextSize() / 2;
                canvas.drawText(String.valueOf(numbersRoulette[col][row]), x, y, paint);
            }
        }

        //Dibujar 0 en la parte superior
        float zeroX = getWidth() / 2 - paint.measureText("0") / 2;
        float zeroY = -cellHeight / 2 + paint.getTextSize();
        canvas.drawText("0", zeroX, zeroY, paint);
    }
}
