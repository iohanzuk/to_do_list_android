package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.to_do_list.models.Lista;
import com.example.to_do_list.models.Tarefa;
import com.example.to_do_list.persistence.Repository;
import com.example.to_do_list.util.Utility;

public class TarefaActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "TarefaActivity";
    private static final int EDIT_TAREFA_ENABLED = 1;
    private static final int EDIT_TAREFA_DISABLED = 0;

    private LineEditText lineEditText;
    private EditText editTextToolbar;
    private TextView textViewToolbar;
    private Tarefa tarefa;
    private Lista mLista;
    private GestureDetector gestureDetector;
    private RelativeLayout checkContainer, backArrowContainer;
    private ImageButton check, backArrow;
    private int modo;
    private Repository repository;
    private Tarefa mTarefaFinal;

    private boolean isNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);
        lineEditText = findViewById(R.id.tarefa_descricao_edit);
        editTextToolbar = findViewById(R.id.nome_tarefa_edit);
        textViewToolbar = findViewById(R.id.nome_tarefa_texto);
        checkContainer = findViewById(R.id.check_container);
        backArrowContainer = findViewById(R.id.back_arrow_container);
        check = findViewById(R.id.toolbar_check);
        backArrow = findViewById(R.id.toolbar_back);

        repository = new Repository(this);
        mLista = getIntent().getParcelableExtra("lista");

        if (getIncoomingContent()) {
            setNovaTarefa();
            enableEditMode();
        } else {
            setPropriedadesTarefa();
            disableContentIteraction();
        }
        setLinesters();
    }

    private boolean getIncoomingContent() {
        if (getIntent().hasExtra("tarefa")) {
            tarefa = getIntent().getParcelableExtra("tarefa");
            mTarefaFinal = new Tarefa();
            mTarefaFinal.setNome(tarefa.getNome());
            mTarefaFinal.setListaId(mLista.getId());
            mTarefaFinal.setDescricao(tarefa.getDescricao());
            mTarefaFinal.setTimestamp(tarefa.getTimestamp());
            mTarefaFinal.setId(tarefa.getId());

            modo = EDIT_TAREFA_DISABLED;
            isNewTask = false;
            return false;
        }
        modo = EDIT_TAREFA_ENABLED;
        isNewTask = true;
        return true;
    }

    private void enableEditMode(){
        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        textViewToolbar.setVisibility(View.GONE);
        editTextToolbar.setVisibility(View.VISIBLE);

        modo = EDIT_TAREFA_ENABLED;
        enableContentIteraction();
    }

    private void disableEditMode(){
        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        textViewToolbar.setVisibility(View.VISIBLE);
        editTextToolbar.setVisibility(View.GONE);

        modo = EDIT_TAREFA_DISABLED;
        disableContentIteraction();
        String temp = lineEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0) {
            mTarefaFinal.setNome(editTextToolbar.getText().toString());
            mTarefaFinal.setDescricao(lineEditText.getText().toString());
            mTarefaFinal.setTimestamp(Utility.getCurrentTimesTamp());

            if (!mTarefaFinal.getDescricao().equals(tarefa.getDescricao()) ||
                    !mTarefaFinal.getNome().equals(tarefa.getNome())) {
                saveChanges();
            }
        }
    }

    private void setNovaTarefa(){
        textViewToolbar.setText("Nova Tarefa");
        editTextToolbar.setText("Nova Tarefa");

        tarefa = new Tarefa();
        mTarefaFinal = new Tarefa();

        tarefa.setNome("Nova Tarefa");
        mTarefaFinal.setNome("Nova Tarefa");
        mTarefaFinal.setListaId(mLista.getId());
    }

    private void setPropriedadesTarefa() {
        textViewToolbar.setText(tarefa.getNome());
        editTextToolbar.setText(tarefa.getNome());
        lineEditText.setText(tarefa.getDescricao());

        mTarefaFinal.setId(tarefa.getId());
        mTarefaFinal.setNome(tarefa.getNome());
        mTarefaFinal.setDescricao(tarefa.getDescricao());
        mTarefaFinal.setListaId(tarefa.getListaId());
        mTarefaFinal.setTimestamp(tarefa.getTimestamp());
    }

    private void setLinesters(){
        lineEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, this);
        textViewToolbar.setOnClickListener(this);
        check.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        editTextToolbar.addTextChangedListener(this);
    }

    private void disableContentIteraction(){
        lineEditText.setKeyListener(null);
        lineEditText.setFocusable(false);
        lineEditText.setFocusableInTouchMode(false);
        lineEditText.setCursorVisible(false);
        lineEditText.clearFocus();
    }

    private void enableContentIteraction(){
        lineEditText.setKeyListener(new EditText(this).getKeyListener());
        lineEditText.setFocusable(true);
        lineEditText.setFocusableInTouchMode(true);
        lineEditText.setCursorVisible(true);
        lineEditText.requestFocus();
    }

    private void hideSoftKeyboard() {
        InputMethodManager imn = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imn.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void saveChanges() {
        if(isNewTask){
            saveNovaTarefa();
        }else {
            updateTarefa();
        }
    }

    private void saveNovaTarefa() {
       repository.insertTarefa(mTarefaFinal);
    }

    private void updateTarefa() {
        repository.updateTarefa(mTarefaFinal);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_check:
                hideSoftKeyboard();
                disableEditMode();
                break;
            case R.id.nome_tarefa_texto:
                enableEditMode();
                editTextToolbar.requestFocus();
                editTextToolbar.setSelection(editTextToolbar.length());
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (modo == EDIT_TAREFA_ENABLED) {
            onClick(check);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("modo", modo);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        modo = savedInstanceState.getInt("modo");
        if (modo == EDIT_TAREFA_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        textViewToolbar.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
