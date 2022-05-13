package com.os_project.quran.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.os_project.quran.R;
import com.os_project.quran.listener.SurahListener;
import com.os_project.quran.model.Surah;

import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.ViewHolder> {

    private Context context;
    private List<Surah> list;
    private final SurahListener surahListener;

    public SurahAdapter(Context context, List<Surah> list, SurahListener surahListener) {
        this.context = context;
        this.list = list;
        this.surahListener = surahListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Surah> getList() {
        return list;
    }

    public void setList(List<Surah> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.surah_layout, parent, false);
        return new ViewHolder(view, surahListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.surahNo.setText(String.valueOf(list.get(position).getNumber()));
        holder.englishName.setText(list.get(position).getEnglishName());
        holder.arabicName.setText(list.get(position).getName());
        holder.totalAya.setText("Aya : " + list.get(position).getNumberOfAyahs());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView surahNo;
        private final TextView arabicName;
        private final TextView englishName;
        private final TextView totalAya;
        private SurahListener surahListener;

        public ViewHolder(@NonNull View itemView, SurahListener surahListener) {
            super(itemView);

            surahNo = itemView.findViewById(R.id.surah_number);
            arabicName = itemView.findViewById(R.id.arabic_name);
            englishName = itemView.findViewById(R.id.english_name);
            totalAya = itemView.findViewById(R.id.total_aya);

            itemView.setOnClickListener(view -> surahListener.onSurahListener(getAdapterPosition()));
        }
    }
}

