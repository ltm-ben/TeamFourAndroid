package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.RequisitionApprovalStatus;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;

public class StoreClerkRequisitionDetailActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    public String mURL;
    private DeptRequisition mRequisition = null;
    private StoreClerkRequisitionDetailRecyclerViewAdapter mStoreClerkRequisitionDetailRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_requisition_detail);

        String requisitionId = "Requisition Id not set";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // change to dept controller instead of store controller in future
            requisitionId = String.valueOf(extras.getInt("requisitionId"));
            mURL = "http://192.168.68.110/store/storeclerkrequisitionfulfillmentapi?id=" + requisitionId;
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.requisitionDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStoreClerkRequisitionDetailRecyclerViewAdapter = new StoreClerkRequisitionDetailRecyclerViewAdapter(this, new DeptRequisition());
        recyclerView.setAdapter(mStoreClerkRequisitionDetailRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mURL);
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

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

                mStoreClerkRequisitionDetailRecyclerViewAdapter.loadNewData(mRequisition);
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}