package com.example.kelvin_pc.film.Model;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.example.kelvin_pc.film.R;
import com.example.kelvin_pc.film.View.Rated;
import com.example.kelvin_pc.film.View.Recommendations;
import com.example.kelvin_pc.film.View.Search;
import com.example.kelvin_pc.film.View.Settings;

public class BaseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private int view;

    public void setUp(int view) {
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // Stop keyboard auto popping up at the start
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rated:
                rated();
                return true;
            case R.id.action_recommendations:
                recommendations();
                return true;
            case R.id.action_settings:
                Settings();
                return true;
            case R.id.action_home:
                Home();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void recommendations() {
        Intent intent = new Intent(this, Recommendations.class);
        startActivity(intent);
    }

    public void rated() {
        Intent intent = new Intent(this, Rated.class);
        startActivity(intent);
    }

    public void Home() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void Settings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
