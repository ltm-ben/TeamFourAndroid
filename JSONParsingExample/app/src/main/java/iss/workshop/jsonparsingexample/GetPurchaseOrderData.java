package iss.workshop.jsonparsingexample;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.PO;
import iss.workshop.jsonparsingexample.Models.RequisitionFulfillmentStatus;

public class GetPurchaseOrderData extends AsyncTask<String, Void, List<PO>> implements GetRawData.OnDownloadComplete {

    private List<PO> mPOList = null;
    private String mBaseURL;

    private final GetPurchaseOrderData.OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<PO> data, DownloadStatus status);
    }

    public GetPurchaseOrderData(GetPurchaseOrderData.OnDataAvailable callBack) {
        mCallBack = callBack;
    }

    void executeOnSameThread(String Url) {
        runningOnSameThread = true;
        String destinationUri = Url;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
    }

    @Override
    protected void onPostExecute(List<PO> poList) {

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mPOList, DownloadStatus.OK);
        }
    }

    @Override
    protected List<PO> doInBackground(String... params) {
        String destinationUri = params[0];

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return mPOList;
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {
            mPOList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("purchaseOrders");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonRequisition = itemsArray.getJSONObject(i);

                    int requisitionId = jsonRequisition.getInt("Id");
                    RequisitionFulfillmentStatus requisitionFulfillmentStatus = RequisitionFulfillmentStatus.valueOf(jsonRequisition.getInt("RequisitionFulfillmentStatus"));

                    PO po = new PO();
                    po.setId(jsonRequisition.getInt("Id"));
                    po.setOrderDate(jsonRequisition.getString("OrderDate"));
                    po.setSupplierName(jsonRequisition.getString("SupplierName"));

                    mPOList.add(po);
                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if(runningOnSameThread && mCallBack != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            mCallBack.onDataAvailable(mPOList, status);
        }

    }
}
