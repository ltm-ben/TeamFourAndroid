package iss.workshop.jsonparsingexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class DelegateEmployeeMainActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete,View.OnClickListener,PostJsonData.OnDownloadComplete{

    public static final String TAG = "DelegateEmployeeMainActivityList";
    RecyclerView deView;
    Button DelegateEmployeeCreate;
    DelegateEmployeeMainActivityListAdapter deEmpAdapter;
    Button cancel;
    String mURLsend = "http://192.168.68.110/Delegate/CancelByAndroid";
    PostJsonData mPostJsonData ;
    private List<DelegatedEmployee> mDelegateEmployees;

    private String mLogoutURL;
    public String mURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_employee_main);

        mLogoutURL = "http://192.168.68.110/logout/logoutapi";
        mURL = "http://192.168.68.110/Delegate/DelegatedEmployeeListApi";

        mPostJsonData =  new PostJsonData(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.delegateEmployeeListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cancel = (Button)findViewById(R.id.btnDelegateEmployeeCancel);
        cancel.setOnClickListener(this);


        deEmpAdapter = new DelegateEmployeeMainActivityListAdapter(this, new ArrayList<DelegatedEmployee>());
        recyclerView.setAdapter(deEmpAdapter);

        //deView= findViewById(R.id.delempListRecyclerView);
        DelegateEmployeeCreate = findViewById(R.id.btnDelegateEmployeeCreate);


        DelegateEmployeeCreate.setOnClickListener(this);

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
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("delegatedEmployees");
                for(int i = 0 ; i< jsonArray.length(); i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    DelegatedEmployee delegateEmployee = new DelegatedEmployee();

                    delegateEmployee.setId(jobj.getInt("id"));
                    delegateEmployee.setName(jobj.getString("name"));
                    delegateEmployee.setStartDate((jobj.getString("startDate").split(" "))[0]);
                    delegateEmployee.setEndDate((jobj.getString("endDate").split(" "))[0]);
                    delegateEmployee.setDelegationStatus(DelegationStatus.valueOf(jobj.getInt("status")));

                    mDelegateEmployees.add(delegateEmployee);
                }
                this.deEmpAdapter.loadNewData(mDelegateEmployees);
                //try to disable create button
                if(mDelegateEmployees != null) {
                    for (DelegatedEmployee demp : mDelegateEmployees) {
                        if (demp.getDelegationStatus().equals(DelegationStatus.SELECTED) || demp.getDelegationStatus().equals(DelegationStatus.EXTENDED)) {
                            DelegateEmployeeCreate.setEnabled(false);
                            cancel.setEnabled(true);
                        }else {
                            DelegateEmployeeCreate.setEnabled(true);
                            cancel.setEnabled(false);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {

        if(view == DelegateEmployeeCreate){
            Intent intent = new Intent(DelegateEmployeeMainActivity.this, CreateNewDelegateEmployee.class);
            startActivity(intent);
        }

        if(view == cancel){
            JSONObject demoObject = new JSONObject();
            try {
                demoObject.put("flag","CANCEL");
                mPostJsonData.loadJsonData(demoObject.toString());
                mPostJsonData.execute(mURLsend);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, DelegateEmployeeMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {
        //no data to get back
    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.depthead_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.delegate_employee_item:
                intent = new Intent(this, DelegateEmployeeMainActivity.class);
                break;
            case R.id.approve_reject_requisitions_item:
                intent = new Intent(this, DeptHeadReqList.class);
                break;
            case R.id.Dept_Head_Logout_item:
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
