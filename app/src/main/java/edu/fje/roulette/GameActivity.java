package edu.fje.roulette;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GameActivity extends AppCompatActivity {
    private RouletteView roulette;
    private BettingBoard bettingBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //Obtener la referencia a la vista personalizada
        roulette = findViewById(R.id.roulette);
        bettingBoard = findViewById(R.id.bettingBoard);

        bettingBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implementa esta función para obtener el número seleccionado
                //int selectedNumber = getSelectedNumber(v);
                Toast.makeText(GameActivity.this, "Apuesta en el número:", Toast.LENGTH_SHORT).show();
            }

            //private int getSelectedNumber(View v) {
            //}
        });
    }
}