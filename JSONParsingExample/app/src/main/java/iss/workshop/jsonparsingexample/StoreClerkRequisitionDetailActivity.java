package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StoreClerkRequisitionDetailActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    public String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_requisition_detail);

        String requisitionId = "Requisition Id not set";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            // change to dept controller instead of store controller in future
            mURL = "http://192.168.68.110/store/storeclerkrequisitionfulfillmentapi?id=" + requisitionId;
        }
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

            try {
                JSONObject jsonData = new JSONObject(data);
                int requisitionId = jsonData.getInt("requisitionId");
                JSONArray itemsArray = jsonData.getJSONArray("requisitionDetails");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonRequisition = itemsArray.getJSONObject(i);

//                    int requisitionId = jsonRequisition.getInt("Id");
//                    RequisitionApprovalStatus requisitionApprovalStatus = RequisitionApprovalStatus.valueOf(jsonRequisition.getInt("RequisitionApprovalStatus"));
//
//                    DeptRequisition deptRequisition = new DeptRequisition();
//                    deptRequisition.setId(requisitionId);
//                    deptRequisition.setRequisitionApprovalStatus(requisitionApprovalStatus);
                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}