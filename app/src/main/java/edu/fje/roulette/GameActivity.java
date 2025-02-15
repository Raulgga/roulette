package edu.fje.roulette;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private RouletteView roulette;
    private BettingBoard bettingBoard;
    private GameView gameView;
    private Chip draggingChip;
    private float touchOffsetX, touchOffsetY;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        roulette = findViewById(R.id.roulette);
        bettingBoard = findViewById(R.id.bettingBoard);
        gameView = findViewById(R.id.gameView);

        if (bettingBoard == null || gameView == null || roulette == null) {
            Log.e("GameActivity", "Error: Una o más vistas no se han inicializado correctamente.");
            return;
        }

        bettingBoard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bettingBoard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        bettingBoard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onTouchEvent(event);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameView != null && gameView.chips != null) {
                    for (Chip chip : gameView.chips) {
                        if (chip.isTouched(x, y)) {
                            draggingChip = chip;
                            touchOffsetX = x - chip.x;
                            touchOffsetY = y - chip.y;
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (draggingChip != null) {
                    draggingChip.x = x - touchOffsetX;
                    draggingChip.y = y - touchOffsetY;
                    bettingBoard.invalidate();
                    gameView.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (draggingChip != null) {
                    if (isBetPlaced(draggingChip)) {
                        if (roulette != null) {
                            roulette.spinRoulette();
                            resetChips();
                        }
                    }
                    draggingChip = null;
                }
                break;
        }
        return true;
    }

    private boolean isBetPlaced(Chip chip) {
        if (bettingBoard.getHeight() == 0) {
            Log.w("GameActivity", "Warning: bettingBoard.getHeight() aún es 0");
        }
        return chip.y < bettingBoard.getY() + bettingBoard.getHeight() - 100;
    }

    private void resetChips() {
        if (gameView != null && gameView.chips != null) {
            for (Chip chip : gameView.chips) {
                chip.resetPosition();
            }
            gameView.invalidate();
        }
    }
}
