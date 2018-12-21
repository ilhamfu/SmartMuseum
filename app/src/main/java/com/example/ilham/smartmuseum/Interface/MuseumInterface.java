package com.example.ilham.smartmuseum.Interface;

import com.example.ilham.smartmuseum.Model.MuseumCollection;
import com.example.ilham.smartmuseum.Model.MuseumItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MuseumInterface {
    @GET("/item")
    Call<MuseumCollection> getMuseum(@Query("name") String name);
    @GET("/item")
    Call<MuseumItem> getItem(@Query("id") int id);
}
