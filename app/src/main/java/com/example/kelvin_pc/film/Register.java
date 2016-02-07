package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_register);
        super.onCreate(savedInstanceState);
    }

    public void Login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }


}
