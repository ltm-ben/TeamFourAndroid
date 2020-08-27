package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import iss.workshop.jsonparsingexample.Models.DTOs.DeptRequisitionDto;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;

public class EmployeeRequisitionFormActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete, PostJsonData.OnDownloadComplete {

    private String mLogoutURL;
    private String mGetRequisitionURL;
    private String mSaveRequisitionURL;
    private DeptRequisition mRequisition;
    private Button mEmployeeRequisitionFormSubmitBtn;
    private EmployeeRequisitionFormRecyclerViewAdapter mEmployeeRequisitionFormRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_requisition_form);

        mLogoutURL = "https://logicuniversity.nusteamfour.online/logout/logoutapi";
        mGetRequisitionURL = "https://logicuniversity.nusteamfour.online/dept/EmployeeRequisitionFormApi";
        mSaveRequisitionURL = "https://logicuniversity.nusteamfour.online/dept/SaveRequisitionApi";

        mEmployeeRequisitionFormSubmitBtn = findViewById(R.id.EmployeeRequisitionFormSubmitBtn);
        mEmployeeRequisitionFormSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get requisition Id, list of item Id and disbursement Qty from mRequisition
                // put data into object
                // serialise the object into json
                ObjectMapper mapper = new ObjectMapper();

                try {

                    // transfer data from mRequisition into DeptRequisitionDto object
                    DeptRequisitionDto dto = deptRequisitionToDto(mRequisition);

                    // generate a json string from DeptRequisitionDto object
                    String json = mapper.writeValueAsString(dto);

                    // post the object to API endpoint
                    callPostApi(json);

                    // launch employee main activity
                    Intent intent = new Intent(getApplicationContext(), DeptEmployeeMainActivity.class);
                    startActivity(intent);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.EmployeeRequisitionFormRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEmployeeRequisitionFormRecyclerViewAdapter = new EmployeeRequisitionFormRecyclerViewAdapter(this, new DeptRequisition());
        recyclerView.setAdapter(mEmployeeRequisitionFormRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mGetRequisitionURL);
    }

    DeptRequisitionDto deptRequisitionToDto(DeptRequisition input) {

        DeptRequisitionDto output = new DeptRequisitionDto();
        output.setId(input.getId());
        output.setFormStatus(input.getFormStatus());
        output.setRequisitionDetails(input.getRequisitionDetails());

        return output;
    }

    public void callPostApi(String json) {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(json);

        // create api in visual studio to receive requisition json string
        // add API URL to mSaveRequisitionDetailURL in onCreate
        postJsonData.execute(mSaveRequisitionURL);
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            mRequisition = new DeptRequisition();

            try {
                JSONObject jsonData = new JSONObject(data);

                JSONObject jsonRequisition = jsonData.getJSONObject("deptRequisitionDto");
                mRequisition.setId(jsonRequisition.getInt("Id"));
                mRequisition.setFormStatus(jsonRequisition.getString("FormStatus"));

                JSONArray itemsArray = jsonRequisition.getJSONArray("RequisitionDetails");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonRequisitionDetail = itemsArray.getJSONObject(i);

                    RequisitionDetail requisitionDetail = new RequisitionDetail();
                    requisitionDetail.setStationeryId(jsonRequisitionDetail.getInt("StationeryId"));
                    requisitionDetail.setStationeryName(jsonRequisitionDetail.getString("StationeryName"));
                    requisitionDetail.setQty(jsonRequisitionDetail.getInt("Qty"));
                    mRequisition.getRequisitionDetails().add(requisitionDetail);
                }

                mEmployeeRequisitionFormRecyclerViewAdapter.loadNewData(mRequisition);

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.Employee_Requisition_Form_item:
                intent = new Intent(this, EmployeeRequisitionFormActivity.class);
                break;
            case R.id.Employee_Logout_item:
                GetRawData getRawData = new GetRawData(this);
                getRawData.execute(mLogoutURL);

                // clear shared preferences
                SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                finish();

                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);

        return true;
    }
}