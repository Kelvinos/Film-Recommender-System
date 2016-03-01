package com.example.kelvin_pc.film.View;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kelvin_pc.film.R;

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
                settings();
                return true;
            case R.id.action_home:
                home();
                return true;
            case R.id.action_film:
                filmList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filmList() {
        Intent intent = new Intent(this, Film_List.class);
        startActivity(intent);
    }

    public void recommendations() {
        Intent intent = new Intent(this, Recommendations.class);
        startActivity(intent);
    }

    public void rated() {
        Intent intent = new Intent(this, Rated.class);
        startActivity(intent);
    }

    public void home() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void settings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public RadioButton radioButton(int id) { return (RadioButton) findViewById(id); }

    public EditText editText(int id) {
        return (EditText) findViewById(id);
    }
    public TextView textView(int id) { return (TextView) findViewById(id); }
    public LinearLayout linearLayout(int id) {
        return (LinearLayout) findViewById(id);
    }
    public ImageView imageView(int id) {
        return (ImageView) findViewById(id);
    }
    public ImageButton imageButton(int id) {
        return (ImageButton) findViewById(id);
    }
    public ListView listView(int id) { return (ListView) findViewById(id); }

    public Spinner spinner(int id, int arr, int sel) {
        android.widget.Spinner spinner = (android.widget.Spinner) findViewById(id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arr, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(sel);
        return spinner;
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Search.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showAlert(String title, String msg, Context c) {
        AlertDialog alert = new AlertDialog.Builder(c).create();
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alert.show();
        alert.setCancelable(false);
    }

    public void startProgressBar(ProgressDialog p, String title) {
        p.setTitle(title);
        p.show();
    }

    public void stopProgressBar(ProgressDialog p) {
        p.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> p) {

    }

}
