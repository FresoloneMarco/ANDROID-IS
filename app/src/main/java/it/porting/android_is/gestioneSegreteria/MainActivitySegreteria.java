package it.porting.android_is.gestioneSegreteria;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

import it.porting.android_is.R;
import it.porting.android_is.adapter.RequestAdapter;
import it.porting.android_is.firebaseArchive.FireBaseArchive;
import it.porting.android_is.firebaseArchive.bean.RequestBean;

public class MainActivitySegreteria extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestAdapter requestAdapter;
    private FireBaseArchive fireBaseArchive;
    private ArrayList<RequestBean> requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_segreteria);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        //individuo la recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //inizializzo un riferimento all'oggetto che si interfaccia con firebase
        fireBaseArchive = new FireBaseArchive();

        //prelevo tutte le request da inserire nella recyclerview
        requests = fireBaseArchive.getAllRequests();
        //inizializzo l'adapter
        requestAdapter = new RequestAdapter(requests);
        //imposto l'adapter per la recyclerview
        recyclerView.setAdapter(requestAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }
}
