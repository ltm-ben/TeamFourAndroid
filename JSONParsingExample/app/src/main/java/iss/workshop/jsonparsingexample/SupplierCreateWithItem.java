package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Item;

public class SupplierCreateWithItem extends AppCompatActivity {


    RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_create_with_item);

        rView = findViewById(R.id.supplierCreateWithItemListRecyclerView);

        Item  i1 = new Item();
        i1.setName("Item1");
        i1.setUnitPrice(10);
        i1.setPrediction(11);

        Item i2 = new Item();
        i2.setName("Item2");
        i2.setUnitPrice(22);
        i2.setPrediction(4);

        List<Item> itemList = new ArrayList<>();

        itemList.add(i1);
        
        SupplierCreateWithItemAdapter adapter = new SupplierCreateWithItemAdapter(this,itemList);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }
}