package edu.fje.roulette;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class BottomFragment extends Fragment {
    public Button btnPlay;
    public Button btnAddUser;
    public Button btnOptions;
    private OnAddUserListener mlistener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

        btnPlay = view.findViewById(R.id.btnPlay);
        btnAddUser = view.findViewById(R.id.btnAddUser);
        btnOptions = view.findViewById(R.id.btnOptions);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), GameActivity.class);
                startActivity(intent);
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar a RankingBetsActivity
                Intent intent = new Intent(getActivity(), RankingBetsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void setOnAddUserListener(OnAddUserListener listener) {
        mlistener = listener;
    }

    public interface OnAddUserListener {
        void onAddUser();
    }
}
