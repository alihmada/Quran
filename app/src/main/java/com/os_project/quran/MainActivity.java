package com.os_project.quran;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.os_project.quran.activities.SurahDetailActivity;
import com.os_project.quran.activities.rosaryActivity;
import com.os_project.quran.adapter.SurahAdapter;
import com.os_project.quran.common.common;
import com.os_project.quran.listener.SurahListener;
import com.os_project.quran.model.Surah;
import com.os_project.quran.viewmodel.SurahViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SurahListener {

    private RecyclerView recyclerView;
    private SurahAdapter surahAdapter;
    private List<Surah> list;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                , WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        recyclerView = findViewById(R.id.surah);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        SurahViewModel surahViewModel = new ViewModelProvider(this).get(SurahViewModel.class);

        surahViewModel.getSurah().observe(this, surahResponse -> {
            for (int i = 0; i < surahResponse.getList().size(); i++) {
                list.add(new Surah(surahResponse.getList().get(i).getNumber(),
                        String.valueOf(surahResponse.getList().get(i).getName()),
                        String.valueOf(surahResponse.getList().get(i).getEnglishName()),
                        String.valueOf(surahResponse.getList().get(i).getEnglishNameTranslation()),
                        surahResponse.getList().get(i).getNumberOfAyahs(),
                        String.valueOf(surahResponse.getList().get(i).getRevelationType())));
            }
            if (list.size() != 0) {
                surahAdapter = new SurahAdapter(this, list, this);
                recyclerView.setAdapter(surahAdapter);
                surahAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onSurahListener(int position) {
        Intent intent = new Intent(MainActivity.this, SurahDetailActivity.class);
        intent.putExtra(common.SURAH_NO, list.get(position).getNumber());
        intent.putExtra(common.SURAH_NAME, list.get(position).getName());
        intent.putExtra(common.SURAH_TOTAL_AYA, list.get(position).getNumberOfAyahs());
        intent.putExtra(common.SURAH_TYPE, list.get(position).getRevelationType());
        intent.putExtra(common.SURAH_TRANSLATION, list.get(position).getEnglishNameTranslation());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.rosary) {
            Intent intent = new Intent(this, rosaryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}