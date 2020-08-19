package iss.workshop.jsonparsingexample;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Category;
import iss.workshop.jsonparsingexample.Models.Stationery;
import iss.workshop.jsonparsingexample.Models.Stock;
import iss.workshop.jsonparsingexample.Models.UOM;

public class GetJsonData extends AsyncTask<String, Void, List<Stock>> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetJsonData";

    private List<Stock> mStockList = null;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    interface OnDataAvailable {
        void onDataAvailable(List<Stock> data, DownloadStatus status);
    }

    public GetJsonData(OnDataAvailable callBack) {
        Log.d(TAG, "GetJsonData called");
        mCallBack = callBack;
    }

    void executeOnSameThread(String Url) {
        Log.d(TAG, "executeOnSameThread starts");
        runningOnSameThread = true;
        String destinationUri = Url;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread ends");
    }

    @Override
    protected void onPostExecute(List<Stock> stockList) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mStockList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected List<Stock> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");
        String destinationUri = params[0];

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        Log.d(TAG, "doInBackground ends");
        return mStockList;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);

        if(status == DownloadStatus.OK) {
            mStockList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("stocks");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonStock = itemsArray.getJSONObject(i);
                    JSONObject jsonStationery = jsonStock.getJSONObject("Stationery");

                    int stockId = jsonStock.getInt("Id");
                    int stockQty = jsonStock.getInt("Qty");
                    int stationeryId = jsonStationery.getInt("Id");
                    String itemNumber = jsonStationery.getString("ItemNumber");
                    Category category = Category.valueOf(jsonStationery.getInt("Category"));
                    String description = jsonStationery.getString("Description");
                    int reorderLevel = jsonStationery.getInt("ReorderLevel");
                    UOM uom = UOM.valueOf(jsonStationery.getInt("Uom"));

                    Stationery stationery = new Stationery();
                    stationery.setId(stationeryId);
                    stationery.setItemNumber(itemNumber);
                    stationery.setCategory(category);
                    stationery.setDescription(description);
                    stationery.setReorderLevel(reorderLevel);
                    stationery.setUom(uom);

                    Stock stock = new Stock();
                    stock.setId(stockId);
                    stock.setStationery(stationery);
                    stock.setQty(stockQty);

                    mStockList.add(stock);

                    Log.d(TAG, "onDownloadComplete: " + stock.toString());
                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if(runningOnSameThread && mCallBack != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            mCallBack.onDataAvailable(mStockList, status);
        }

        Log.d(TAG, "onDownloadComplete ends");
    }
}
