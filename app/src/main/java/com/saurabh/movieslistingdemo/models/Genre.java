package com.saurabh.movieslistingdemo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saurabhs on 4/6/19.
 */
@Entity
public class Genre {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "genre_id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "genre_name")
    @SerializedName("name")
    @Expose
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
