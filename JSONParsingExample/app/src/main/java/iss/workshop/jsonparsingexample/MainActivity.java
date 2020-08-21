package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Defining buttons
    Button mStockListBtn;
    Button mRequisitionListBtn;
    Button mStoreDeptBtn;
    Button mPOBtn;
    Button mSupplierCreateWithItem;
    Button mDisbursementDetail;
    Button mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mDisbursementDetail = findViewById(R.id.DisbursementDetailsBtn);
        mDisbursementDetail .setOnClickListener(this);

        mBarChart = findViewById(R.id.DisbursementDetailsBtn);
        mBarChart.setOnClickListener(this);

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
    void launchStoreDeptActivity(){
        Intent intent = new Intent(this,CreatStoreDeptActivity.class);
        startActivity(intent);
    }

    void launchPurchaseOrderActivity(){
        Intent intent = new Intent(this,PurchaseOrder.class);
        startActivity(intent);
    }

    void launchSupplierCreateWithItemActivity(){
        Intent intent = new Intent(this,SupplierCreateWithItem.class);
        startActivity(intent);
    }

    void launchDisbursementDetailActivity() {
        Intent intent = new Intent(this, StoreClerkDisbursementDetailActivity.class);
        startActivity(intent);
    }
    void launchBarChartActivity(){
            Intent intent = new Intent(this, BarChartActivity.class);
            startActivity(intent);
    }
    void launchTestPostJsonActivity(){
        Intent intent = new Intent(this, TestPostJsonActivity.class);
        startActivity(intent);
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
            case R.id.PO_item:
                launchPurchaseOrderActivity();
                return true;
            case R.id.StoreDept_item:
                launchStoreDeptActivity();
                return true;
            case R.id.StockList_item:
                launchStockListActivity();
                return true;
            case R.id.Test_Post_Json:
                launchTestPostJsonActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
