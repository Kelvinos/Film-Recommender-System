package com.example.kelvin_pc.film;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends BaseActivity {

//    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUp(R.layout.activity_register);
        super.onCreate(savedInstanceState);

//        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
//        loginDataBaseAdapter.open();
    }

    public void Confirm(View view) {

        final EditText textUsername = (EditText) findViewById(R.id.text_username);
        final EditText textPassword = (EditText) findViewById(R.id.text_password);
        final EditText textConfirmPassword = (EditText) findViewById(R.id.text_confirm_password);
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        String confirmPassword = textConfirmPassword.getText().toString();

        if (!password.equals(confirmPassword)) {
            final TextView textError = (TextView) findViewById(R.id.text_error);
            textError.setText("Password miss match");
        } else {
//            loginDataBaseAdapter.insertEntry(username, password);

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        loginDataBaseAdapter.close();
    }


}
