package com.os_project.quran.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.os_project.quran.network.API;
import com.os_project.quran.network.JsonPlaceHolderAPI;
import com.os_project.quran.response.SurahResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurahRepository {
    JsonPlaceHolderAPI jsonPlaceHolderAPI;

    public SurahRepository() {
        this.jsonPlaceHolderAPI = API.getRetrofit().create(JsonPlaceHolderAPI.class);
    }

    public LiveData<SurahResponse> getSurah() {
        MutableLiveData<SurahResponse> data = new MutableLiveData<>();

        jsonPlaceHolderAPI.getSurah().enqueue(new Callback<SurahResponse>() {
            @Override
            public void onResponse(@NonNull Call<SurahResponse> call, @NonNull Response<SurahResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SurahResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
