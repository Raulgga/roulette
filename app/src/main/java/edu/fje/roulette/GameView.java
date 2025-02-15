package edu.fje.roulette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class GameView extends View {
    List<Chip> chips;

    public GameView(Context context) {
        super(context);
        chips = new ArrayList<>();
        addInitialChips();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        chips = new ArrayList<>();
        addInitialChips();
    }

    private void addInitialChips() {
        // Añadir algunas fichas iniciales para que los jugadores puedan arrastrarlas
        chips.add(new Chip(100, 800, Color.BLUE, 10));
        chips.add(new Chip(200, 800, Color.RED, 20));
        chips.add(new Chip(300, 800, Color.GREEN, 50));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibuja las fichas
        for (Chip chip : chips) {
            chip.draw(canvas);
        }
    }
}
