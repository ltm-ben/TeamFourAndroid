package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestPostJsonActivity extends AppCompatActivity implements PostJsonData.OnDownloadComplete {

    private String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_post_json);

        mURL = "http://192.168.68.110/store/PostTestObject";

        Button button = findViewById(R.id.postJsonBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPostApi();
            }
        });
    }

    private String formatDataAsJSON(){

        final JSONObject root = new JSONObject();

        try {
            root.put("Id", 15);
            root.put("Name", "John");

            return root.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void callPostApi() {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(formatDataAsJSON());
        postJsonData.execute(mURL);
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }
}