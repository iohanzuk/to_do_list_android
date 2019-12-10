package com.example.to_do_list.adpters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.R;
import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.util.Utility;

import java.util.ArrayList;

public class TarefasRecyclerAdpter extends RecyclerView.Adapter<TarefasRecyclerAdpter.ViewHolder> {

    private static final String TAG = "TarefaAdpter";

    private ArrayList<Tarefa> tarefas = new ArrayList<>();
    private TarefasRecyclerAdpter.OnTarefasListener onTarefasListener;

    public TarefasRecyclerAdpter(ArrayList<Tarefa> tarefas, TarefasRecyclerAdpter.OnTarefasListener onTarefasListener) {
        this.tarefas = tarefas;
        this.onTarefasListener = onTarefasListener;
    }

    @NonNull
    @Override
    public TarefasRecyclerAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_list_tarefas, viewGroup, false);
        return new TarefasRecyclerAdpter.ViewHolder(view, onTarefasListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefasRecyclerAdpter.ViewHolder holder, int position) {
        try{
            String month = tarefas.get(position).getTimestamp().substring(0, 2);
            month = Utility.getMonthForNumber(month);
            String year = tarefas.get(position).getTimestamp().substring(0, 3);
            String timestamp = month + " " + year;

            holder.timestamp.setText(timestamp);
            holder.nome.setText(tarefas.get(position).getNome());
        }catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nome, timestamp;
        private OnTarefasListener onTarefasListener;

        public ViewHolder(@NonNull View itemView, TarefasRecyclerAdpter.OnTarefasListener onTarefasListener) {
            super(itemView);
            nome      = itemView.findViewById(R.id.nome_tarefa);
            timestamp = itemView.findViewById(R.id.timestamp_tarefa);
            this.onTarefasListener = onTarefasListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTarefasListener.onTarefaClick(getAdapterPosition());
        }
    }

    public interface OnTarefasListener {
        void onTarefaClick(int position);
    }
}
