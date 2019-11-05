package com.netology.androidcherepanov5_1_1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

class SampleAdapter extends BaseAdapter {

    private final List<Sample> samples;
    private final LayoutInflater inflater;
    private final Context context;
    private Button mBtnDel;

    SampleAdapter(Context context, List<Sample> samples) {

        this.context =context;
        this.samples = samples;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return samples.size();
    }

    @Override
    public Object getItem(int i) {
        return samples.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.sample_item_layout, viewGroup, false);
        }

        Sample sample = samples.get(i);

        ((TextView) view.findViewById(R.id.sample_title)).setText(sample.getTitle());
        ((TextView) view.findViewById(R.id.sample_category)).setText(sample.getCategory());

        mBtnDel = view.findViewById(R.id.btnDel);
        mBtnDel.setTag(i);
        mBtnDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                samples.remove((int) view.getTag());
                ((MainActivity) context).rewriteAllSamples();
                notifyDataSetChanged();
            }
        });
        return view;
    }
}