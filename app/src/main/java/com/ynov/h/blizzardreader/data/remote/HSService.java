package com.ynov.h.blizzardreader.data.remote;

import com.ynov.h.blizzardreader.data.model.Card;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface HSService {

    @Headers({
            "X-Mashape-Key: VYaB2iL2MFmshX4kAlcyatjLWpRtp1ox9EVjsnEhpx6fcGvvIi"
    })
    @GET("cards/classes/{playerClass}")
    Call<List<Card>> getCards(@Path("playerClass") String playerClass);

}
