package edu.fje.roulette;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que hereta de fragment
 *
 * @author sergi.grau@fje.edu
 * @version 5.0 27.01.2020
 */
public class LlistaFragmentActivity extends Fragment {

    ListView llista;
    ArrayAdapter<String> itemsAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_llista,
                container, false);

        llista = view.findViewById(R.id.llista);

        itemsAdapter =
                new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, FragmentsActivity.CURSOS);
        llista.setAdapter(itemsAdapter);
        return view;
    }

    public void afegirNom(String nom){
        itemsAdapter.notifyDataSetChanged();
    }
}