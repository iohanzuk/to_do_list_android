package com.example.to_do_list.adpters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.R;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.util.Utility;

import java.util.ArrayList;

public class ListasRecyclerAdpter extends RecyclerView.Adapter<ListasRecyclerAdpter.ViewHolder> {

    private static final String TAG = "ListaAdpter";
    private ArrayList<Lista> listas = new ArrayList<>();
    private OnListaListener onListaListener;

    public ListasRecyclerAdpter(ArrayList<Lista> listas, OnListaListener onListaListener) {
        this.listas = listas;
        this.onListaListener = onListaListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_list_listas, viewGroup, false);
        return new ViewHolder(view, onListaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            String month = listas.get(position).getTimestamp().substring(0, 2);
            month = Utility.getMonthForNumber(month);
            String year = listas.get(position).getTimestamp().substring(0, 4);
            String timestamp = month + "\n" + year;
            holder.timestamp.setText(timestamp);
            holder.nome.setText(listas.get(position).getNome());
        }catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nome, timestamp;
        private OnListaListener onListaListener;

        public ViewHolder(@NonNull View itemView, OnListaListener onListaListener) {
            super(itemView);
            nome      = itemView.findViewById(R.id.nome_lista);
            timestamp = itemView.findViewById(R.id.timestamp_lista);
            this.onListaListener = onListaListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListaListener.onListaClick(getAdapterPosition());
        }
    }

    public interface OnListaListener{
        void onListaClick(int position);
    }
}
