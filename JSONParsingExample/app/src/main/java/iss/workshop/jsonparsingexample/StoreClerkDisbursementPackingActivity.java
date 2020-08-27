package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import iss.workshop.jsonparsingexample.Models.DTOs.DeptRequisitionDto;
import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDTO;
import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDetailDto;

public class StoreClerkDisbursementPackingActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete, PostJsonData.OnDownloadComplete {

    private String mGetDisbursementDetailURL;
    private String mSaveDisbursementDetailURL;
    private DisbursementDTO mDisbursement;
    private DisbursementPackingRecyclerViewAdapter mDisbursementPackingRecyclerViewAdapter;
    private Button mStoreClerkDisbursementDetailSubmitBtn;
    private EditText mStoreClerkDisbursementCollectionDate;
    private DatePickerDialog mDatePickerDialog;
    private int mYear;
    private int mMonth;
    private int mDay;

    private String mLogoutURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_packing);

        String disbursementId = "Disbursement Id not set";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            disbursementId = String.valueOf(extras.getInt("disbursementId"));
            mGetDisbursementDetailURL = "http://192.168.68.110/store/StoreClerkDisbursementDetailApi?id=" + disbursementId;
            mSaveDisbursementDetailURL = "http://192.168.68.110/store/StoreClerkSaveDisbursementDetailApi";
            mLogoutURL = "http://192.168.68.110/logout/logoutapi";
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.disbursementDetailRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDisbursementPackingRecyclerViewAdapter = new DisbursementPackingRecyclerViewAdapter(this, new ArrayList<DisbursementDetailDto>());
        recyclerView.setAdapter(mDisbursementPackingRecyclerViewAdapter);

        //initiate the date picker and a  button
        mStoreClerkDisbursementCollectionDate = findViewById(R.id.StoreClerkDisbursementCollectionDate);
        mStoreClerkDisbursementCollectionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                //current year, month,day fr Calendar
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                //datepicker dialog
                mDatePickerDialog = new DatePickerDialog(StoreClerkDisbursementPackingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        if (month<9  && day <9) {
                            mStoreClerkDisbursementCollectionDate.setText(year + "-" + "0" + (month + 1) + "-" +  "0"+day);
                        }
                        else if (month<9 && day>9)
                        {
                            mStoreClerkDisbursementCollectionDate.setText(year + "-"  + "0"+ (month + 1) + "-" +day);
                        }
                        else if (month>9 && day<9)
                        {
                            mStoreClerkDisbursementCollectionDate.setText(year + "-"  + (month + 1) + "-" +"0" + day);
                        }
                        else
                        {
                            mStoreClerkDisbursementCollectionDate.setText(year + "-"  + (month + 1) + "-" +day);
                        }
                    }
                },mYear, mMonth,mDay);
                mDatePickerDialog.show();
            }
        });

        mStoreClerkDisbursementDetailSubmitBtn = findViewById(R.id.storeClerkDisbursementDetailSubmitBtn);
        mStoreClerkDisbursementDetailSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DisbursementDTO data = new DisbursementDTO();
                data.setId(mDisbursement.getId());
                data.setDisbursementDate(mStoreClerkDisbursementCollectionDate.getText().toString());

                callPostApi(formatDataAsJSON(data));

                launchStoreClerkDisbursementListActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mGetDisbursementDetailURL);
    }

    private String formatDataAsJSON(DisbursementDTO input){

        final JSONObject root = new JSONObject();

        try {
            root.put("Id", input.getId());
            root.put("DisbursementDate", input.getDisbursementDate());

            return root.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void callPostApi(String json) {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(json);

        // create api in visual studio to receive requisition json string
        // add API URL to mSaveRequisitionDetailURL in onCreate
        postJsonData.execute(mSaveDisbursementDetailURL);
    }

    void launchStoreClerkDisbursementListActivity() {
        Intent intent = new Intent(this, StoreClerkDisbursementListActivity.class);
        startActivity(intent);
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            mDisbursement = new DisbursementDTO();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONObject jsonDisbursement = jsonData.getJSONObject("disbursement");

                mDisbursement.setId(jsonDisbursement.getInt("Id"));
                JSONArray itemsArray = jsonDisbursement.getJSONArray("DisbursementDetails");

                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(i);

                    DisbursementDetailDto disbursementDetail = new DisbursementDetailDto();
                    disbursementDetail.setItemCode(jsonDisbursementDetail.getString("StationeryCode"));
                    disbursementDetail.setItemName(jsonDisbursementDetail.getString("StationeryName"));
                    disbursementDetail.setQty(jsonDisbursementDetail.getInt("Qty"));
                    mDisbursement.getDisbursementDetails().add(disbursementDetail);
                }

                mDisbursementPackingRecyclerViewAdapter.loadNewData(mDisbursement.getDisbursementDetails());

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

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
            case R.id.Disbursement_Packing_item:
                intent = new Intent(this, StoreClerkDisbursementPackingActivity.class);
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
                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);

        return true;
    }
}