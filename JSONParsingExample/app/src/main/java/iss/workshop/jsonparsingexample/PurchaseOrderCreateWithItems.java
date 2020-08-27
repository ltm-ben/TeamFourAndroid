package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DTOs.DeptRequisitionDto;
import iss.workshop.jsonparsingexample.Models.PO;
import iss.workshop.jsonparsingexample.Models.PODetails;
import iss.workshop.jsonparsingexample.Models.POItems;
import iss.workshop.jsonparsingexample.Models.PurchaseOrderStatus;
//import iss.workshop.jsonparsingexample.Models.TestDTO;

public class PurchaseOrderCreateWithItems extends AppCompatActivity implements GetItemsListAccordingToSupplierData.OnDataAvailable,PostJsonData.OnDownloadComplete, GetRawData.OnDownloadComplete {

    public static final String TAG = "ItemList";
    private String mLogoutURL;

    public String mURL;
    RecyclerView rView;
    Button mbtnSave;
    PurchaseOrderCreateWithItemAdapter adapter;

    PostJsonData mPostJsonData;
    private String mPostURL = "http://192.168.68.110/PO/POSave";;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_create_with_items);

        mURL = "http://192.168.68.110/PO/POItemApi";
        mLogoutURL = "http://192.168.68.110/logout/logoutapi";

        rView = (RecyclerView) findViewById(R.id.supplierCreateWithItemListRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PurchaseOrderCreateWithItemAdapter(this,new POItems());
        rView.setAdapter(adapter);

        mbtnSave = findViewById(R.id.btnPOSave);
        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POItems items = adapter.getPoDetailsList();
                //TextView tv  = findViewById(R.id.supplierCreateItemHeader);
                //Log.d(TAG, "onClick: "+items.getPoDetailsList().get(1).getPredictionQty());
                //tv.setText("Qty  : "+items.getPoDetailsList().get(1).getQty());

                callPostApi(items);

                Intent intent = new Intent(PurchaseOrderCreateWithItems.this,POList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetItemsListAccordingToSupplierData getItemsListAccordingToSupplierData = new GetItemsListAccordingToSupplierData(this);
        getItemsListAccordingToSupplierData.execute(mURL);
        Log.d(TAG, "onResume ends");
    }

    @Override
    public void onDataAvailable(POItems data, DownloadStatus status) {
            Log.d(TAG, "onDataAvailable: starts");
            if(status == DownloadStatus.OK) {
                adapter.loadNewData(data);
                Log.d(TAG, "onDataAvailable: in"+data.toString());
            } else {

                Log.e(TAG, "onDataAvailable failed with status " + status);
            }

            Log.d(TAG, "onDataAvailable: ends");
        }


    public void callPostApi(POItems poItems) {

        PostJsonData postJsonData = new PostJsonData(this);

        Log.d(TAG, "In CallPost API : ");
        postJsonData.loadJsonData(formatDataAsJSON(poItems));
        Log.d(TAG, "pass data : " + formatDataAsJSON(poItems));

        Log.d(TAG,"Before execute.");
        postJsonData.execute(mPostURL);
    }

    private String formatDataAsJSON(POItems poItems){
        JSONArray poList = new JSONArray();
        String json = null;
        List<String> list = new ArrayList<>();
        for(int i= 0; i< poItems.getPoDetailsList().size(); i++){
           JSONObject obj = new JSONObject();


            ObjectMapper mapper = new ObjectMapper();

            try {
                json = mapper.writeValueAsString(poItems.getPoDetailsList().get(i));
                Log.d(TAG, "Post Json object Details :"+ json);
                poList.put(json);
                list.add(json);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        final JSONObject root = new JSONObject();

        try {
            Log.d(TAG, "In format API : ");
            root.put("supplierID", poItems.getSupplierID());
            root.put("OrderDate",poItems.getOrderDate());
            //root.put("POStatus", poItems.getPoStatus());
           // root.put("supplierID", poItems.getSupplierID());
            root.put("poDetailsList", toJson(poItems.getPoDetailsList()));
            Log.d(TAG, "In format API before return: ");
            return root.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {

            // work with response data from POST Request API
            try {

                JSONObject jsonData = new JSONObject(data);
                JSONObject result = jsonData.getJSONObject("result");

                String text = result.getString("Message");
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
    public static JSONArray toJson(List<PODetails> detailList) throws JSONException {
        JSONArray jArr = new JSONArray();
        JSONObject jo;
        for (PODetails d : detailList) {
            jo = new JSONObject();
            jo.put("Id", d.getId());
            jo.put("poID", d.getPoId());
            jo.put("supplierDetailId", d.getSupplierDetailsid());
            jo.put("stationeryDescription", d.getStationaryDescription());
            jo.put("stationeryId", d.getStationaryId());
            jo.put("unitPrice", d.getUnitPrice());
            jo.put("predictionQty", d.getPredictionQty());

            jo.put("Qty", d.getQty());
            jArr.put(jo);
        }
        return jArr;
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

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
                break;
            case R.id.Requisition_List_item:
                intent = new Intent(this, StoreClerkRequisitionListActivity.class);
                break;
            case R.id.Disbursement_List_item:
                intent = new Intent(this, StoreClerkDisbursementListActivity.class);
                break;
            case R.id.Stock_List_item:
                intent = new Intent(this, StockListActivity.class);
                break;
            case R.id.PO_List_item:
                intent = new Intent(this,POList.class);
                break;
            case R.id.Store_Clerk_Logout_item:
                GetRawData getRawData = new GetRawData(this);
                getRawData.execute(mLogoutURL);

                // clear shared preferences
                SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                finish();

                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);

        return true;
    }
}