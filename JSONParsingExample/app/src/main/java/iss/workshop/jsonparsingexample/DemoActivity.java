package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import iss.workshop.jsonparsingexample.Models.DTOs.AnotherTestDto;
import iss.workshop.jsonparsingexample.Models.DTOs.DeptRequisitionDto;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.TestDTO;

public class DemoActivity extends AppCompatActivity implements PostJsonData.OnDownloadComplete {

    PostJsonData mPostJsonData;
    private String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPostJsonData = new PostJsonData(this);
//        mURL = "http://192.168.68.110/store/PostTestObject";
        mURL = "http://192.168.68.110/store/StoreClerkSaveRequisitionApi";

        callAPI();
    }

    public void callAPI() {

        try {

            DeptRequisitionDto testDto = new DeptRequisitionDto();
            testDto.setId(15);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(testDto);

            mPostJsonData.loadJsonData(json);
            mPostJsonData.execute(mURL);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

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