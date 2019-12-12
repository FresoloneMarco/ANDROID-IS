package it.porting.android_is.firebaseArchive;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import it.porting.android_is.firebaseArchive.bean.RequestBean;


public class FireBaseArchive {

    //istanza di connessione al db
    FirebaseFirestore db;
    final ArrayList <RequestBean> requestBeans;



    public FireBaseArchive(){
        db = FirebaseFirestore.getInstance();
        requestBeans = new ArrayList<RequestBean>();
    }


    /**
     * Consente di prelevare tutte le request dal db, restituisce un arraylist di request
     */

    public ArrayList<RequestBean> getAllRequests(){

        //Eseguo la "query", salvando la collection
        CollectionReference collectionReference = db.collection("request");

        //una volta completata, lancio onComplete
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //Se il task ha successo, salvo ogni "tupla" all'interno dell ArrayList
                    for(QueryDocumentSnapshot req : task.getResult()){
                        RequestBean requestBean = req.toObject(RequestBean.class);
                        System.out.println("inarchive" + requestBean.toString());
                        requestBeans.add(requestBean);

                    }

                }
                else{
                    Log.d("Errore nella query","ERRORE");
                }
            }
        });

        for(RequestBean r : requestBeans){
            System.out.println("Prova"+ r. toString());
        }
        return requestBeans;
    }

}
