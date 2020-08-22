package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText mUsernameTxt;
    EditText mPasswordTxt;
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);

        if(pref.contains("username") && pref.contains("password")) {

            boolean loginOk = logIn(pref.getString("username", ""),
                    pref.getString("password", ""));

            if(loginOk) {
                startMainActivity();
            }
        }

        mUsernameTxt = (EditText) findViewById(R.id.txtUsername);
        mPasswordTxt = (EditText) findViewById(R.id.txtPassword);
        mLoginBtn = (Button) findViewById(R.id.btnLogin);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsernameTxt.getText().toString();
                String password = mPasswordTxt.getText().toString();

                boolean loginOk = logIn(username, password);

                if(loginOk) {

                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "Login successful!",
                            Toast.LENGTH_SHORT).show();

                    startMainActivity();
                }
            }
        });
    }

    private boolean logIn(String username, String password) {
        if (username.equals("DipSA") && password.equals("DipSA")) {
            return true;
        }
        return false;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}