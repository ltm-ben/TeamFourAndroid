package iss.workshop.jsonparsingexample;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.RequisitionApprovalStatus;
import iss.workshop.jsonparsingexample.Models.RequisitionFulfillmentStatus;

public class GetRequisitionData extends AsyncTask<String, Void, List<DeptRequisition>> implements GetRawData.OnDownloadComplete {

    private List<DeptRequisition> mRequisitionList = null;
    private String mBaseURL;

    private final GetRequisitionData.OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<DeptRequisition> data, DownloadStatus status);
    }

    public GetRequisitionData(GetRequisitionData.OnDataAvailable callBack) {
        mCallBack = callBack;
    }

    void executeOnSameThread(String Url) {
        runningOnSameThread = true;
        String destinationUri = Url;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
    }

    @Override
    protected void onPostExecute(List<DeptRequisition> stockList) {

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mRequisitionList, DownloadStatus.OK);
        }
    }

    @Override
    protected List<DeptRequisition> doInBackground(String... params) {
        String destinationUri = params[0];

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return mRequisitionList;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {
            mRequisitionList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("requisitions");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonRequisition = itemsArray.getJSONObject(i);

                    int requisitionId = jsonRequisition.getInt("Id");
                    RequisitionFulfillmentStatus requisitionFulfillmentStatus = RequisitionFulfillmentStatus.valueOf(jsonRequisition.getInt("RequisitionFulfillmentStatus"));

                    DeptRequisition deptRequisition = new DeptRequisition();
                    deptRequisition.setId(requisitionId);
                    deptRequisition.setRequisitionFulfillmentStatus(requisitionFulfillmentStatus);

                    mRequisitionList.add(deptRequisition);
                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if(runningOnSameThread && mCallBack != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            mCallBack.onDataAvailable(mRequisitionList, status);
        }

    }
}
