package com.example.kelvin_pc.film.View;

import android.os.Bundle;
import android.widget.TextView;

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
        generateDescription();
    }

    public void getBundleData() {
        Bundle b = getIntent().getExtras();
        description = b.getString("Description");
    }

    public void generateDescription() {
        TextView t = textView(R.id.text_description);
        t.setText(description);
    }

}
