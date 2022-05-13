package com.os_project.quran.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.os_project.quran.R;

import java.util.Objects;

public class rosaryActivity extends AppCompatActivity {

    TextView txtCounter;
    Button btnCounter;
    int txt_counter = 0, btn_counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rosary);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        txtCounter = findViewById(R.id.counterText);
        btnCounter = findViewById(R.id.counterButton);


        btnCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_counter++;
                btn_counter++;
                txtCounter.setText(String.valueOf(txt_counter));
                btnCounter.setText(String.valueOf(btn_counter + "/" + "100"));
                if (btn_counter >= 100)
                    btn_counter = 0;
            }
        });
    }
}