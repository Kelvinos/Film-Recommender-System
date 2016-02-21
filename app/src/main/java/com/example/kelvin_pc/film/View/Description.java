package com.example.kelvin_pc.film.View;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kelvin_pc.film.Model.BaseActivity;
import com.example.kelvin_pc.film.R;

public class Description extends BaseActivity {

    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_description);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        getBundleData();
        updateDescription();
    }

    public void getBundleData() {
        Bundle b = getIntent().getExtras();
        description = b.getString("Description");
    }

    public void updateDescription() {
        TextView tv = (TextView) findViewById(R.id.text_description);
        tv.setText(description);
    }

}
