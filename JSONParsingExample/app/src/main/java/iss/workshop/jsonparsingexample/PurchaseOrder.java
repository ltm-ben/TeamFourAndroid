package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import iss.workshop.jsonparsingexample.Models.CollectionPoint;
import iss.workshop.jsonparsingexample.Models.SupplierList;

public class PurchaseOrder extends AppCompatActivity implements PostJsonData.OnDownloadComplete {
        String selectedItemText;
        EditText mDate;
        DatePickerDialog mDatePickerDialog;
        String mURL =  "http://192.168.68.110/PO/POCreate";
        String orderDate;
        public static final String TAG = "Purchase Order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);

        // Get the widget reference from XML layout
        Spinner mSpinner =(Spinner) findViewById(R.id.spnSupplier);

        // Bind Spinner to states enum
        String[] suppliers = getResources().getStringArray(R.array.suppliers);
        mSpinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, suppliers));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                Toast.makeText
                        (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                        if (month<9  && day <9) {
                            mDate.setText(year + "-" + "0" + (month + 1) + "-" +  "0"+day);
                        }
                        else if (month<9 && day>9)
                        {
                            mDate.setText(year + "-"  + "0"+ (month + 1) + "-" +day);
                        }
                        else if (month>9 && day<9)
                        {
                            mDate.setText(year + "-" + (month + 1) + "-" +"0" + day);
                        }
                        else
                        {
                            mDate.setText(year + "-"  + (month + 1) + "-" +day);
                        }
                    }
                },mYear, mMonth,mDay);
                mDatePickerDialog.show();
            }
        });
        orderDate = mDate.getText().toString();


        //send orderDate, selectedItemText to Visual Studio
        //PostJson to VS

        Button button = findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onResume ends" +orderDate);
                mDate = findViewById(R.id.date);
                orderDate = mDate.getText().toString();
                Toast.makeText
                        (getApplicationContext(), orderDate, Toast.LENGTH_SHORT)
                        .show();
                callPostApi(orderDate,selectedItemText);
                Intent intent = new Intent(PurchaseOrder.this,PurchaseOrderCreateWithItems.class);
                startActivity(intent);

            }
        });

    }
    private String formatDataAsJSON(String orderDate, String selectedItemText){

        final JSONObject root = new JSONObject();

        try {
            root.put("OrderDate", orderDate);
            root.put("SupplierName",selectedItemText );

            return root.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void callPostApi(String orderDate, String selectedItemText) {

        PostJsonData postJsonData = new PostJsonData(this);
        postJsonData.loadJsonData(formatDataAsJSON(orderDate,selectedItemText));
        postJsonData.execute(mURL);
    }

    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            // work with response data from POST Request API
            try {

                JSONObject jsonData = new JSONObject(data);
                JSONObject result = jsonData.getJSONObject("result");


                String text = result.getString("Message");
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();

            } catch(JSONException json) {
                json.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    //  Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.storeclerk_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.Bar_Chart_List_item:
                intent = new Intent(this, BarChartActivity.class);
                startActivity(intent);
                return true;
            case R.id.Requisition_List_item:
                intent = new Intent(this, StoreClerkRequisitionListActivity.class);
                startActivity(intent);
                return true;
            case R.id.Disbursement_List_item:
                intent = new Intent(this, StoreClerkDisbursementListActivity.class);
                startActivity(intent);
                return true;
            case R.id.Disbursement_Packing_item:
                intent = new Intent(this, StoreClerkDisbursementPackingActivity.class);
                startActivity(intent);
                return true;
            case R.id.Stock_List_item:
                intent = new Intent(this, StockListActivity.class);
                startActivity(intent);
                return true;
            case R.id.PO_List_item:
                intent = new Intent(this,POList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}