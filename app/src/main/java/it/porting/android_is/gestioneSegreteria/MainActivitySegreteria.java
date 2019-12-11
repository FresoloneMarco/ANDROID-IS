package it.porting.android_is.gestioneSegreteria;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import java.util.ArrayList;

import it.porting.android_is.R;
import it.porting.android_is.adapter.RequestAdapter;

public class MainActivitySegreteria extends AppCompatActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestAdapter requestAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_segreteria);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList <String> arrayList = new ArrayList<String>();
        for (int i=0; i < 10; i++){
            arrayList.add("Persona "+ i);
        }

        requestAdapter = new RequestAdapter(arrayList);
        recyclerView.setAdapter(requestAdapter);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
}
