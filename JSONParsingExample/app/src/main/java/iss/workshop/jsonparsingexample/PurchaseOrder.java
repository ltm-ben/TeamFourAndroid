package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import iss.workshop.jsonparsingexample.Models.CollectionPoint;
import iss.workshop.jsonparsingexample.Models.SupplierList;

public class PurchaseOrder extends AppCompatActivity {
    EditText mDate;
    DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);

        // Get the widget reference from XML layout
        Spinner mSpinner =(Spinner) findViewById(R.id.spnSupplier);

        // Bind Spinner to states enum
        mSpinner.setAdapter(new ArrayAdapter<SupplierList>(this,
                android.R.layout.simple_list_item_1, SupplierList.values()));
        //initiate the date picker and a  button
        mDate = findViewById(R.id.date);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                //current year, month,day fr Calendar
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                //datepicker dialog
                mDatePickerDialog = new DatePickerDialog(PurchaseOrder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mDate.setText(day + "/" + (month + 1) + "/" +year);
                    }
                },mYear, mMonth,mDay);
                mDatePickerDialog.show();
            }
        });


    }
}