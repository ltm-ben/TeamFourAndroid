package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;


public class StoreClerkRequisitionListActivity extends AppCompatActivity implements GetRequisitionData.OnDataAvailable {

    private RequisitionRecyclerViewAdapter mRequisitionRecyclerViewAdapter;
    private RequisitionRecyclerViewAdapter.RecyclerViewClickListener mListener;
    private List<DeptRequisition> mData;

    public String mURL = "http://192.168.68.110/store/storeclerkrequisitionlistapi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_requisition_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.requisitionListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        mRequisitionRecyclerViewAdapter = new RequisitionRecyclerViewAdapter(this, new ArrayList<DeptRequisition>(), mListener);
        recyclerView.setAdapter(mRequisitionRecyclerViewAdapter);
    }

    private void setOnClickListener() {
        mListener = new RequisitionRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), StoreClerkRequisitionDetailActivity.class);
                intent.putExtra("requisitionId", mData.get(position).getId());
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRequisitionData getRequisitionData = new GetRequisitionData(this);
        getRequisitionData.execute(mURL);
    }

    @Override
    public void onDataAvailable(List<DeptRequisition> data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            mRequisitionRecyclerViewAdapter.loadNewData(data);
            mData = data;
        } else {
            // download or processing failed
        }
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
                startActivity(intent);
                return true;
            case R.id.Requisition_List_item:
                intent = new Intent(this, StoreClerkRequisitionListActivity.class);
                startActivity(intent);
                return true;
            case R.id.Disbursement_List_item:
                intent = new Intent(this, StoreClerkDisbursementListActivity.class);
                startActivity(intent);
                return true;
            case R.id.Disbursement_Packing_item:
                intent = new Intent(this, StoreClerkDisbursementPackingActivity.class);
                startActivity(intent);
                return true;
            case R.id.Stock_List_item:
                intent = new Intent(this, StockListActivity.class);
                startActivity(intent);
                return true;
            case R.id.PO_List_item:
                intent = new Intent(this,POList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}