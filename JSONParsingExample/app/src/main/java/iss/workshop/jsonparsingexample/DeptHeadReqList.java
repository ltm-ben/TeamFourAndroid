package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;

public class DeptHeadReqList extends AppCompatActivity implements GetRequisitionData.OnDataAvailable {

    private DeptHeadReqListRecyclerViewAdapter mDeptHeadReqListRecyclerViewAdapter;
    private DeptHeadReqListRecyclerViewAdapter.DeptHeadReqListRecyclerViewClickListener mListener;
    //data container for the recycler view. we are going to get data from json and put it in here.
    private List<DeptRequisition> reqList;

    public String mURL = "http://192.168.1.8:8080/Dept/DeptHeadRequisitionListApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_list);

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

}