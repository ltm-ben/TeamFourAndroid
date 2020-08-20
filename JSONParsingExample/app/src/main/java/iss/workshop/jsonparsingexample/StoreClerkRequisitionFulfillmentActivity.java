package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StoreClerkRequisitionFulfillmentActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_requisition_fulfillment);
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

    }
}