package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import iss.workshop.jsonparsingexample.Models.TestDTO;

public class DemoActivity extends AppCompatActivity implements PostJsonData.OnDownloadComplete {

    PostJsonData mPostJsonData;
    private String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPostJsonData = new PostJsonData(this);
        mURL = "http://192.168.68.110/store/PostTestObject";

        callAPI();
    }

    public void callAPI() {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(30);
        testDTO.setName("Joe");

        JSONObject demoObject = new JSONObject();
        try {

            demoObject.put("testDTO", testDTO);

            mPostJsonData.loadJsonData(demoObject.toString());
            mPostJsonData.execute(mURL);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            // work with response data from POST Request API
            try {

                JSONObject jsonData = new JSONObject(data);
                JSONObject result = jsonData.getJSONObject("result");

                String text = result.getString("Message");
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}