package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    EditText inputUsername;
    EditText inputPassword;
    EditText inputFirstName;
    EditText inputLastName;
    ImageView logoImageView;
    LinearLayout backgroundLayout;

    public void startHomepageActivity() {
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent);
    }

    // allows the sign up button to be clicked with the keyboard
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUpClicked(view);
        }

        return false;
    }

    // hides the keyboard when the background is clicked
    @Override
    public void onClick(View view) {
        if (view == logoImageView || view == backgroundLayout) {
            InputMethodManager inputMethodManager  = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    // switches to login
    public void signInLinkClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void login(EditText username, EditText password) {
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Log.i("Login", "Success");
                    startHomepageActivity();
                }
                else {
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // signs up user
    public void signUpClicked(View view) {
        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);

        if (inputUsername.getText().toString().matches("") || inputPassword.getText().toString().matches("")) {
            Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseUser user = new ParseUser();
            user.setUsername(inputUsername.getText().toString());
            user.setPassword(inputPassword.getText().toString());
            user.put("firstName", inputFirstName.getText().toString());
            user.put("lastName", inputLastName.getText().toString());


            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("SignUp", "Success");
                        login(inputUsername, inputPassword);
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputFirstName = (EditText) findViewById(R.id.input_first_name);
        inputLastName = (EditText) findViewById(R.id.input_last_name);

        inputPassword.setOnKeyListener(this);

        logoImageView = (ImageView) findViewById(R.id.imageView);
        backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);

        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
