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
import androidx.core.content.ContextCompat;

public class RouletteView extends View {
    private Context context;
    private Paint paint; //Objeto para pintar
    private Bitmap ballBitmap; //Imagen de la bola
    private int [] colorRoulette; //Array de colores
    private ValueAnimator animator;
    private float angleBall = 0;

    public RouletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

        //configurar listener para detectar clics
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinRoulette();
            }
        });
    }

    public void init() {

        colorRoulette = new int[]{
                ContextCompat.getColor(context, R.color.color_0),
                ContextCompat.getColor(context, R.color.color_1),
                ContextCompat.getColor(context, R.color.color_2),
                ContextCompat.getColor(context, R.color.color_3),
                ContextCompat.getColor(context, R.color.color_4),
                ContextCompat.getColor(context, R.color.color_5),
                ContextCompat.getColor(context, R.color.color_6),
                ContextCompat.getColor(context, R.color.color_7),
                ContextCompat.getColor(context, R.color.color_8),
                ContextCompat.getColor(context, R.color.color_9),
                ContextCompat.getColor(context, R.color.color_10),
                ContextCompat.getColor(context, R.color.color_11),
                ContextCompat.getColor(context, R.color.color_12),
                ContextCompat.getColor(context, R.color.color_13),
                ContextCompat.getColor(context, R.color.color_14),
                ContextCompat.getColor(context, R.color.color_15),
                ContextCompat.getColor(context, R.color.color_16),
                ContextCompat.getColor(context, R.color.color_17),
                ContextCompat.getColor(context, R.color.color_18),
                ContextCompat.getColor(context, R.color.color_19),
                ContextCompat.getColor(context, R.color.color_20),
                ContextCompat.getColor(context, R.color.color_21),
                ContextCompat.getColor(context, R.color.color_22),
                ContextCompat.getColor(context, R.color.color_23),
                ContextCompat.getColor(context, R.color.color_24),
                ContextCompat.getColor(context, R.color.color_25),
                ContextCompat.getColor(context, R.color.color_26),
                ContextCompat.getColor(context, R.color.color_27),
                ContextCompat.getColor(context, R.color.color_28),
                ContextCompat.getColor(context, R.color.color_29),
                ContextCompat.getColor(context, R.color.color_30),
                ContextCompat.getColor(context, R.color.color_31),
                ContextCompat.getColor(context, R.color.color_32),
                ContextCompat.getColor(context, R.color.color_33),
                ContextCompat.getColor(context, R.color.color_34),
                ContextCompat.getColor(context, R.color.color_35),
                ContextCompat.getColor(context, R.color.color_36)
        };

        int[] numbersRoulette = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 25, 25, 26, 27, 28, 28, 29, 30, 31, 32, 33, 34, 35, 36
        };

        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ballroullete);

        ballBitmap = scaleBitmap(ballBitmap, 50, 50); //tamaño de la imagen

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//suavizar los bordes de lo que se dibuja
        paint.setTextSize(40);
    }

    private Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    @Override
    protected void onDraw( Canvas canvas) {
        super.onDraw(canvas);

        //1.center and roulette radious
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) -50; //rouletteRadius

        //2. printRoulette
        float startAngle = 0;
        float sweepAngle = 360f / 37; //angle for each number in array

        for (int i = 0; i < 37; i++) {
            //colorea los trozo de la ruleta
            paint.setColor(colorRoulette[i]);

            canvas.drawArc(
                    centerX - radius, centerY - radius, // Esquina superior izquierda
                    centerX + radius, centerY + radius,   // Esquina inferior derecha
                    startAngle, sweepAngle,                      // Ángulo inicial y barrido
                    true, paint);                                // Rellenar la sección

            //printNumbers in seccion
            paint.setColor(Color.WHITE); //color texto
            float textAngle = startAngle + sweepAngle / 2; //Angulo del texto
            float textX = centerX + (float) (radius * 0.7 * Math.cos(Math.toRadians(textAngle)));
            float textY = centerY + (float) (radius * 0.7 * Math.sin(Math.toRadians(textAngle)));
            canvas.drawText(String.valueOf(i), textX, textY, paint);

            startAngle += sweepAngle;
        }
        //3. print ball in its position
        float ballX = centerX + (float) (radius * 0.8 * Math.cos(Math.toRadians(angleBall)));
        float ballY = centerY + (float) (radius * 0.8 * Math.sin(Math.toRadians(angleBall)));
        canvas.drawBitmap(ballBitmap, ballX - ballBitmap.getWidth() / 2f, ballY - ballBitmap.getHeight() / 2f, paint);
    }
    private void spinRoulette() {
        int completeSpin = 5; // Número de giros completos
        float finalAngle = 360 * completeSpin + (float) (Math.random() *360);//Angulo final

        animator = ValueAnimator.ofFloat(0, finalAngle);
        animator.setDuration(3000); //duración del animación
        animator.setInterpolator(new DecelerateInterpolator()); //desaceleración

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angleBall = (float) animation.getAnimatedValue(); //Actualiza el angulo

                invalidate(); //redibuja la vista
            }
        });
        animator.start();
    }
}
