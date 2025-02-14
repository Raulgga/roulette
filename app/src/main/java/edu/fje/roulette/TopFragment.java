package edu.fje.roulette;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TopFragment extends Fragment {

    public EditText inputUser;
    public TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        inputUser = view.findViewById(R.id.inputUser);
        textView = view.findViewById(R.id.textView);

        return view;
    }
}
