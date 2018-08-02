package com.parse.starter;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    EditText inputUsername;
    EditText inputPassword;
    ImageView logoImageView;
    LinearLayout backgroundLayout;

    public void startHomepageActivity() {
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent);
    }

    // allows the login button to be clicked with the keyboard
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            loginClicked(view);
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

    // switches to sign up
    public void signUpLinkClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    // logs in user
    public void loginClicked(View view) {

        ParseUser.logInInBackground(inputUsername.getText().toString(), inputPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Log.i("Login", "Success");
                    startHomepageActivity();
                }
                else {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if there is a user logged in, directly go to the homepage
        if(ParseUser.getCurrentUser().getSessionToken() != null) {
            Log.i("User", "Current User Present");
            startHomepageActivity();
        } else {
            Log.i("User", "Current User NOT Present");
        }

        inputUsername = (EditText) findViewById(R.id.input_username);
        inputPassword = (EditText) findViewById(R.id.input_password);

        inputPassword.setOnKeyListener(this);

        logoImageView = (ImageView) findViewById(R.id.imageView);
        backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);

        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
