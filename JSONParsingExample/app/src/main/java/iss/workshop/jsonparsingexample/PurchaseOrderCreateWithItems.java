package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import iss.workshop.jsonparsingexample.Models.PO;
import iss.workshop.jsonparsingexample.Models.PODetails;
import iss.workshop.jsonparsingexample.Models.POItems;
import iss.workshop.jsonparsingexample.Models.PurchaseOrderStatus;
import iss.workshop.jsonparsingexample.Models.Stationary;

public class PurchaseOrderCreateWithItems extends AppCompatActivity implements GetItemsListAccordingToSupplierData.OnDataAvailable,PostJsonData.OnDownloadComplete{

    public static final String TAG = "ItemList";

    public String mURL = "http://172.20.10.4:80/PO/POItemApi";
    RecyclerView rView;
    Button mbtnSave;
    PurchaseOrderCreateWithItemAdapter adapter;

    PostJsonData mPostJsonData;
    private String mPostURL = "http://172.20.10.4:80//PO/POSave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_order_create_with_items);

        rView = (RecyclerView) findViewById(R.id.supplierCreateWithItemListRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PurchaseOrderCreateWithItemAdapter(this,new POItems());
        rView.setAdapter(adapter);

        mbtnSave = findViewById(R.id.btnPOSave);
        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //mPostJsonData = new PostJsonData(PurchaseOrderCreateWithItems.this);

                try {
                    callPostApi();
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
            if(status == DownloadStatus.OK && data != null) {
                adapter.loadNewData(data);
                Log.d(TAG, "onDataAvailable: in"+data.toString());
            } else {
                // download or processing failed
                Log.e(TAG, "onDataAvailable failed with status " + status);
            }

            Log.d(TAG, "onDataAvailable: ends");
        }


    public void callPostApi() throws JsonProcessingException {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(formatDataAsJSON());
        postJsonData.execute(mURL);
    }

    private String formatDataAsJSON() throws JsonProcessingException {

        POItems poItems = new POItems();
        poItems.setSupplierID(1);
        poItems.orderDate= "";
        poItems.poDetailsList = new ArrayList<>();

        PODetails pod = new PODetails();
        pod.setId(1);
        pod.setPredictionQty(10);
        pod.setStationaryDescription("Eraser");
        pod.setSupplierDetailsid(1);
        pod.setUnitPrice(100);

        poItems.poDetailsList.add(pod);

        poItems.setPoStatus(PurchaseOrderStatus.Processing);
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(poItems);

        Log.d(TAG, "sending Json : " + data);

        final JSONObject root = new JSONObject();

        try {
            root.put("input", data);

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
}