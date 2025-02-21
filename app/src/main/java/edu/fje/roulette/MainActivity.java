package edu.fje.roulette;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements BottomFragment.OnAddUserListener {

    private DatabaseReference DBUsers;
    private TopFragment topFragment;
    private BottomFragment bottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos la referencia a Firebase
        DBUsers = FirebaseDatabase.getInstance().getReference("users");

        // Configurar los fragments
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

        // Fragmentos para orientación horizontal o vertical
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.landscapeTopFragment, new TopFragment());
            transaction.replace(R.id.landscapeBottomFragment, new BottomFragment());
        } else {
            transaction.replace(R.id.portraitTopFragment, new TopFragment());
            transaction.replace(R.id.portraitBottomFragment, new BottomFragment());
        }

        transaction.commit();
    }

    @Override
    public void onAddUser() {
        // Obtenemos el nombre desde el TopFragment
        String userName = topFragment.getUserName();

        if (!userName.isEmpty()) {
            // Guardamos el nombre del usuario en Firebase
            String id = DBUsers.push().getKey();  // Genera un ID único
            DBUsers.child(id).setValue(userName);

            // Limpiamos el campo de texto
            topFragment.clearUserName();

            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Usuario añadido", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Por favor, ingrese un nombre", Toast.LENGTH_LONG).show();
        }
    }
}