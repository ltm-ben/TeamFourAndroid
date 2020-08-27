package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDTO;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.DisbursementStatus;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;
import iss.workshop.jsonparsingexample.Models.RequisitionFulfillmentStatus;

public class StoreClerkDisbursementListActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    private DisbursementListRecyclerViewAdapter mDisbursementListRecyclerViewAdapter;
    private DisbursementListRecyclerViewAdapter.RecyclerViewClickListener mListener;
    private List<DisbursementDTO> mData;

    private String mLogoutURL;
    public String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_list);

        mURL = "https://logicuniversity.nusteamfour.online/store/StoreClerkDisbursementListApi";
        mLogoutURL = "https://logicuniversity.nusteamfour.online/logout/logoutapi";

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.StoreClerkDisbursementListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setOnClickListener();
        mDisbursementListRecyclerViewAdapter = new DisbursementListRecyclerViewAdapter(this, new ArrayList<DisbursementDTO>(), mListener);
        recyclerView.setAdapter(mDisbursementListRecyclerViewAdapter);
    }

    private void setOnClickListener() {
        mListener = new DisbursementListRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), StoreClerkDisbursementPackingActivity.class);
                intent.putExtra("disbursementId", mData.get(position).getId());
                startActivity(intent);
            }
        };
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

            mData = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("disbursementList");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonDisbursement = itemsArray.getJSONObject(i);

                    DisbursementDTO disbursementDTO = new DisbursementDTO();
                    disbursementDTO.setId(jsonDisbursement.getInt("Id"));
                    disbursementDTO.setDisbursementStatus(DisbursementStatus.valueOf(jsonDisbursement.getInt("DisbursementStatus")));
                    mData.add(disbursementDTO);
                }

                mDisbursementListRecyclerViewAdapter.loadNewData(mData);

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
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