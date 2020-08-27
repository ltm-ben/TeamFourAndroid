package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DeptHeadMainActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete {

    private String mLogoutURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_main);

        mLogoutURL = "http://192.168.68.110/logout/logoutapi";
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

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