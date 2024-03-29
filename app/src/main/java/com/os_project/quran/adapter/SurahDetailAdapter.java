package com.os_project.quran.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.os_project.quran.R;
import com.os_project.quran.model.SurahDetail;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailAdapter extends RecyclerView.Adapter<SurahDetailAdapter.ViewHolder> {

    private Context context;
    private List<SurahDetail> list;

    public SurahDetailAdapter(Context context, List<SurahDetail> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<SurahDetail> getList() {
        return list;
    }

    public void setList(List<SurahDetail> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SurahDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.surah_detail_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahDetailAdapter.ViewHolder holder, int position) {
        holder.ayaNo.setText(String.valueOf(list.get(position).getAya()));
        holder.arabicText.setText(list.get(position).getArabic_text());
        holder.translation.setText(list.get(position).getTranslation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filter(ArrayList<SurahDetail> details){
        list = details;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView ayaNo, arabicText, translation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ayaNo = itemView.findViewById(R.id.aya_no);
            arabicText = itemView.findViewById(R.id.arabic_text);
            translation = itemView.findViewById(R.id.translation);
        }
    }
}
