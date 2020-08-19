package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;


public class DeptHeadRequisitionListActivity extends AppCompatActivity implements GetRequisitionData.OnDataAvailable {

    private RequisitionRecyclerViewAdapter mRequisitionRecyclerViewAdapter;

    public String mURL = "http://192.168.68.110/dept/deptheadrequisitionlistapi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_requisition_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.requisitionListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRequisitionRecyclerViewAdapter = new RequisitionRecyclerViewAdapter(this, new ArrayList<DeptRequisition>());
        recyclerView.setAdapter(mRequisitionRecyclerViewAdapter);
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
        } else {
            // download or processing failed
        }
    }
}