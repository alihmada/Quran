package com.os_project.quran.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.os_project.quran.repository.SurahRepository;
import com.os_project.quran.response.SurahResponse;

public class SurahViewModel extends ViewModel {

    private SurahRepository surahRepository;

    public SurahViewModel() {
        this.surahRepository = new SurahRepository();
    }

    public LiveData<SurahResponse> getSurah() {
        return surahRepository.getSurah();
    }
}