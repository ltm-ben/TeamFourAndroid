package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class StoreClerkRequisitionDetailActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete, PostJsonData.OnDownloadComplete {

    private String mGetRequisitionDetailURL;
    private String mSaveRequisitionDetailURL;
    private DeptRequisition mRequisition;
    private StoreClerkRequisitionDetailRecyclerViewAdapter mStoreClerkRequisitionDetailRecyclerViewAdapter;
    private Button mStoreClerkRequisitionDetailSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_requisition_detail);

        String requisitionId = "Requisition Id not set";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            requisitionId = String.valueOf(extras.getInt("requisitionId"));
            mGetRequisitionDetailURL = "http://192.168.68.110/store/storeclerkrequisitionfulfillmentapi?id=" + requisitionId;
            mSaveRequisitionDetailURL = "http://192.168.68.110/store/StoreClerkSaveRequisitionApi";
        }

        mStoreClerkRequisitionDetailSubmitBtn = findViewById(R.id.storeClerkRequisitionDetailSubmitBtn);
        mStoreClerkRequisitionDetailSubmitBtn.setOnClickListener(new View.OnClickListener() {
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

                    // launch store clerk requisition list activity
                    launchStoreClerkRequisitionListActivity();

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.requisitionDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStoreClerkRequisitionDetailRecyclerViewAdapter = new StoreClerkRequisitionDetailRecyclerViewAdapter(this, new DeptRequisition());
        recyclerView.setAdapter(mStoreClerkRequisitionDetailRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mGetRequisitionDetailURL);
    }

    DeptRequisitionDto deptRequisitionToDto(DeptRequisition input) {

        DeptRequisitionDto output = new DeptRequisitionDto();
        output.setId(input.getId());
        output.setRequisitionDetails(input.getRequisitionDetails());

        return output;
    }

    public void callPostApi(String json) {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(json);

        // create api in visual studio to receive requisition json string
        // add API URL to mSaveRequisitionDetailURL in onCreate
        postJsonData.execute(mSaveRequisitionDetailURL);
    }

    public void launchStoreClerkRequisitionListActivity() {
        Intent intent = new Intent(this, StoreClerkRequisitionListActivity.class);
        startActivity(intent);
    }

    @Override
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
                    requisitionDetail.setStockQty(jsonRequisitionDetail.getInt("StockQty"));
                    requisitionDetail.setCollectedQty(jsonRequisitionDetail.getInt("CollectedQty"));
                    mRequisition.getRequisitionDetails().add(requisitionDetail);
                }

                mStoreClerkRequisitionDetailRecyclerViewAdapter.loadNewData(mRequisition);

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            // Get response from API to show that the data was saved successfully

//            mRequisition = new DeptRequisition();
//
//            try {
//                JSONObject jsonData = new JSONObject(data);
//
//                int requisitionId = jsonData.getInt("requisitionId");
//                mRequisition.setId(requisitionId);
//
//                JSONArray itemsArray = jsonData.getJSONArray("requisitionDetails");
//
//                for(int i=0; i<itemsArray.length(); i++) {
//                    JSONObject jsonRequisitionDetail = itemsArray.getJSONObject(i);
//
//                    RequisitionDetail requisitionDetail = new RequisitionDetail();
//                    requisitionDetail.setId(jsonRequisitionDetail.getInt("Id"));
//                    requisitionDetail.setStationeryId(jsonRequisitionDetail.getInt("StationeryId"));
//                    requisitionDetail.setStationeryName(jsonRequisitionDetail.getString("StationeryName"));
//                    requisitionDetail.setQty(jsonRequisitionDetail.getInt("Qty"));
//                    mRequisition.getRequisitionDetails().add(requisitionDetail);
//                }
//
//                mStoreClerkRequisitionDetailRecyclerViewAdapter.loadNewData(mRequisition);
//            } catch(JSONException jsone) {
//                jsone.printStackTrace();
//                status = DownloadStatus.FAILED_OR_EMPTY;
//            }
        }
    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.storeclerk_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.Bar_Chart_List_item:
                intent = new Intent(this, BarChartActivity.class);
                startActivity(intent);
                return true;
            case R.id.Requisition_List_item:
                intent = new Intent(this, StoreClerkRequisitionListActivity.class);
                startActivity(intent);
                return true;
            case R.id.Disbursement_List_item:
                intent = new Intent(this, StoreClerkDisbursementListActivity.class);
                startActivity(intent);
                return true;
            case R.id.Stock_List_item:
                intent = new Intent(this, StockListActivity.class);
                startActivity(intent);
                return true;
            case R.id.PO_List_item:
                intent = new Intent(this,POList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}