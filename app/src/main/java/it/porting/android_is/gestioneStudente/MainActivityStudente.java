package it.porting.android_is.gestioneStudente;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import it.porting.android_is.R;
import it.porting.android_is.gestioneUtente.ViewActivityUtente;

public class MainActivityStudente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_studente);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable (Color.rgb(255,153,0)));
        actionBar.setTitle("");
        Button bmod;
        bmod = findViewById(R.id.button2);
        bmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modpage();
            }
        });
    }

    public void modpage(){
        Intent intent = new Intent(getApplicationContext(), ViewActivityUtente.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
}
