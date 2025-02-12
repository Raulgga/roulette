package edu.fje.roulette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

public class BettingBoard extends View {
    private Paint paint;
    private int cellWidth;
    private int cellHeight;


    public BettingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawColumn(Canvas canvas, int startX, int startY, int colSpan, int rowSpan, String x){

        for (int i = 0; i < rowSpan; i++) {
            int left = startX;
            int top = startY + (i * cellHeight);
            int right = left + colSpan * cellWidth;
            int bottom = top + cellHeight;

            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, paint);

            //dibujar texto en celdas
            paint.setStyle(Paint.Style.FILL);
            String cellText = "x"; //uso de texto especifico
            float textX = left + (colSpan * cellWidth) / 4;
            float textY = top + (cellHeight / 2);
            canvas.drawText(cellText, textX,textY, paint);
            paint.setStyle(Paint.Style.STROKE);
        }
    }
    @Override
    protected  void drawCuadrado(Canvas canvas, int cordX, int cordY, int colSpan, int rowSpan) {
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paint);

        //dibujar texto en celdas
        paint.setStyle(Paint.Style.FILL);
        String cellText = "x"; //uso de texto especifico
        float textX = left + (colSpan * cellWidth) / 4;
        float textY = top + (cellHeight / 2);
        canvas.drawText(cellText, textX,textY, paint);
        paint.setStyle(Paint.Style.STROKE);
    }
    @Override
    protected void onDrawCol1(Canvas canvas){

    }
    @Override
    protected void onDrawCol2(Canvas canvas){

    }
    @Override
    protected void onDrawCol3(Canvas canvas){
        int HorizontalPoint = 0;
        int VerticalPoint = 0;

        for (int i = 0; i <=37; i++){
            if (i % 3 == 0){
                HorizontalPoint = 0;
                VerticalPoint += 75;
            }else{
                HorizontalPoint += 150;
            }
            draw(VerticalPoint, HorizontalPoint, i);

        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        //Definir ancho y alto de celdas
        cellWidth = width / 7; // 7 columnas totales
        cellHeight = height / 14;// 14 filas totales

        // Columna 1: 6 filas
        String[] column1Texts = {"1 to 18", "Even", "Black", "Red", "Odd", "19 to 36"};
        drawColumn(canvas, 0, 0, 1, 6, Arrays.toString(column1Texts));

        // Columna 2: 3 filas
        String column2Texts = Arrays.toString(new String[]{ "1st 12", "2nd 12", "3rd 12" });
        drawColumn(canvas, cellWidth, 0, 1, 3, column2Texts);

        // Columna 3: 13 filas
        String column3Texts = Arrays.toString(new String[]{ "1", "4", "7", "10", "13", "16", "19", "22", "26", "29", "32", "35", "2to1" });
        drawColumn(canvas, 2 * cellWidth, 0, 1, 13, column3Texts);

        // Columna 4: 13 filas
        String column4Texts = Arrays.toString(new String[]{ "2", "5", "8", "11", "14", "17", "20", "23", "27", "30", "33", "36", "2to1" });
        drawColumn(canvas, 3 * cellWidth, 0, 1, 13, column4Texts);

        // Columna 5: 13 filas
        String column5Texts = Arrays.toString(new String[]{ "3", "6", "9", "12", "15", "18", "21", "24", "28", "31", "34", "37", "2to1" });
        drawColumn(canvas, 4 * cellWidth, 0, 1, 13, column5Texts);
    }
}