package edu.fje.roulette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BettingBoard extends View {
    private Paint paint;
    private int width;
    private int height;

    public BettingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        drawRouletteBoard(canvas);
    }

    private void drawRouletteBoard(Canvas canvas) {
        int[] numbersRoulette = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36};

        int xOffset = 50; //espaciado horizontal
        int yOffset = 100; // espaciado vertical
        int rowCount = 3; //numero de columnas en la fila
        int numerRows = 12; //numero de filas sin contar 0

        //dibujar numero 0
        paint.setColor(Color.GREEN);
        int x0 = xOffset + 1 * 100; //posicion horizontal del 0
        int y0 = yOffset; //coloca el 0 en la parte superior
        canvas.drawRect(x0, y0, x0 + 80, y0 + 80, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        float textWidth0 = paint.measureText("0");
        canvas.drawText("0", x0 + (80 - textWidth0) / 2, y0 + 60, paint);

        // dibujar los demás números de la ruleta
        for (int i = 1; i < numbersRoulette.length; i++){
            int number = numbersRoulette[i];
            int x = xOffset + (i % rowCount) * 100;// Ajusta el espaciado horizontal
            int y = yOffset + ((i - 1) / rowCount) * 100 + 80; // Ajusta el espaciado vertical después del 0

            //Asignar color segun el numero
            if (number == 0){
                continue; //0 Dibujado
            } else if (number % 2 == 0){
                paint.setColor(Color.BLACK); //Numeros pares negros
            } else {
                paint.setColor(Color.RED); //Numeros impares rojos
            }
            // Dibuja el rectángulo para el número
            canvas.drawRect(x, y, x + 80, y + 80, paint);

            // Dibuja el número
            paint.setColor(Color.WHITE);
            float textWidth = paint .measureText(String.valueOf(number));
            canvas.drawText(String.valueOf(number), x + (80 - textWidth) / 2, y + 60, paint);

        }
        // Dibuja las apuestas
        drawSpecialBets(canvas);
    }
    private void drawSpecialBets(Canvas canvas){
        // Dibuja area de apuestas especiales
        //Rojo/Negro
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(50, height - 200, 200, height - 100, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Rojo/Negro", 60, height - 150, paint);

        // Par/Impar
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(250, height - 200, 400, height - 100, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Par/Impar", 260, height - 150, paint);

        // Bajo/Alto
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(450, height - 200, 600, height - 100, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Bajo/Alto", 460, height - 150, paint);

        // Docenas
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(650, height - 200, 800, height - 100, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Docenas", 660, height - 150, paint);
    }
}