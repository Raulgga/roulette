package edu.fje.roulette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BettingBoard extends View {
    private Paint paint;
    private int cellWidth;
    private int cellHeight;
    private int numero;

    public BettingBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BettingBoard(Context context, int numero) {
        super(context);
        this.numero = numero;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Calcular el ancho y alto de cada celda
        cellWidth = w / 3;  // 3 columnas
        cellHeight = (h - (h / 12)) / 12; // 12 filas, restando el espacio para la casilla superior
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar la casilla superior con el "0"
        drawCasillaSuperior(canvas);

        // Dibujar la cuadrícula
        onDrawCuadricula(canvas);
    }

    private void drawCasillaSuperior(Canvas canvas) {
        // Crear pincel para el rectángulo
        Paint pincelRectangulo = new Paint();
        pincelRectangulo.setColor(Color.GREEN); // Color verde para la casilla superior

        // Dibujar el rectángulo de la casilla superior
        int alturaCasillaSuperior = getHeight() / 12; // Altura de la casilla superior
        canvas.drawRect(0, 0, getWidth(), alturaCasillaSuperior, pincelRectangulo);

        // Crear pincel para el texto
        Paint pincelTexto = new Paint();
        pincelTexto.setColor(Color.WHITE); // Color blanco
        pincelTexto.setTextSize(40); // Tamaño de texto 40

        // Obtener límites del texto
        Rect bounds = new Rect();
        String texto = "0";
        pincelTexto.getTextBounds(texto, 0, texto.length(), bounds);

        // Calcular posición centrada del texto dentro de la casilla superior
        int x = (getWidth() - bounds.width()) / 2;
        int y = (alturaCasillaSuperior + bounds.height()) / 2;

        // Dibujar el texto "0"
        canvas.drawText(texto, x, y, pincelTexto);
    }

    protected void onDrawCuadricula(Canvas canvas) {
        // Dibujar una cuadrícula de 3x12 celdas
        int alturaCasillaSuperior = getHeight() / 12; // Altura de la casilla superior
        for (int fila = 0; fila < 12; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                int startX = columna * cellWidth;
                int startY = alturaCasillaSuperior + fila * cellHeight; // Ajustar la posición Y para dejar espacio para la casilla superior
                // Dibujar cada casilla
                drawCasilla(canvas, startX, startY, fila * 3 + columna + 1);
            }
        }
    }

    private void drawCasilla(Canvas canvas, int startX, int startY, int numero) {
        // Crear pincel para el rectángulo
        Paint pincelRectangulo = new Paint();
        pincelRectangulo.setColor(Color.BLUE); // Color azul

        // Dibujar el rectángulo de la casilla
        canvas.drawRect(startX, startY, startX + cellWidth, startY + cellHeight, pincelRectangulo);

        // Crear pincel para el número
        Paint pincelNumero = new Paint();
        pincelNumero.setColor(Color.WHITE); // Color blanco
        pincelNumero.setTextSize(40); // Tamaño de texto 40

        // Obtener límites del texto
        Rect bounds = new Rect();
        String texto = String.valueOf(numero);
        pincelNumero.getTextBounds(texto, 0, texto.length(), bounds);

        // Calcular posición centrada del número dentro de la casilla
        int x = startX + (cellWidth - bounds.width()) / 2;
        int y = startY + (cellHeight + bounds.height()) / 2;

        // Dibujar número
        canvas.drawText(texto, x, y, pincelNumero);
    }
}