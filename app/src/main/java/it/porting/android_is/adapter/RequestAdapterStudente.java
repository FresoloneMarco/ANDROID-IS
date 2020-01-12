package it.porting.android_is.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import it.porting.android_is.R;
import it.porting.android_is.firebaseArchive.bean.RequestBean;

import static androidx.core.content.ContextCompat.startActivity;


/**
 * Classe Adapter che consente la gestione della recyclerView nell'activity della segreteria
 */
public class RequestAdapterStudente extends RecyclerView.Adapter <RequestAdapterStudente.ViewHolder> {

    ArrayList<RequestBean> arrayList;



    public RequestAdapterStudente(ArrayList<RequestBean> arrayList) {
        this.arrayList = arrayList;

    }




    /**
     * Inserisce nella recyclerview la formattazione di ogni riga
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item_studente, parent, false);
        return new ViewHolder(v);


    }


    /**
     * Inserisce in ogni campo il rispettivo valore
     *
     * @param holder   la recyclerview
     * @param position posizione della tupla
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.idText.setText("ID richiesta: "  + Integer.toString(arrayList.get(position).getId()));
        holder.emailText.setText("Email: " + arrayList.get(position).getUser_key());
        holder.statoText.setText("Stato : " + arrayList.get(position).isStato());

    }



    /**
     * conteggio elementi
     * @return
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    /**
     * classe "gestore" della recyclerview, in cui vengono inizializzati tutti i campi.
     */

    public class ViewHolder extends RecyclerView.ViewHolder {


        View root;
        TextView idText;
        TextView emailText;
        TextView statoText;






        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            idText = root.findViewById(R.id.idText);
            emailText = root.findViewById(R.id.emailText);
            statoText = root.findViewById(R.id.statoText);


        }
    }





}
