package edu.fje.roulette;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class RankingBetsActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private BetsAdapter adapter;
    private ArrayList<Bet> betsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_bets);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewBets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        betsList = new ArrayList<>();
        adapter = new BetsAdapter(betsList);
        recyclerView.setAdapter(adapter);

        fetchBetsFromFirestore();
    }

    // Método para obtener las apuestas de Firebase Firestore
    private void fetchBetsFromFirestore() {
        db.collection("bets")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        for (QueryDocumentSnapshot document : result) {
                            Bet bet = document.toObject(Bet.class);
                            betsList.add(bet);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("RankingBetsActivity", "Error getting documents.", task.getException());
                        Toast.makeText(RankingBetsActivity.this, "Error al obtener apuestas", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
