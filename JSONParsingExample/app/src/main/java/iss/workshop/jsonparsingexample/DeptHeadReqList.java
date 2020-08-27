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

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;

public class DeptHeadReqList extends AppCompatActivity implements GetRequisitionData.OnDataAvailable, GetRawData.OnDownloadComplete {


    private DeptHeadReqListRecyclerViewAdapter mDeptHeadReqListRecyclerViewAdapter;
    private DeptHeadReqListRecyclerViewAdapter.DeptHeadReqListRecyclerViewClickListener mListener;
    //data container for the recycler view. we are going to get data from json and put it in here.
    private List<DeptRequisition> reqList;

    private String mLogoutURL;
    private String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_list);

        mURL = "http://192.168.68.110/Dept/DeptHeadRequisitionListApi";
        mLogoutURL = "http://192.168.68.110/logout/logoutapi";

        //allocating the id of the recyclerview for deptheadreqlist.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.deptHeadReqListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        //creating adapter
        mDeptHeadReqListRecyclerViewAdapter = new DeptHeadReqListRecyclerViewAdapter(this, new ArrayList<DeptRequisition>(), mListener);
        recyclerView.setAdapter(mDeptHeadReqListRecyclerViewAdapter);
    }

    private void setOnClickListener() {
        mListener = new DeptHeadReqListRecyclerViewAdapter.DeptHeadReqListRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), DeptHeadReqListDetails.class);
                //transferring requisition id over for getting data at DeptHeadReqDetails activity.
                intent.putExtra("requisitionId", reqList.get(position).getId());
                startActivity(intent);
            }
        };
    }

    protected void onResume() {
        super.onResume();
        GetRequisitionData getRequisitionData = new GetRequisitionData(this);
        getRequisitionData.execute(mURL);
    }

    //Setting data into reqList in line 36
    @Override
    public void onDataAvailable(List<DeptRequisition> data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            mDeptHeadReqListRecyclerViewAdapter.loadNewData(data);
            reqList = data;
        } else {
            // download or processing failed
        }
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

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