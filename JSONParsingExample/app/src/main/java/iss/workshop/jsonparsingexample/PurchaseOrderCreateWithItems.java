package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import iss.workshop.jsonparsingexample.Models.PO;
import iss.workshop.jsonparsingexample.Models.POItems;
import iss.workshop.jsonparsingexample.Models.PurchaseOrderStatus;
//import iss.workshop.jsonparsingexample.Models.TestDTO;

public class PurchaseOrderCreateWithItems extends AppCompatActivity implements GetItemsListAccordingToSupplierData.OnDataAvailable,PostJsonData.OnDownloadComplete{

    public static final String TAG = "ItemList";

    public String mURL = "http://192.168.68.110/PO/POItemApi";
    RecyclerView rView;
    Button mbtnSave;
    PurchaseOrderCreateWithItemAdapter adapter;

    PostJsonData mPostJsonData;
    private String mPostURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_order_create_with_items);

        rView = (RecyclerView) findViewById(R.id.supplierCreateWithItemListRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PurchaseOrderCreateWithItemAdapter(this,new POItems());
        rView.setAdapter(adapter);

        /*mbtnSave = findViewById(R.id.btnPOSave);
        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPostJsonData = new PostJsonData(PurchaseOrderCreateWithItems.this);
                mURL = "http://192.119.86.65:90/PO/POSave";

                try {
                    callAPI();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });*/
        mbtnSave = findViewById(R.id.btnPOSave);
        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POItems items = adapter.getPoDetailsList();
                TextView tv  = findViewById(R.id.supplierCreateItemHeader);
                Log.d(TAG, "onClick: "+items.getPoDetailsList().get(1).getPredictionQty());
                tv.setText("Qty  : "+items.getPoDetailsList().get(1).getQty());

                mPostJsonData = new PostJsonData(PurchaseOrderCreateWithItems.this);
                mURL = "http://192.168.68.110/PO/POSave";

                try {
                    callAPI(items);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
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
                // download or processing failed
                Log.e(TAG, "onDataAvailable failed with status " + status);
            }

            Log.d(TAG, "onDataAvailable: ends");
        }


    public void callAPI(POItems items) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(items);

        JSONObject demoObject = new JSONObject();
        try {

            demoObject.put("input", data);

            mPostJsonData.loadJsonData(data);
            mPostJsonData.execute(mURL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}