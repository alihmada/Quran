package com.os_project.quran.response;

import com.google.gson.annotations.SerializedName;
import com.os_project.quran.model.SurahDetail;

import java.util.List;

public class SurahDetailResponse {
    @SerializedName("result")
    private List<SurahDetail> list;

    public List<SurahDetail> getList() {
        return list;
    }

    public void setList(List<SurahDetail> list) {
        this.list = list;
    }
}
