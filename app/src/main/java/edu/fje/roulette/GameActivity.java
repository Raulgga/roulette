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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private RouletteView roulette;
    private BettingBoard bettingBoard;
    private GameView gameView;
    private Chip draggingChip;
    private float touchOffsetX, touchOffsetY;
    private int playerBalance = 1000;
    private TextView balanceTextView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db = FirebaseFirestore.getInstance();

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
        if (chip.getValue() > playerBalance) {
            Toast.makeText(this, "Saldo insuficiente para esta apuesta", Toast.LENGTH_SHORT).show();
            return false;
        }

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

    private void checkingWinningCondition() {
        if (roulette != null) {
            int winningNumber = roulette.getWinningNumber();
            Log.d("GameActivity", "Número ganador: " + winningNumber);

            boolean hasWon = false;
            int winnings = 0;
            if (gameView != null && gameView.chips != null) {
                for (Chip chip : gameView.chips) {
                    // Verifica si la apuesta es ganadora
                    if (isWinningBet(chip, winningNumber)) {
                        hasWon = true;
                        winnings += chip.getValue() * 2;
                        break;
                    }
                }
            }

            // Mostrar mensaje y actualizar saldo
            if (hasWon) {
                updateBalance(winnings);
                Toast.makeText(GameActivity.this, "Has ganado! +$" + winnings, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameActivity.this, "Has perdido...", Toast.LENGTH_SHORT).show();
            }

            // Ahora, registrar las apuestas en Firestore
            for (Chip chip : gameView.chips) {
                if (chip != null) {
                    String userName = "exampleUser"; // Aquí deberías obtener el nombre del usuario de alguna forma

                    double betAmount = chip.getValue();
                    int betNumber = chip.getBetNumber(); // Número de la apuesta
                    String betType = getBetTypeFromChip(chip); // Tipo de apuesta (rojo, negro, par, impar, etc.)
                    boolean isWinner = isWinningBet(chip, winningNumber);

                    // Obtener el timestamp
                    long timestamp = System.currentTimeMillis();

                    // Crear una nueva apuesta
                    Bet bet = new Bet(userName, betAmount, betNumber, betType, isWinner, timestamp);

                    // Convertir la apuesta a un Map para Firebase
                    Map<String, Object> betMap = bet.toMap();

                    // Guardar la apuesta en Firestore
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("bets")
                            .add(betMap)
                            .addOnSuccessListener(documentReference -> {
                                Log.d("Firestore", "Apuesta guardada con éxito");
                            })
                            .addOnFailureListener(e -> {
                                Log.w("Firestore", "Error al guardar la apuesta", e);
                            });
                }
            }
        }
    }

    // Método para obtener el tipo de apuesta desde el chip
    private String getBetTypeFromChip(Chip chip) {
        // Si la apuesta es numérica, devolvemos "Number"
        if (chip.getBetNumber() != -1) {
            return "Number " + chip.getBetNumber(); // La apuesta es a un número específico
        }

        // Si es una apuesta especial, usamos el valor de specialBet para determinar el tipo
        switch (chip.getSpecialBet()) {
            case 0: return "Even";  // Par
            case 1: return "Black"; // Negro
            case 2: return "Red";   // Rojo
            case 3: return "Odd";   // Impar
            default: return "Unknown";  // Otro tipo de apuesta
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