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


public class DeptHeadRequisitionListActivity extends AppCompatActivity implements GetRequisitionData.OnDataAvailable {

    private RequisitionRecyclerViewAdapter mRequisitionRecyclerViewAdapter;
    private RequisitionRecyclerViewAdapter.RecyclerViewClickListener mListener;
    private List<DeptRequisition> mData;

    public String mURL = "http://192.168.68.110/store/storeclerkrequisitionlistapi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_requisition_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.requisitionListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        mRequisitionRecyclerViewAdapter = new RequisitionRecyclerViewAdapter(this, new ArrayList<DeptRequisition>(), mListener);
        recyclerView.setAdapter(mRequisitionRecyclerViewAdapter);
    }

    private void setOnClickListener() {
        mListener = new RequisitionRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), StoreClerkRequisitionDetailActivity.class);
                intent.putExtra("requisitionId", mData.get(position).getId());
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRequisitionData getRequisitionData = new GetRequisitionData(this);
        getRequisitionData.execute(mURL);
    }

    @Override
    public void onDataAvailable(List<DeptRequisition> data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            mRequisitionRecyclerViewAdapter.loadNewData(data);
            mData = data;
        } else {
            // download or processing failed
        }
    }
}