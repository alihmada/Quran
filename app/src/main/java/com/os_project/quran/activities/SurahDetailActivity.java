package com.os_project.quran.activities;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.os_project.quran.R;
import com.os_project.quran.adapter.SurahDetailAdapter;
import com.os_project.quran.common.common;
import com.os_project.quran.model.SurahDetail;
import com.os_project.quran.viewmodel.SurahDetailViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class SurahDetailActivity extends AppCompatActivity {

    private final String urdu = "urdu_junagarhi";
    private final String english = "english_hilali_khan";
    private final String hindi = "hindi_omari";
    private final String french = "french_hameedullah";
    private final String turkish = "turkish_rwwad";
    private final String qariAB = "yasser_ad-dussary";
    private final String qariAC = "nasser_bin_ali_alqatami";
    private final String qariAD = "abdul_basit_murattal";
    private final String qariAE = "muhammad_ayyoob_hq";
    Handler handler = new Handler();
    SeekBar seekBar;
    TextView startTime, totalTime;
    ImageButton playButton;
    MediaPlayer mediaPlayer;
    private TextView surahName, surahType, surahTranslation;
    private int no;
    private RecyclerView recyclerView;
    private List<SurahDetail> list;
    private SurahDetailAdapter adapter;
    private EditText searchView;
    private ImageButton settingButton;
    private RadioGroup radioGroup, audioGroup;
    private RadioButton translationButton, qariSelectButton;
    private String lan;
    private String qr;
    private String str;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        init();

        recyclerView = findViewById(R.id.surah_detail);

        no = getIntent().getIntExtra(common.SURAH_NO, 0);

        surahName.setText(getIntent().getStringExtra(common.SURAH_NAME));

        surahType.setText(String.format("%s %d AYA", getIntent().getStringExtra(common.SURAH_TYPE),
                getIntent().getIntExtra(common.SURAH_TOTAL_AYA, 0)));

        surahTranslation.setText(getIntent().getStringExtra(common.SURAH_TRANSLATION));

        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        surahTranslation(english, no);

        try {
            listenAudio(qariAC);
        } catch (IOException e) {
            e.printStackTrace();
        }

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        settingButton.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SurahDetailActivity.this,
                    R.style.BottomSheetDialogTheme);

            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(R.layout.bottom_sheet_layout, findViewById(R.id.sheetContainer));
            view.findViewById(R.id.save_settings_button);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioGroup = view.findViewById(R.id.translation_group);
                    audioGroup = view.findViewById(R.id.audio_group);

                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    translationButton = view.findViewById(selectedId);
                    if (selectedId == -1) {
                        Toast.makeText(SurahDetailActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SurahDetailActivity.this, "Selected", Toast.LENGTH_SHORT).show();
                    }
                    if (translationButton.getText().toString().toLowerCase().trim().equals("english")) {
                        lan = english;
                    } else if (translationButton.getText().toString().toLowerCase().trim().equals("french")) {
                        lan = french;
                    } else if (translationButton.getText().toString().toLowerCase().trim().equals("hindi")) {
                        lan = hindi;
                        translationButton.setChecked(true);
                    } else if (translationButton.getText().toString().toLowerCase().trim().equals("turkish")) {
                        lan = turkish;
                    } else if (translationButton.getText().toString().toLowerCase().trim().equals("urdu")) {
                        lan = urdu;
                    }
                    try {
                        surahTranslation(lan, no);
                    } catch (Exception ignored) {
                    }

                    int id = audioGroup.getCheckedRadioButtonId();
                    qariSelectButton = view.findViewById(id);

                    if (qariSelectButton.getText().toString().toLowerCase().trim().equals("abdul basit")) {
                        qr = qariAD;
                    } else if (qariSelectButton.getText().toString().toLowerCase().trim().equals("muhammad ayyoob")) {
                        qr = qariAE;
                    } else if (qariSelectButton.getText().toString().toLowerCase().trim().equals("Nasser alqatami")) {
                        qr = qariAC;
                    } else if (qariSelectButton.getText().toString().toLowerCase().trim().equals("yasser Ad-dussary")) {
                        qr = qariAB;
                    }
                    selectedId = audioGroup.getCheckedRadioButtonId();
                    if (selectedId == -1) {
                        Toast.makeText(SurahDetailActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SurahDetailActivity.this, "Selected", Toast.LENGTH_SHORT).show();
                    }
                    bottomSheetDialog.dismiss();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    try {
                        listenAudio(qr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            bottomSheetDialog.setContentView(view);
            bottomSheetDialog.show();
        });

    }    private final Runnable updater = new Runnable() {

        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            startTime.setText(timeToMilliSecond(currentDuration));
            totalTime.setText(timeToMilliSecond(mediaPlayer.getDuration() - currentDuration));
        }
    };

    private void filter(String id) {
        ArrayList<SurahDetail> arrayList = new ArrayList<>();
        for (SurahDetail detail : list) {
            if (String.valueOf(detail.getId()).contains(id)) {
                arrayList.add(detail);
            }
        }
        adapter.filter(arrayList);
    }

    private void init() {
        surahName = findViewById(R.id.surah_name);
        surahType = findViewById(R.id.type);
        surahTranslation = findViewById(R.id.translation);
        searchView = findViewById(R.id.search_view);
        settingButton = findViewById(R.id.settings_button);
    }

    private void surahTranslation(String lan, int id) {
        if (list.size() > 0) {
            list.clear();
        }

        SurahDetailViewModel surahDetailViewModel = new ViewModelProvider(this).get(SurahDetailViewModel.class);
        surahDetailViewModel.getSurahDetail(lan, id).observe(this, surahDetailResponse -> {
            for (int i = 0; i < surahDetailResponse.getList().size(); i++) {
                list.add(new SurahDetail(surahDetailResponse.getList().get(i).getId(),
                        surahDetailResponse.getList().get(i).getSura(),
                        surahDetailResponse.getList().get(i).getAya(),
                        surahDetailResponse.getList().get(i).getArabic_text(),
                        surahDetailResponse.getList().get(i).getTranslation(),
                        surahDetailResponse.getList().get(i).getFootnotes()));
            }

            if (list.size() != 0) {
                adapter = new SurahDetailAdapter(this, list);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void listenAudio(String qari) throws IOException {
        playButton = findViewById(R.id.play_button);
        startTime = findViewById(R.id.starting_time);
        totalTime = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = new MediaPlayer();
        seekBar.setMax(100);
        playButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                handler.removeCallbacks(updater);
                mediaPlayer.pause();
                playButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            } else {
                mediaPlayer.start();
                playButton.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                updateSeekBar();
            }
        });

        preparedMediaPlayer(qari);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                startTime.setText(timeToMilliSecond((mediaPlayer.getCurrentPosition())));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar.setProgress(0);
                playButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                startTime.setText("00:00");
                totalTime.setText("00:00");
                mediaPlayer.reset();
                try {
                    preparedMediaPlayer(qari);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void preparedMediaPlayer(String qari) throws IOException {
        if (no < 10) {
            str = "00" + no;
        } else if (no < 100) {
            str = "0" + no;
        } else if (no >= 100) {
            str = String.valueOf(no);
        }

        mediaPlayer.setDataSource("https://download.quranicaudio.com/quran/" + qari + "/" + str.trim() + ".mp3");
        mediaPlayer.prepare();
        totalTime.setText(timeToMilliSecond((mediaPlayer.getDuration())));
    }

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    @SuppressLint("DefaultLocale")
    private String timeToMilliSecond(long milliSecond) {
        int seconds = (int) Math.floor(milliSecond / 1000);
        int minutes = (int) Math.floor(seconds / 60);
        int hours = (int) Math.floor(minutes / 60);

        seconds %= 60;
        minutes %= 60;

        if (hours <= 0 && minutes >= 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacks(updater);
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
        super.onPause();
    }


}