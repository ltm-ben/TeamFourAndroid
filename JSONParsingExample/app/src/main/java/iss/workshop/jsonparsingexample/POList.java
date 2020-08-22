package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Item;
import iss.workshop.jsonparsingexample.Models.PO;

public class POList extends AppCompatActivity implements View.OnClickListener{


    RecyclerView rView;
    Button mbtnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_o_list);

            rView = findViewById(R.id.poListRecyclerView);
        Date d = new Date();

        PO po1 = new PO();
        po1.setId(1);
        po1.setOrderDate("10/10/2020 12:00:00 AM");
        po1.setSupplierName("Fair Price");
        po1.setStatus("Processing");

        mbtnCreate = findViewById(R.id.btnPOCreate);
        mbtnCreate.setOnClickListener(this);



            List<PO> poList = new ArrayList<>();

            poList.add(po1);

            POListAdpater poAdapter = new POListAdpater(this,poList);

            //SupplierCreateWithItemAdapter adapter = new SupplierCreateWithItemAdapter(this, itemList);

            rView.setAdapter(poAdapter);
            rView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.btnPOCreate:
                launchPOCreateActivity();
                break;


        }
    }

    void launchPOCreateActivity(){
        Intent intent = new Intent(this,PurchaseOrder.class);
        startActivity(intent);
    }
}