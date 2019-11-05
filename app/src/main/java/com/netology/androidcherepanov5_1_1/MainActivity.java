package com.netology.androidcherepanov5_1_1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final List<Sample> mSamples = new ArrayList<>();
    private BaseAdapter mSampleAdapter;

    static final String EDIT_SAMPLE = "edit_sample";
    static final String SAMPLE_POSITION = "sample_position";
    static final String REQUEST_CODE = "request_code";
    static final int REQUEST_EDIT = 1;
    static final int REQUEST_ADD = 2;
    private final String SAMPLES_FILE_NAME = "base_samples.txt";
    private Button mNew_sample_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNew_sample_btn = findViewById(R.id.new_sample_btn);
        mNew_sample_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addSample = new Intent(MainActivity.this, SampleActivity.class);
                addSample.putExtra(REQUEST_CODE, REQUEST_ADD);
                startActivityForResult(addSample, REQUEST_ADD);
            }
        });

        readSavedSamples();

        mSampleAdapter = new SampleAdapter(MainActivity.this, mSamples);
        ListView mSamplesList = findViewById(R.id.samples_list);

        mSamplesList.setAdapter(mSampleAdapter);
        mSamplesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent editSample = new Intent(MainActivity.this, SampleActivity.class);
                editSample.putExtra(EDIT_SAMPLE, mSamples.get(i));
                editSample.putExtra(SAMPLE_POSITION, i);
                editSample.putExtra(REQUEST_CODE, REQUEST_EDIT);
                startActivityForResult(editSample, REQUEST_EDIT);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_EDIT) {
            int samplePosition = data.getIntExtra(SAMPLE_POSITION, -1);
            Sample sample = data.getParcelableExtra(EDIT_SAMPLE);
            if (samplePosition >= 0 && samplePosition < mSamples.size() && sample != null) {
                mSamples.remove(samplePosition);
                mSamples.add(samplePosition, sample);
                rewriteAllSamples();
            }
        }
        if (requestCode == REQUEST_ADD) {
            Sample sample = data.getParcelableExtra(EDIT_SAMPLE);
            if (sample != null) {
                mSamples.add(sample);
                saveSample(sample);
            }
        }
        mSampleAdapter.notifyDataSetChanged();
    }

    private void readSavedSamples() {
        mSamples.clear();
        if (isExternalStorageReadable()) {
            try {
                File samplesFile = new File(getExternalFilesDir(null), SAMPLES_FILE_NAME);
                FileReader fileReader = new FileReader(samplesFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = bufferedReader.readLine();

                if (line == null) throw new FileNotFoundException();

                while (line != null) {
                    String[] splittedLine = line.split(";");
                    Sample sample = new Sample(splittedLine[0], splittedLine[1]);
                    mSamples.add(sample);
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                fileReader.close();

            } catch (FileNotFoundException e) {

                Toast.makeText(getApplicationContext(), getString(R.string.not_file), Toast.LENGTH_LONG).show();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void saveSample(Sample sample) {

        if (isExternalStorageWritable()) {

            try {
                File samplesFile = new File(getExternalFilesDir(null), SAMPLES_FILE_NAME);
                FileWriter fileWriter = new FileWriter(samplesFile, true);
                fileWriter.write(sample.toString());
                fileWriter.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    void rewriteAllSamples() {

        if (isExternalStorageWritable()) {

            try {
                File samplesFile = new File(getExternalFilesDir(null), SAMPLES_FILE_NAME);
                FileWriter fileWriter = new FileWriter(samplesFile);

                for (Sample sample : mSamples) {
                    fileWriter.write(sample.toString());
                }
                fileWriter.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private boolean isExternalStorageWritable() {

        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);

    }

    private boolean isExternalStorageReadable() {

        String state = Environment.getExternalStorageState();
        return isExternalStorageWritable() || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

}