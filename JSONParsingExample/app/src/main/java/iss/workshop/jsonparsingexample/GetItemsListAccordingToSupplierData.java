package iss.workshop.jsonparsingexample;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.PO;
import iss.workshop.jsonparsingexample.Models.PODetails;
import iss.workshop.jsonparsingexample.Models.POItems;
import iss.workshop.jsonparsingexample.Models.PurchaseOrderStatus;
import iss.workshop.jsonparsingexample.Models.Stationary;

public class GetItemsListAccordingToSupplierData extends AsyncTask<String, Void, POItems> implements GetRawData.OnDownloadComplete {

    private POItems mPOitem = null;
    private String mBaseURL;

    private final GetItemsListAccordingToSupplierData.OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(POItems data, DownloadStatus status);
    }

    public GetItemsListAccordingToSupplierData(GetItemsListAccordingToSupplierData.OnDataAvailable callBack) {
        mCallBack = callBack;
    }

    void executeOnSameThread(String Url) {
        runningOnSameThread = true;
        String destinationUri = Url;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
    }

    @Override
    protected void onPostExecute(POItems poList) {

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mPOitem, DownloadStatus.OK);
        }
    }

    @Override
    protected POItems doInBackground(String... params) {
        String destinationUri = params[0];

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return mPOitem;
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {
            mPOitem = new POItems();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONObject jsonRequisition = jsonData.getJSONObject("items");

                    //JSONObject jsonRequisition = itemsArray.getJSONObject(1);

                    PurchaseOrderStatus poStatus = PurchaseOrderStatus.valueOf(jsonRequisition.getInt("POStatus"));


                    mPOitem.setOrderDate(jsonRequisition.getString("OrderDate"));
                    mPOitem.setPoStatus(poStatus);
                    mPOitem.setSupplierID(jsonRequisition.getInt("supplierID"));
                    // need to change

                    List<PODetails> pdList = new ArrayList<>();
                    JSONArray poDetailList =  jsonRequisition.getJSONArray("poDetailsList");

                    for(int j = 0; j < poDetailList.length(); j++){
                        JSONObject detailArray =poDetailList.getJSONObject(j);

                        PODetails pD = new PODetails();
                        pD.setPoId(detailArray.getInt("poID"));
                        pD.setSupplierDetailsid(detailArray.getInt("supplierDetailId"));
                        pD.setStationaryId(detailArray.getInt("stationeryId"));
                        pD.setStationaryDescription(detailArray.getString("stationeryDescription"));
                        pD.setPredictionQty(detailArray.getDouble("predictionQty"));
                        pD.setUnitPrice(detailArray.getInt("unitPrice"));
                        pdList.add(pD);

                    }
                    mPOitem.setPoDetailsList(pdList);
                    //mPOList.add(po);

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if(runningOnSameThread && mCallBack != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            mCallBack.onDataAvailable(mPOitem, status);
        }

    }
}
