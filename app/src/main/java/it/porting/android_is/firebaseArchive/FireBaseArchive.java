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



    public FireBaseArchive(){
        db = FirebaseFirestore.getInstance();
    }


    /**
     * Consente di prelevare tutte le request dal db, restituisce un arraylist di request
     * @param onCompleteListener
     */

    public void getAllRequests(OnCompleteListener<QuerySnapshot> onCompleteListener){
        //Eseguo la "query", salvando la collection
        CollectionReference collectionReference = db.collection("request");

        //una volta completata, lancio onComplete
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

}
