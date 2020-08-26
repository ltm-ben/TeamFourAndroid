package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import iss.workshop.jsonparsingexample.Models.DelegatedEmployee;

public class DeptHeadMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_main);
    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.depthead_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delegate_employee_item:
                launchDelegateEmployeeMainActivity();
                return true;
            case R.id.approve_reject_requisitions_item:
                launchApproveRejectRequisitionsMainActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void launchDelegateEmployeeMainActivity() {
        Intent intent = new Intent(this, DelegateEmployeeMainActivity.class);
        startActivity(intent);
    }

    private void launchApproveRejectRequisitionsMainActivity() {
        Intent intent = new Intent(this, DeptHeadReqList.class);
        startActivity(intent);
    }


}