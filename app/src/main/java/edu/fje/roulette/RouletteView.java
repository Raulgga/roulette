package edu.fje.roulette;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class RouletteView extends View {
    private Context context;
    private Paint paint; // Objeto para pintar
    private Bitmap ballBitmap; // Imagen de la bola
    private ValueAnimator animator;
    private float angleBall = 0;

    public RouletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

        // Configurar listener para detectar clics
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinRoulette();
            }
        });
    }

    public void init() {
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ballroulette);
        ballBitmap = scaleBitmap(ballBitmap, 50, 50); // Tamaño de la imagen
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); // Suavizar los bordes de lo que se dibuja
        paint.setTextSize(30); // Reducir el tamaño del texto
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private int getRouletteColor(int number) {
        if (number == 0) {
            return Color.GREEN;
        }

        int[] redNumbers = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for (int red : redNumbers) {
            if (number == red) {
                return Color.RED;
            }
        }
        return Color.BLACK;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 1. Centro y radio de la ruleta
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) - 50; // Radio de la ruleta

        // 2. Dibujar las secciones de la ruleta
        float startAngle = 0;
        float sweepAngle = 360f / 37; // Ángulo para cada número en el arreglo

        for (int i = 0; i < 37; i++) {
            // Colorear las secciones de la ruleta
            paint.setColor(getRouletteColor(i));

            canvas.drawArc(
                    centerX - radius, centerY - radius, // Esquina superior izquierda
                    centerX + radius, centerY + radius, // Esquina inferior derecha
                    startAngle, sweepAngle,             // Ángulo inicial y barrido
                    true, paint);                       // Rellenar la sección

            // Dibujar los números con un borde alrededor
            paint.setColor(Color.WHITE); // Color del texto
            paint.setTextAlign(Paint.Align.CENTER); // Alineación centrada
            paint.setStyle(Paint.Style.FILL); // Relleno sólido para el texto
            float textAngle = startAngle + sweepAngle / 2; // Ángulo del texto
            float textX = centerX + (float) (radius * 0.75 * Math.cos(Math.toRadians(textAngle))); // Colocar más afuera
            float textY = centerY + (float) (radius * 0.75 * Math.sin(Math.toRadians(textAngle))); // Colocar más afuera

            // Añadir un borde negro al texto para mayor visibilidad
            paint.setStrokeWidth(2); // Grosor del borde
            paint.setStyle(Paint.Style.STROKE); // Dibujar borde
            paint.setColor(Color.BLACK);
            canvas.drawText(String.valueOf(i), textX, textY, paint);

            // Dibujar el número real en blanco
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawText(String.valueOf(i), textX, textY, paint);

            // Dibujar las líneas blancas que separan las casillas
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(4); // Grosor de la línea divisoria
            canvas.drawLine(
                    centerX, centerY, // Punto central
                    centerX + (float) (radius * Math.cos(Math.toRadians(startAngle))),
                    centerY + (float) (radius * Math.sin(Math.toRadians(startAngle))),
                    paint); // Línea hacia el borde de la sección

            canvas.drawLine(
                    centerX, centerY, // Punto central
                    centerX + (float) (radius * Math.cos(Math.toRadians(startAngle + sweepAngle))),
                    centerY + (float) (radius * Math.sin(Math.toRadians(startAngle + sweepAngle))),
                    paint); // Línea hacia el borde de la siguiente sección

            startAngle += sweepAngle;
        }

        // 3. Dibujar la bola en su posición
        float ballX = centerX + (float) (radius * 0.8 * Math.cos(Math.toRadians(angleBall)));
        float ballY = centerY + (float) (radius * 0.8 * Math.sin(Math.toRadians(angleBall)));
        canvas.drawBitmap(ballBitmap, ballX - ballBitmap.getWidth() / 2f, ballY - ballBitmap.getHeight() / 2f, paint);
    }

    void spinRoulette() {
        int completeSpin = 5; // Número de giros completos
        float finalAngle = 360 * completeSpin + (float) (Math.random() * 360); // Ángulo final

        animator = ValueAnimator.ofFloat(0, finalAngle);
        animator.setDuration(3000); // Duración de la animación
        animator.setInterpolator(new DecelerateInterpolator()); // Desaceleración

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angleBall = (float) animation.getAnimatedValue(); // Actualiza el ángulo

                invalidate(); // Redibuja la vista
            }
        });
        animator.start();
    }
}