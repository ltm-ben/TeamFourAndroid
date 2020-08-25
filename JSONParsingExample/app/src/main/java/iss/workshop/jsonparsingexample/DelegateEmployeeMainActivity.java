package iss.workshop.jsonparsingexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DelegatedEmployee;
import iss.workshop.jsonparsingexample.Models.DelegationStatus;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.DeptRole;
import iss.workshop.jsonparsingexample.Models.Employee;

public class DelegateEmployeeMainActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete{

    public static final String TAG = "DelegateEmployeeMainActivityList";
    RecyclerView deView;
    Button DelegateEmployeeCreate;
    DelegateEmployeeMainActivityListAdapter deempAdapter;

    //  private DelegateEmployeeRecyclerViewAdapter mDelegateEmployeeRecyclerViewAdapter;
    //  private DelegateEmployeeRecyclerViewAdapter.RecyclerViewClickListener mListener;
    private List<DelegatedEmployee> mDelegateEmployees;

    public String mURL = "https://logicu.nusteamfour.online/Delegate/DelegatedEmployeeListApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_employee_main);

        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.delegateEmployeeListRecyclerView);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //mDelegateEmployeeRecyclerViewAdapter = new DelegateEmployeeRecyclerViewAdapter(this, new ArrayList<DelegateEmployee>(), mListener);
        //recyclerView.setAdapter(mDelegateEmployeeRecyclerViewAdapter);

        //deView= findViewById(R.id.delempListRecyclerView);


        DelegateEmployeeCreate = findViewById(R.id.btnDelegateEmployeeCreate);
        DelegateEmployeeCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DelegateEmployeeMainActivity.this, CreateNewDelegateEmployee.class);
                startActivity(intent);
            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mURL);
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if (status == DownloadStatus.OK) {
            mDelegateEmployees = new ArrayList<DelegatedEmployee>();

            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = jsonObject.getJSONArray("delegatedEmployees");
                for(int i = 0 ; i< jsonArray.length(); i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    DelegatedEmployee delegateEmployee = new DelegatedEmployee();

                    delegateEmployee.setId(jobj.getInt("id"));
                    delegateEmployee.setName(jobj.getString("name"));
                    delegateEmployee.setStartDate(jobj.getString("startDate"));
                    delegateEmployee.setEndDate(jobj.getString("endDate"));
                    delegateEmployee.setDelegationStatus(DelegationStatus.valueOf(jobj.getInt("status")));

                    mDelegateEmployees.add(delegateEmployee);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}