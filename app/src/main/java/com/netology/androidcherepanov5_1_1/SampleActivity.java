package com.netology.androidcherepanov5_1_1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SampleActivity extends AppCompatActivity {

    private Sample sample;
    private int samplePosition;
    private EditText mTitle;
    private EditText mCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        Button mSaveBtn = findViewById(R.id.save_btn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sample.setTitle(mTitle.getText().toString());
                sample.setCategory(mCategory.getText().toString());

                Intent resultSample = new Intent();
                resultSample.putExtra(MainActivity.EDIT_SAMPLE, sample);
                resultSample.putExtra(MainActivity.SAMPLE_POSITION, samplePosition);
                setResult(RESULT_OK, resultSample);
                finish();
            }
        });


        mTitle = findViewById(R.id.title_sample);
        mCategory = findViewById(R.id.category_sample);

        initFields();

        Intent intent = getIntent();
        if (intent != null) {
            int request = intent.getIntExtra(MainActivity.REQUEST_CODE, -1);
            if (request == MainActivity.REQUEST_EDIT) {
                sample = getIntent().getParcelableExtra(MainActivity.EDIT_SAMPLE);
                samplePosition = getIntent().getIntExtra(MainActivity.SAMPLE_POSITION, -1);
            }
            if (request == MainActivity.REQUEST_ADD) {
                sample = new Sample("", "");
                samplePosition = -1;
            }
            mTitle.setText(sample.getTitle());
            mCategory.setText(sample.getCategory());

        } else finish();
    }

    private void initFields() {
        mTitle.setText("");
        mCategory.setText("");

    }
}
