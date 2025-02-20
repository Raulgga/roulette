package edu.fje.roulette;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private RouletteView roulette;
    private BettingBoard bettingBoard;
    private GameView gameView;
    private Chip draggingChip;
    private float touchOffsetX, touchOffsetY;
    private int playerBalance = 1000;
    private TextView balanceTextView;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        roulette = findViewById(R.id.roulette);
        bettingBoard = findViewById(R.id.bettingBoard);
        gameView = findViewById(R.id.gameView);
        balanceTextView = findViewById(R.id.balanceTextView);

        if (bettingBoard == null || gameView == null || roulette == null) {
            Log.e("GameActivity", "Error: Una o más vistas no se han inicializado correctamente.");
            return;
        }

        updateBalanceUI();

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
                            deductBet(draggingChip.getValue());
                            roulette.spinRoulette();
                            checkingWinningCondition();
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
        float chipX = chip.x;
        float chipY = chip.y;

        if (chip.y < bettingBoard.getY() + bettingBoard.getHeight() - bettingBoard.cellHeight) {
            return true;
        }

        int startY = bettingBoard.getHeight() - bettingBoard.cellHeight;
        int extraWidth = bettingBoard.getWidth() / 4;

        for (int i = 0; i < 4; i++) {
            int startX = i * extraWidth;
            if(chipX >= startX && chipX < startX + extraWidth && chipY >= startY){
                chip.setSpecialBet(i);
                return true;
            }
        }
        return false;
    }

    private void resetChips() {
        if (gameView != null && gameView.chips != null) {
            for (Chip chip : gameView.chips) {
                chip.resetPosition();
            }
            gameView.invalidate();
        }
    }

    private void checkingWinningCondition(){
        if(roulette != null){
            int winningNumber = roulette.getWinningNumber();
            Log.d("GameActivity", "Número ganador: " + winningNumber);

            boolean hasWon = false;
            int winnings = 0;
            if(gameView != null && gameView.chips != null){
                for (Chip chip: gameView.chips) {
                    if(isWinningBet(chip, winningNumber)){
                        hasWon = true;
                        winnings += chip.getValue() * 2;
                        break;
                    }
                }
            }
            if(hasWon){
                updateBalance(winnings);
                Toast.makeText(GameActivity.this,"Has ganado! +$" + winnings,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(GameActivity.this, "Has perdido...", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean isWinningBet(Chip chip, int winningNumber) {
        // Verifica si el chip está en el número ganador o en una apuesta válida
        if(chip.getBetNumber() == winningNumber){
            return true;
        }

        //Verifica apuestas especiales
        switch (chip.getSpecialBet()){
            case 0: //Even
                return winningNumber != 0 && winningNumber % 2 == 0;
            case 1: //Black
                return !roulette.isRed(winningNumber) && winningNumber != 0;
            case 2: // Red
                return roulette.isRed(winningNumber);
            case 3: // Odd
                return winningNumber % 2 != 0;
        }
        return false;
    }

    private void updateBalance(int amount) {
        playerBalance += amount;
        updateBalanceUI();
    }

    private void deductBet(int betAmount){
        playerBalance -= betAmount;
        updateBalanceUI();
    }

    private void updateBalanceUI(){
        balanceTextView.setText("Saldo: $" + playerBalance);
    }
}