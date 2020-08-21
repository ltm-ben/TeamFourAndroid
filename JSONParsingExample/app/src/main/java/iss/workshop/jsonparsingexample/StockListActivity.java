package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Stock;


public class StockListActivity extends AppCompatActivity implements GetJsonData.OnDataAvailable {

    public static final String TAG = "MainActivity";
    private StockRecyclerViewAdapter mStockRecyclerViewAdapter;
    
    public String mURL = "http://192.119.86.65:8080/store/storeclerkstocklistapi";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStockRecyclerViewAdapter = new StockRecyclerViewAdapter(this, new ArrayList<Stock>());
        recyclerView.setAdapter(mStockRecyclerViewAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume starts");
        super.onResume();
        GetJsonData getJsonData = new GetJsonData(this);
        getJsonData.execute(mURL);
        Log.d(TAG, "onResume ends");
    }

    @Override
    public void onDataAvailable(List<Stock> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            mStockRecyclerViewAdapter.loadNewData(data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }
}