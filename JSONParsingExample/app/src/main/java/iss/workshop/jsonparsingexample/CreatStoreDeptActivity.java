package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import iss.workshop.jsonparsingexample.Models.CollectionPoint;

public class CreatStoreDeptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_store_dept);

        // Get the widget reference from XML layout
        Spinner mSpinner =(Spinner) findViewById(R.id.spnCollectionP);

        // Bind Spinner to states enum
        mSpinner.setAdapter(new ArrayAdapter<CollectionPoint>(this,
                android.R.layout.simple_list_item_1, CollectionPoint.values()));
    }
}