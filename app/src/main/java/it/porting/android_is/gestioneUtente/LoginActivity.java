package it.porting.android_is.gestioneUtente;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.porting.android_is.gestioneStudente.MainActivityStudente;
import it.porting.android_is.R;


public class LoginActivity extends AppCompatActivity {

    private Button bLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        bLogin = findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login(){
        Intent intent = new Intent(this, MainActivityStudente.class);
        startActivity(intent);
    }
}
