package com.os_project.quran.network;

import com.os_project.quran.response.SurahDetailResponse;
import com.os_project.quran.response.SurahResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderAPI {
    @GET("surah")
    Call<SurahResponse> getSurah();

    @GET("sura/{language}/{id}")
    Call<SurahDetailResponse> getSurahDetail(@Path("language")String lan,
                                             @Path("id")int surahId);
}
