package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.Item;
import iss.workshop.jsonparsingexample.Models.PO;

public class POList extends AppCompatActivity implements GetPurchaseOrderData.OnDataAvailable{

    public static final String TAG = "POList";

    public String mURL = "http://192.119.86.65:90/PO/POListAPI";
    RecyclerView rView;
    Button mbtnCreate;
    POListAdpater poAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_o_list);

            rView = findViewById(R.id.poListRecyclerView);
        Date d = new Date();

        /*PO po1 = new PO();
        po1.setId(1);
        po1.setOrderDate("10/10/2020 12:00:00 AM");
        po1.setSupplierName("Fair Price");
        po1.setStatus("Processing");*/

        mbtnCreate = findViewById(R.id.btnPOCreate);
        mbtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(POList.this,PurchaseOrder.class);
                startActivity(intent);
            }
        });

            List<PO> poList = new ArrayList<>();

           // poList.add(po1);

             poAdapter = new POListAdpater(this,poList);
             poAdapter = new POListAdpater(this,new ArrayList<PO>());

            rView.setAdapter(poAdapter);
            rView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetPurchaseOrderData getPurchaseOrderdata = new GetPurchaseOrderData(this);
        getPurchaseOrderdata.execute(mURL);
        Log.d(TAG, "onResume ends");
    }


    @Override
    public void onDataAvailable(List<PO> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            poAdapter.loadNewData(data);
            Log.d(TAG, "onDataAvailable: in"+data.toString());
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }
}