package com.os_project.quran.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.os_project.quran.repository.SurahDetailRepository;
import com.os_project.quran.response.SurahDetailResponse;

public class SurahDetailViewModel extends ViewModel {
    public SurahDetailRepository surahDetailRepository;

    public SurahDetailViewModel() {
        this.surahDetailRepository = new SurahDetailRepository();
    }

    public LiveData<SurahDetailResponse> getSurahDetail(String lan, int id) {
        return surahDetailRepository.getSurahDetail(lan, id);
    }
}
