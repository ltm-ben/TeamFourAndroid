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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import iss.workshop.jsonparsingexample.Models.DTOs.DeptRequisitionDto;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.RequisitionApprovalStatus;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;

public class DeptHeadReqListDetails extends AppCompatActivity implements GetRawData.OnDownloadComplete, PostJsonData.OnDownloadComplete{

    private String mGetRequisitionDetailURL;
    private String mSaveRequisitionURL;
    //1
    private Button mSaveRequisitionBtn;
    //2
    private Spinner mReqApprovalStatus;
    //3
    private EditText mReason;
    //data container
    private DeptRequisition mRequisition = null;
    //4
    private DeptHeadReqListDetailsRecyclerViewAdapter mDeptHeadReqListDetailsRecyclerViewAdapter;
    private String mLogoutURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_list_details);

        mLogoutURL = "http://192.168.68.110/logout/logoutapi";

        String requisitionId = "Requisition Id not set";

        Bundle extras = getIntent().getExtras();
        /*getting requisition id from intent pass from previous activity*/
        if (extras != null) {
            requisitionId = String.valueOf(extras.getInt("requisitionId"));
            mGetRequisitionDetailURL = "http://192.168.68.110/Dept/DeptHeadRequisitionDetailsApi?id=" + requisitionId;
        }

        mSaveRequisitionURL = "http://192.168.68.110/Dept/PostReqApprovalStatus";

        RecyclerView recyclerView = findViewById(R.id.deptHeadReqDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDeptHeadReqListDetailsRecyclerViewAdapter = new DeptHeadReqListDetailsRecyclerViewAdapter(this, new DeptRequisition());
        recyclerView.setAdapter(mDeptHeadReqListDetailsRecyclerViewAdapter);

        mReqApprovalStatus = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        mReason = findViewById(R.id.EditTextFeedbackBody);

        mSaveRequisitionBtn = findViewById(R.id.deptHeadReqDetailSaveBtn);
        mSaveRequisitionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reasonInput = mReason.getText().toString();
                String approvalStatus = mReqApprovalStatus.getSelectedItem().toString();
                RequisitionApprovalStatus eapprovalstatus = RequisitionApprovalStatus.valueOf(approvalStatus.toUpperCase());
                mRequisition.setRequisitionApprovalStatus(eapprovalstatus);
                mRequisition.setReason(reasonInput);

                callPostApi();

                Intent intent = new Intent(v.getContext(), DeptHeadReqList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mGetRequisitionDetailURL);
    }

    private String formatDataAsJSON(){

        final JSONObject root = new JSONObject();

        try {
            root.put("Id", mRequisition.getId());
            root.put("RequisitionApprovalStatus", mRequisition.getRequisitionApprovalStatus().toString());
            root.put("Reason", mRequisition.getReason());

            return root.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void callPostApi() {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(formatDataAsJSON());

        postJsonData.execute(mSaveRequisitionURL);
    }

    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {
            mRequisition = new DeptRequisition();
            try {
                JSONObject jsonData = new JSONObject(data);
                int requisitionId = jsonData.getInt("requisitionId");
                mRequisition.setId(requisitionId);

                JSONArray itemsArray = jsonData.getJSONArray("requisitionDetails");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonRequisitionDetail = itemsArray.getJSONObject(i);
                    RequisitionDetail requisitionDetail = new RequisitionDetail();
                    requisitionDetail.setId(jsonRequisitionDetail.getInt("Id"));
                    requisitionDetail.setStationeryId(jsonRequisitionDetail.getInt("StationeryId"));
                    requisitionDetail.setStationeryName(jsonRequisitionDetail.getString("StationeryName"));
                    requisitionDetail.setQty(jsonRequisitionDetail.getInt("Qty"));
                    mRequisition.getRequisitionDetails().add(requisitionDetail);
                }
                mDeptHeadReqListDetailsRecyclerViewAdapter.loadNewData(mRequisition);
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK){

            try {

                JSONObject jsonData = new JSONObject(data);
                JSONObject result = jsonData.getJSONObject("result");

                String text = result.getString("Message");
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_LONG).show();

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.depthead_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.delegate_employee_item:
                intent = new Intent(this, DelegateEmployeeMainActivity.class);
                break;
            case R.id.approve_reject_requisitions_item:
                intent = new Intent(this, DeptHeadReqList.class);
                break;
            case R.id.Dept_Head_Logout_item:
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
