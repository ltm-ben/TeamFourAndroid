package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import iss.workshop.jsonparsingexample.Models.DeptEmployeeMainActivity;
import iss.workshop.jsonparsingexample.Models.Employee;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pref;
    String username;

    // Defining buttons
    Button mStockListBtn;
    Button mRequisitionListBtn;
    Button mStoreDeptBtn;
    Button mPOBtn;
    Button mSupplierCreateWithItem;
    Button mPOList;
    Button mDisbursementDetail;
    Button mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        username = pref.getString("username", "");

        mStockListBtn = findViewById(R.id.stockListBtn);
        mStockListBtn.setOnClickListener(this);

        mRequisitionListBtn = findViewById(R.id.requisitionListBtn);
        mRequisitionListBtn.setOnClickListener(this);

        mStoreDeptBtn = findViewById(R.id.StoreDeptBtn);
        mStoreDeptBtn.setOnClickListener(this);

        mPOBtn = findViewById((R.id.POBtn));
        mPOBtn.setOnClickListener(this);

        mSupplierCreateWithItem = findViewById(R.id.SupplierCreateItemBtn);
        mSupplierCreateWithItem.setOnClickListener(this);

        mPOList = findViewById(R.id.poListBtn);
        mPOList.setOnClickListener(this);

        mDisbursementDetail = findViewById(R.id.DisbursementDetailsBtn);
        mDisbursementDetail .setOnClickListener(this);

        mBarChart= findViewById(R.id.BarChartBtn);
        mBarChart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.stockListBtn:
                launchStockListActivity();
                break;
            case R.id.requisitionListBtn:
                launchRequisitionListActivity();
                break;
            case R.id.StoreDeptBtn:
                launchStoreDeptActivity();
                break;
            case R.id.POBtn:
                launchPurchaseOrderActivity();
                break;
            case R.id.SupplierCreateItemBtn:
                launchSupplierCreateWithItemActivity();
                break;
            case R.id.poListBtn:
                launchPoListActivity();
                break;

            case R.id.DisbursementDetailsBtn:
                launchDisbursementDetailActivity();
                break;

            case R.id.BarChartBtn:
                launchBarChartActivity();
                break;
        }
    }

    void launchStockListActivity() {
        Intent intent = new Intent(this, StockListActivity.class);
        startActivity(intent);
    }
    void launchRequisitionListActivity() {
        Intent intent = new Intent(this, StoreClerkRequisitionListActivity.class);
        startActivity(intent);
    }
    void launchStoreDeptActivity() {
        Intent intent = new Intent(this,CreatStoreDeptActivity.class);
        startActivity(intent);
    }
    void launchPurchaseOrderActivity() {
        Intent intent = new Intent(this,PurchaseOrder.class);
        startActivity(intent);
    }
    void launchSupplierCreateWithItemActivity() {
        Intent intent = new Intent(this,SupplierCreateWithItem.class);
        startActivity(intent);
    }
    void launchPoListActivity() {
        Intent intent = new Intent(this,POList.class);
        startActivity(intent);
    }
    void launchTestPostJsonActivity() {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }
    void launchDisbursementDetailActivity() {
        Intent intent = new Intent(this, StoreClerkDisbursementDetailActivity.class);
        startActivity(intent);
    }
    void launchBarChartActivity() {
        Intent intent = new Intent(this, BarChartActivity.class);
        startActivity(intent);
    }
    void launchDeptHeadMainActivity() {
        Intent intent = new Intent(this, DeptHeadMainActivity.class);
        startActivity(intent);
    }
    void launchEmployeeMainActivity() {
        Intent intent = new Intent(this, DeptEmployeeMainActivity.class);
        startActivity(intent);
    }
    void launchStoreClerkMainActivity() {
        Intent intent = new Intent(this, StoreClerkMainActivity.class);
        startActivity(intent);
    }
    void launchLogoutActivity() {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        finish();
    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dept_head_item:
                launchDeptHeadMainActivity();
                return true;
            case R.id.employee_item:
                launchEmployeeMainActivity();
                return true;
            case R.id.store_clerk_item:
                launchStoreClerkMainActivity();
                return true;
            case R.id.PO_item:
                launchPurchaseOrderActivity();
                return true;
            case R.id.StoreDept_item:
                launchStoreDeptActivity();
                return true;
            case R.id.StockList_item:
                launchStockListActivity();
                return true;
            case R.id.Test_Post_Json_item:
                launchTestPostJsonActivity();
                return true;
            case R.id.Logout_item:
                launchLogoutActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
