package com.example.to_do_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.to_do_list.adpters.ListasRecyclerAdpter;
import com.example.to_do_list.models.Lista;
import com.example.to_do_list.persistence.Repository;
import com.example.to_do_list.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class ListasActivity extends AppCompatActivity implements
        ListasRecyclerAdpter.OnListaListener,
        FloatingActionButton.OnClickListener {

    private RecyclerView mRecyclerView;

    private ArrayList<Lista> mListas = new ArrayList<>();
    private ListasRecyclerAdpter listasRecyclerAdpter;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycleView);
        findViewById(R.id.fab_lista);
        repository = new Repository(this);
        initRecycleView();
        findListas();
        setSupportActionBar((Toolbar) findViewById(R.id.to_do_list_toolbar));
        setTitle("To Do List");
    }

    private void findListas() {
        repository.retreviewListas().observe(this, new Observer<List<Lista>>() {
            @Override
            public void onChanged(List<Lista> listas) {
                if (mListas.size() > 0 ) {
                    mListas.clear();
                }
                if (listas != null) {
                    mListas.addAll(listas);
                }
                listasRecyclerAdpter.notifyDataSetChanged();
            }
        });
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        listasRecyclerAdpter = new ListasRecyclerAdpter(mListas, this);
        mRecyclerView.setAdapter(listasRecyclerAdpter);
    }

    @Override
    public void onListaClick(int position) {
        Intent intent = new Intent(this, TarefasActivity.class);
        intent.putExtra("lista", mListas.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
//        View mView = getLayoutInflater().inflate(R.layout.dialog_lista, null);
//        EditText mNomeLista = (EditText) mView.findViewById(R.id.cad_lista_nome);
//        Button mCriar = (Button) mView.findViewById(R.id.button_cad_lista);
    }

    private void deleteLista(Lista lista) {
        mListas.remove(lista);
        listasRecyclerAdpter.notifyDataSetChanged();
        repository.deleteLista(lista);
        repository.deleteTarefaFromLista(lista.getId());
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteLista(mListas.get(viewHolder.getAdapterPosition()));
        }
    };
}
