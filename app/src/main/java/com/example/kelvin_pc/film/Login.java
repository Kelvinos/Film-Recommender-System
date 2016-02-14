package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends BaseActivity {

//    private LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_login);
        super.onCreate(savedInstanceState);
//        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
//        loginDataBaseAdapter.open();
    }

    // Go back to the homepage
    public void HomePage(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Log.d("mytag", "21212121");
        final EditText textUsername = (EditText) findViewById(R.id.text_username);
        final EditText textPassword = (EditText) findViewById(R.id.text_password);
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        DataReader ur = new DataReader();
        //String storedPass = loginDataBaseAdapter.getSinlgeEntry(username);
        String storedPass = ur.getPass(username);
        if (storedPass!=null && storedPass.equals(password)) {
            Intent intent = new Intent(this, Account.class);
            startActivity(intent);
        } else {
            final TextView textError = (TextView) findViewById(R.id.text_error);
            textError.setText("Invalid username or password ");
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        loginDataBaseAdapter.close();
    }

}
