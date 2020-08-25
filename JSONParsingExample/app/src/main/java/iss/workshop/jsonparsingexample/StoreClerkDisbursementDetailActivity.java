package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import iss.workshop.jsonparsingexample.Models.DisbursementDetail;


public class StoreClerkDisbursementDetailActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    public String mURL;
    private ArrayList<DisbursementDetail> mDisbursementDetail = null;
    private StoreClerkDisbursementDetailRecyclerViewAdapter disRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_detail);

        mURL = "https://logicu.nusteamfour.online/store/storeclerkdisbursementdetailslistapi" ;


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.disbursementDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDisbursementDetail = new ArrayList<>();
        disRecyclerViewAdapter = new StoreClerkDisbursementDetailRecyclerViewAdapter(this, mDisbursementDetail);
        recyclerView.setAdapter(disRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mURL);
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            mDisbursementDetail = new ArrayList<DisbursementDetail>();

            try {
                JSONObject jsonData = new JSONObject(data);


                JSONArray itemsArray = jsonData.getJSONArray("requisitions");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(i);

                    DisbursementDetail disbursementDetail = new  DisbursementDetail();
                    disbursementDetail.setId(jsonDisbursementDetail.getInt("Id"));
                    disbursementDetail.setStationeryId(jsonDisbursementDetail.getInt("StationeryId"));
                    disbursementDetail.setStationeryName(jsonDisbursementDetail.getString("StationeryName"));
                    disbursementDetail.setQty(jsonDisbursementDetail.getInt("Qty"));
                    disbursementDetail.setA_Date((jsonDisbursementDetail.getString("A_Date").split("T"))[0]);
                    disbursementDetail.setDisbursementId(jsonDisbursementDetail.getInt("DisbursementId"));
                    disbursementDetail.setDeptName(jsonDisbursementDetail.getString("DeptName"));
                    mDisbursementDetail.add(disbursementDetail);
                }


                this.disRecyclerViewAdapter.loadNewData(mDisbursementDetail);
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item= menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               disRecyclerViewAdapter.getFilter().filter(newText);
               return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
