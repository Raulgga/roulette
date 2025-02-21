package edu.fje.roulette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BetsAdapter extends RecyclerView.Adapter<BetsAdapter.BetViewHolder> {

    private List<Bet> betsList;

    public BetsAdapter(List<Bet> betsList) {
        this.betsList = betsList;
    }

    @Override
    public BetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bet, parent, false);
        return new BetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BetViewHolder holder, int position) {
        Bet bet = betsList.get(position);
        holder.userNameTextView.setText("Usuario: " + bet.getUserName());
        holder.betAmountTextView.setText("Monto: $" + bet.getBetAmount());
        holder.winningNumberTextView.setText("Número: " + bet.getWinningNumber());
        holder.betTypeTextView.setText("Tipo de Apuesta: " + bet.getBetType());
    }

    @Override
    public int getItemCount() {
        return betsList.size();
    }

    public static class BetViewHolder extends RecyclerView.ViewHolder {

        public TextView userNameTextView;
        public TextView betAmountTextView;
        public TextView winningNumberTextView;
        public TextView betTypeTextView;

        public BetViewHolder(View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            betAmountTextView = itemView.findViewById(R.id.betAmountTextView);
            winningNumberTextView = itemView.findViewById(R.id.winningNumberTextView);
            betTypeTextView = itemView.findViewById(R.id.betTypeTextView);
        }
    }
}
