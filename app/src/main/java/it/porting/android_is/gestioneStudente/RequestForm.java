package it.porting.android_is.gestioneStudente;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import it.porting.android_is.R;
import it.porting.android_is.gestioneUtente.Guida;
import it.porting.android_is.gestioneUtente.LoginActivity;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;
import it.porting.android_is.utility.LazyInitializedSingleton;

public class RequestForm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_form);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 153, 0)));
        actionBar.setTitle("Inserisci richiesta");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option1:
                modpage();
                return true;
            case R.id.option2:
                guida();
                return true;
            case R.id.logout:
                logout();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    public void modpage() {
        Intent intent = new Intent(getApplicationContext(), ViewActivityUtente.class);
        startActivity(intent);
    }

    public void guida() {
        Intent intent = new Intent(getApplicationContext(), Guida.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


    public void logout() {
        //Logout dal modulo di autenticazione di firebase
        FirebaseAuth.getInstance().signOut();
        //elimino la "sessione"
        LazyInitializedSingleton lazyInitializedSingleton = LazyInitializedSingleton.getInstance();
        lazyInitializedSingleton.clearInstance();
        //ritorno alla pagina di login
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }



}
