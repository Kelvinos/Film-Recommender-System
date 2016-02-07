package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    // Go back to the homepage
    public void HomePage(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}
