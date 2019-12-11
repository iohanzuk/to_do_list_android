package com.example.to_do_list.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tarefas")
public class Tarefa implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer listaId;
    private String nome;
    private String descricao;
    private String timestamp;

    @Ignore
    public Tarefa(){}

    public Tarefa(String nome, Integer listaId, String descricao, String timestamp) {
        this.nome = nome;
        this.listaId = listaId;
        this.descricao = descricao;
        this.timestamp = timestamp;
    }


    protected Tarefa(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            listaId = null;
        } else {
            listaId = in.readInt();
        }
        nome = in.readString();
        descricao = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<Tarefa> CREATOR = new Creator<Tarefa>() {
        @Override
        public Tarefa createFromParcel(Parcel in) {
            return new Tarefa(in);
        }

        @Override
        public Tarefa[] newArray(int size) {
            return new Tarefa[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getListaId() {
        return listaId;
    }

    public void setListaId(Integer listaId) {
        this.listaId = listaId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (listaId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(listaId);
        }
        dest.writeString(nome);
        dest.writeString(descricao);
        dest.writeString(timestamp);
    }
}
