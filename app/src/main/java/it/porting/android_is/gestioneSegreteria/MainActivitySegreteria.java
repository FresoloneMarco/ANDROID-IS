package it.porting.android_is.gestioneSegreteria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.porting.android_is.R;
import it.porting.android_is.adapter.RequestAdapter;
import it.porting.android_is.firebaseArchive.FireBaseArchive;
import it.porting.android_is.firebaseArchive.bean.RequestBean;
import it.porting.android_is.firebaseArchive.bean.UtenteBean;

public class MainActivitySegreteria extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RequestAdapter requestAdapter;
    private FireBaseArchive fireBaseArchive;
    private ArrayList<RequestBean> requestBeans;
    private ArrayList<UtenteBean> utenteBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_segreteria);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home Segreteria");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255,153,0)));
        requestBeans = new ArrayList<RequestBean>();
        utenteBeans = new ArrayList<UtenteBean>();

        //individuo la recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        //inizializzo un riferimento all'oggetto che si interfaccia con firebase
        fireBaseArchive = new FireBaseArchive();

        //prelevo tutte le request da inserire nella recyclerview
        fireBaseArchive.getAllRequests(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    //Se il task ha successo, salvo ogni "tupla" all'interno dell ArrayList
                    for(QueryDocumentSnapshot req : task.getResult()){
                        RequestBean requestBean = req.toObject(RequestBean.class);
                        requestBeans.add(requestBean);
                    }

                    utenteBeans = getUsersData(requestBeans);

                    //inizializzo l'adapter

                    requestAdapter = new RequestAdapter(requestBeans, utenteBeans);
                    //imposto l'adapter per la recyclerview

                    recyclerView.setAdapter(requestAdapter);


                }
                else{
                    Log.d("Errore nella query","ERRORE");
                }
            }
        });
    }


    public ArrayList <UtenteBean> getUsersData(ArrayList<RequestBean> requestBeans){

        for(RequestBean req : requestBeans) {
            String email = req.getUser_key();
            fireBaseArchive.getUserByKey(email, new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        UtenteBean utenteBean = task.getResult().toObject(UtenteBean.class);
                        utenteBeans.add(utenteBean);
                    }
                }
            });

        }

        return utenteBeans;
    }



}
