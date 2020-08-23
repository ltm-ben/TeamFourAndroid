package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import iss.workshop.jsonparsingexample.Models.POItems;

public class PurchaseOrderCreateWithItems extends AppCompatActivity implements GetItemsListAccordingToSupplierData.OnDataAvailable{

    public static final String TAG = "ItemList";

    public String mURL = "http://192.119.86.65:90/PO/POItemApi";
    RecyclerView rView;
    Button mbtnSave;
    PurchaseOrderCreateWithItemAdapter adapter;

    PostJsonData mPostJsonData;
    private String mPostURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        setContentView(R.layout.activity_purchase_order_create_with_items);

        rView = (RecyclerView) findViewById(R.id.supplierCreateWithItemListRecyclerView);
        rView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PurchaseOrderCreateWithItemAdapter(this,new POItems());
        rView.setAdapter(adapter);


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

/*
    public void callAPI() throws JsonProcessingException {

        TestDTO testDTO = new TestDTO();
        testDTO.setId(30);
        testDTO.setName("Joe");

        POItems poItems = new POItems();
        poItems.setSupplierID(1);
        poItems.setPoStatus(PurchaseOrderStatus.Processing);
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(poItems);

        JSONObject demoObject = new JSONObject();
        try {

            demoObject.put("data", data);

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
    }*/
}