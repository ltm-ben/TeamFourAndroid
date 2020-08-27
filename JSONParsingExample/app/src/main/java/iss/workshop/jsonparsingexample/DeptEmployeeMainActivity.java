package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import iss.workshop.jsonparsingexample.R;

public class DeptEmployeeMainActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    private String mLogoutURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_employee_main);

        mLogoutURL = "http://192.168.68.110/logout/logoutapi";
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.Employee_Requisition_Form_item:
                intent = new Intent(this, EmployeeRequisitionFormActivity.class);
                break;
            case R.id.Employee_Logout_item:
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