package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StoreClerkMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_main);
    }
    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.storeclerk_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Stock_List_item:
                launchStockListActivity();
                return true;
            case R.id.PO_List_item:
                launchPoListActivity();
                return true;
            case R.id.Disbursement_List_item:
                launchStoreClerkDisbursementListActivity();
                return true;
            case R.id.Disbursement_Packing_item:
                launchStoreClerkDisbursementPackingActivity();
                return true;
            case R.id.Bar_Chart_List_item:
                launchBarChartActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void launchStockListActivity() {
        Intent intent = new Intent(this, StockListActivity.class);
        startActivity(intent);
    }
    void launchPoListActivity() {
        Intent intent = new Intent(this,POList.class);
        startActivity(intent);
    }
    void launchStoreClerkDisbursementListActivity() {
        Intent intent = new Intent(this, StoreClerkDisbursementListActivity.class);
        startActivity(intent);
    }
    void launchStoreClerkDisbursementPackingActivity() {
        Intent intent = new Intent(this, StoreClerkDisbursementPackingActivity.class);
        startActivity(intent);
    }
    void launchBarChartActivity() {
        Intent intent = new Intent(this, BarChartActivity.class);
        startActivity(intent);
    }

}

