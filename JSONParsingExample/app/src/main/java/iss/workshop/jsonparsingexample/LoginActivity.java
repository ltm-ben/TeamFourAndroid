package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDTO;
import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDetailDto;

public class LoginActivity extends AppCompatActivity implements PostJsonData.OnDownloadComplete {

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

        // encode password using SHA256
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        if (username.equals("DipSA") && password.equals("DipSA")) {
            return true;
        }
        return false;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

//            mDisbursement = new DisbursementDTO();
//
//            try {
//                JSONObject jsonData = new JSONObject(data);
//                JSONObject jsonDisbursement = jsonData.getJSONObject("disbursement");
//
//                mDisbursement.setId(jsonDisbursement.getInt("Id"));
//                JSONArray itemsArray = jsonDisbursement.getJSONArray("DisbursementDetails");
//
//                for(int i=0; i<itemsArray.length(); i++) {
//                    JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(i);
//
//                    DisbursementDetailDto disbursementDetail = new DisbursementDetailDto();
//                    disbursementDetail.setItemCode(jsonDisbursementDetail.getString("StationeryCode"));
//                    disbursementDetail.setItemName(jsonDisbursementDetail.getString("StationeryName"));
//                    disbursementDetail.setQty(jsonDisbursementDetail.getInt("Qty"));
//                    mDisbursement.getDisbursementDetails().add(disbursementDetail);
//                }
//
//                mDisbursementPackingRecyclerViewAdapter.loadNewData(mDisbursement.getDisbursementDetails());
//
//            } catch(JSONException jsone) {
//                jsone.printStackTrace();
//                status = DownloadStatus.FAILED_OR_EMPTY;
//            }
        }
    }
}