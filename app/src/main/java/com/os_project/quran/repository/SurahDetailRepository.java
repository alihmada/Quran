package com.os_project.quran.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.os_project.quran.network.API;
import com.os_project.quran.network.JsonPlaceHolderAPI;
import com.os_project.quran.response.SurahDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurahDetailRepository {
    JsonPlaceHolderAPI jsonPlaceHolderAPI;

    public SurahDetailRepository() {
        this.jsonPlaceHolderAPI = API.getInstance().create(JsonPlaceHolderAPI.class);
    }

    public LiveData<SurahDetailResponse> getSurahDetail(String lan, int id) {
        MutableLiveData<SurahDetailResponse> data = new MutableLiveData<>();

        jsonPlaceHolderAPI.getSurahDetail(lan, id).enqueue(new Callback<SurahDetailResponse>() {
            @Override
            public void onResponse(Call<SurahDetailResponse> call, Response<SurahDetailResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SurahDetailResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
