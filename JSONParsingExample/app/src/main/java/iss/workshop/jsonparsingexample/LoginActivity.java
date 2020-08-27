package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements PostJsonData.OnDownloadComplete {

    EditText mUsernameTxt;
    EditText mPasswordTxt;
    Button mLoginBtn;
    public String mURL = "http://192.168.68.110/login/loginapi";
    //SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if(pref.contains("deptRole")) {
//         redirectToActivity(mUserId, pref.getString("deptRole", ""));
//        }

        mUsernameTxt = (EditText) findViewById(R.id.txtUsername);
        mPasswordTxt = (EditText) findViewById(R.id.txtPassword);
        mLoginBtn = (Button) findViewById(R.id.btnLogin);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsernameTxt.getText().toString();
                String password = mPasswordTxt.getText().toString();

                logIn(username, password);

//                if(loginOk) {
//
//                    SharedPreferences.Editor editor = pref.edit();
//
//                    editor.putString("username", username);
//                    editor.putString("password", password);
//                    editor.commit();
//
//                    Toast.makeText(getApplicationContext(), "Login successful!",
//                            Toast.LENGTH_SHORT).show();
//
//                    startMainActivity();
//                }
            }
        });
    }

    private void logIn(String username, String password) {

        // encode password using SHA256

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytesPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String stringPassword = Base64.encodeToString(bytesPassword, Base64.DEFAULT);

            // Create JSON
            JSONObject root = new JSONObject();
            root.put("Username", username);
            root.put("Password", stringPassword);

            // call API
            PostJsonData postJsonData = new PostJsonData(this);
            postJsonData.loadJsonData(root.toString());
            postJsonData.execute(mURL);

        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        }

//        if (username.equals("DipSA") && password.equals("DipSA")) {
//            return true;
//        }
//        return false;
    }

    public void redirectToActivity(String deptRole) {

        Intent intent;

//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("username", mUsernameTxt.getText().toString());
//        editor.putString("deptRole", deptRole);
//        editor.putInt("userId", userId);
//        editor.commit();


        switch (deptRole) {
            case "DeptHead":
                intent = new Intent(this, DeptHeadMainActivity.class);
                break;
            case "Employee":
                intent = new Intent(this, DeptEmployeeMainActivity.class);
                break;
            case "DelegatedEmployee":
                intent = new Intent(this, DelegateEmployeeMainActivity.class);
                break;
            case "StoreClerk":
                intent = new Intent(this, StoreClerkMainActivity.class);
                break;
            default:
                intent = new Intent(this, LoginActivity.class);
                break;
        }

        startActivity(intent);
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            try {

                // post process json string
                data = data.trim();
                data = data.substring(1, data.length() - 1);
                data = data.replace("\\", "");

                JSONObject jsonData = new JSONObject(data);
                String response = jsonData.getString("response");

                if (response.equals("Successful")) {
                    String deptRole = jsonData.getString("deptRole");
                    redirectToActivity(deptRole);
                }
                else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

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