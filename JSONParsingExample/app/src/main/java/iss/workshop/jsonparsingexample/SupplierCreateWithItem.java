package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Item;

public class SupplierCreateWithItem extends AppCompatActivity {
    private static final String TAG = "SupplierCreateAdapter";

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
        itemList.add(i2);
        final Integer[] enteredNumber = new Integer[10];
        
        SupplierCreateWithItemAdapter adapter = new SupplierCreateWithItemAdapter(this, itemList, new SupplierCreateWithItemAdapter.OnEditTextChanged() {
            @Override
            public void onTextChanged(int suppliedQty, int position, String charSeq) {
                enteredNumber[position] = Integer.valueOf(charSeq);
            }
        });

        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

        for(int i = 0 ; i < enteredNumber.length; i++){
            Log.d(TAG, "Exit Text Value : " + enteredNumber[i] + " Position : " + i);
        }
    }
}