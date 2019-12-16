package it.porting.android_is.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import it.porting.android_is.R;
import it.porting.android_is.firebaseArchive.bean.RequestBean;


/**
 * Classe Adapter che consente la gestione della recyclerView nell'activity della segreteria
 */
public class RequestAdapter extends RecyclerView.Adapter <RequestAdapter.ViewHolder>{

    ArrayList<RequestBean>arrayList;


    public RequestAdapter(ArrayList<RequestBean> arrayList) {
        this.arrayList = arrayList;


    }


    /**
     * Inserisce nella recyclerview la formattazione di ogni riga
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);

        return new ViewHolder(v);


    }


    /**
     * Inserisce in ogni campo il rispettivo valore
     * @param holder la recyclerview
     * @param position posizione della tupla
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int formato = DateFormat.LONG;
        DateFormat dateFormat = DateFormat.getDateInstance(formato, Locale.ITALY);
        String dataRelease = dateFormat.format(arrayList.get(position).getRelease_date().toDate());
        String dataExpiry = dateFormat.format(arrayList.get(position).getExpiry_date().toDate());
        holder.idText.setText("ID richiesta: "  + Integer.toString(arrayList.get(position).getId()));
        holder.livelloText.setText("Livello: " +  arrayList.get(position).getLevel());
        holder.releaseText.setText("Rilascio: " + dataRelease);
        holder.expiryText.setText("Scadenza: " + dataExpiry);
        holder.annoText.setText("Anno: " + arrayList.get(position).getYear());
        holder.serialeText.setText("Seriale: " + Integer.toString(arrayList.get(position).getSerial()));
        holder.cfuText.setText("CFU: " + Integer.toString(arrayList.get(position).getValidated_cfu()));



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
        TextView nomeText;
        TextView cognomeText;
        TextView livelloText;
        TextView releaseText;
        TextView expiryText;
        TextView annoText;
        TextView serialeText;
        TextView cfuText;
        Button btSend;
        


        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            idText = root.findViewById(R.id.idText);
            nomeText = root.findViewById(R.id.nomeText);
            cognomeText = root.findViewById(R.id.cognomeText);
            livelloText = root.findViewById(R.id.livelloText);
            releaseText = root.findViewById(R.id.releaseText);
            expiryText = root.findViewById(R.id.expiryText);
            annoText = root.findViewById(R.id.annoText);
            serialeText = root.findViewById(R.id.serialeText);
            cfuText = root.findViewById(R.id.cfuText);
            btSend = root.findViewById(R.id.btSend);



        }
    }





}
