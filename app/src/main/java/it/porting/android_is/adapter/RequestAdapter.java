package it.porting.android_is.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        holder.idText.setText(Integer.toString(arrayList.get(position).getId()));
        holder.livelloText.setText(arrayList.get(position).getLevel());
        holder.releaseText.setText(arrayList.get(position).getRelease_date().toString());
        holder.expiryText.setText(arrayList.get(position).getExpiry_date().toString());
        holder.annoText.setText(arrayList.get(position).getYear());
        holder.serialeText.setText(Integer.toString(arrayList.get(position).getSerial()));
        holder.cfuText.setText(Integer.toString(arrayList.get(position).getValidated_cfu()));

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
