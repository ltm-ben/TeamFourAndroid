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
    PurchaseOrderCreateWithItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_create_with_items);

        rView = findViewById(R.id.supplierCreateWithItemListRecyclerView);

        adapter = new PurchaseOrderCreateWithItemAdapter(this,new POItems());

        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

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
}