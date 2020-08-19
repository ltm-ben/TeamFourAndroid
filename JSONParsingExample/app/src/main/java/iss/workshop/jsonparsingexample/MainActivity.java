package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Defining buttons
    Button mStockListBtn;
    Button mRequisitionListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStockListBtn = findViewById(R.id.stockListBtn);
        mStockListBtn.setOnClickListener(this);

        mRequisitionListBtn = findViewById(R.id.requisitionListBtn);
        mRequisitionListBtn.setOnClickListener(this);
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
        }
    }

    void launchStockListActivity() {
        Intent intent = new Intent(this, StockListActivity.class);
        startActivity(intent);
    }

    void launchRequisitionListActivity() {
        Intent intent = new Intent(this, DeptHeadRequisitionListActivity.class);
        startActivity(intent);
    }
}