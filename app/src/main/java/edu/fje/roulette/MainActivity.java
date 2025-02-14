package edu.fje.roulette;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragments();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setupFragments();
    }

    private void setupFragments() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        View landscapeLayout = findViewById(R.id.landscapeLayout);
        View portraitLayout = findViewById(R.id.portraitLayout);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landscapeLayout.setVisibility(View.VISIBLE);
            portraitLayout.setVisibility(View.GONE);
            transaction.replace(R.id.landscapeTopFragment, new TopFragment());
            transaction.replace(R.id.landscapeBottomFragment, new BottomFragment());
        } else {
            landscapeLayout.setVisibility(View.GONE);
            portraitLayout.setVisibility(View.VISIBLE);
            transaction.replace(R.id.portraitTopFragment, new TopFragment());
            transaction.replace(R.id.portraitBottomFragment, new BottomFragment());
        }

        transaction.commit();
    }
}