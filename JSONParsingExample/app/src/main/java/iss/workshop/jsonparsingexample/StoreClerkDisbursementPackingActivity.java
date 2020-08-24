package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iss.workshop.jsonparsingexample.Models.DTOs.DeptRequisitionDto;
import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDTO;
import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDetailDto;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.DisbursementDetail;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;

public class StoreClerkDisbursementPackingActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    private String mGetDisbursementDetailURL;
    //private String mSaveRequisitionDetailURL;
    private DisbursementDTO mDisbursement;
    private DisbursementPackingRecyclerViewAdapter mDisbursementPackingRecyclerViewAdapter;
    private Button mStoreClerkDisbursementDetailSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_packing);

        String disbursementId = "Disbursement Id not set";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            disbursementId = String.valueOf(extras.getInt("disbursementId"));
            mGetDisbursementDetailURL = "http://192.168.68.110/store/StoreClerkDisbursementDetailApi?id=" + disbursementId;
            //mSaveRequisitionDetailURL = "http://192.168.68.110/store/StoreClerkSaveRequisitionApi";
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.disbursementDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDisbursementPackingRecyclerViewAdapter = new DisbursementPackingRecyclerViewAdapter(this, new ArrayList<DisbursementDetailDto>());
        recyclerView.setAdapter(mDisbursementPackingRecyclerViewAdapter);

        mStoreClerkDisbursementDetailSubmitBtn = findViewById(R.id.storeClerkDisbursementDetailSubmitBtn);
        mStoreClerkDisbursementDetailSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send POST to API to submit collection date
                // status is automatically changed to pending disbursement in api method
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mGetDisbursementDetailURL);
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            mDisbursement = new DisbursementDTO();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONObject jsonDisbursement = jsonData.getJSONObject("disbursement");

                mDisbursement.setId(jsonDisbursement.getInt("Id"));
                JSONArray itemsArray = jsonDisbursement.getJSONArray("DisbursementDetails");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(i);

                    DisbursementDetailDto disbursementDetail = new DisbursementDetailDto();
                    disbursementDetail.setItemCode(jsonDisbursementDetail.getString("StationeryCode"));
                    disbursementDetail.setItemName(jsonDisbursementDetail.getString("StationeryName"));
                    disbursementDetail.setQty(jsonDisbursementDetail.getInt("Qty"));
                    mDisbursement.getDisbursementDetails().add(disbursementDetail);
                }

                mDisbursementPackingRecyclerViewAdapter.loadNewData(mDisbursement.getDisbursementDetails());

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}