package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import iss.workshop.jsonparsingexample.Models.DisbursementDetail;

public class BarChartActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete,View.OnClickListener {

    ArrayList<BarEntry> entries = null;
    ArrayList<String> labels = null;
    ArrayList<String> stationeries = null;
    public String mURL;
    BarChart barChart;
    Spinner spinnerStationary;
    JSONArray itemsArray;
    Button submit;
    int selectedItemId;
    String selectedItemName;
    EditText editTextDateFrom;
    DatePickerDialog mDatePickerDialogFrom;
    String dateFrom;
    EditText editTextDateTo;
    DatePickerDialog mDatePickerDialogTo;
    String dateTo;

    private String mLogoutURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = (BarChart) findViewById(R.id.barchart);
        mURL = "http://192.168.68.110/store/storeclerkdisbursementdetailslistapi" ;
        mLogoutURL = "http://192.168.68.110/logout/logoutapi";
        submit = (Button) findViewById(R.id.submit_btn);
        submit.setOnClickListener(this);
        entries = new ArrayList<>();
        /*entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));*/

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        labels = new ArrayList<String>();
        /*labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");*/

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Total Orders by Department");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        stationeries = new ArrayList<>();
        itemsArray = new JSONArray();
        spinnerStationary = (Spinner)findViewById(R.id.spinner1);

        spinnerStationary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int o, long l) {

                selectedItemId = o;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editTextDateFrom = findViewById(R.id.editTextDateFrom);
        editTextDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                //current year, month,day fr Calendar
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                //datepicker dialog
                mDatePickerDialogFrom = new DatePickerDialog(BarChartActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        if (month<9  && day <9) {
                            editTextDateFrom.setText(year + "-" + "0" + (month + 1) + "-" +  "0"+day);
                        }
                        else if (month<9 && day>9)
                        {
                            editTextDateFrom.setText(year + "-"  + "0"+ (month + 1) + "-" +day);
                        }
                        else
                        {
                            editTextDateFrom.setText(year + "-"  + (month + 1) + "-" +day);
                        }
                    }
                },mYear, mMonth,mDay);
                mDatePickerDialogFrom.show();
                dateFrom = editTextDateFrom.getText().toString();
            }
        });

        editTextDateTo = findViewById(R.id.editTextDateTo);
        editTextDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                //current year, month,day fr Calendar
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                //datepicker dialog
                mDatePickerDialogTo = new DatePickerDialog(BarChartActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        if (month<9  && day <9) {
                            editTextDateTo.setText(year + "-" + "0" + (month + 1) + "-" +  "0"+day);
                        }
                        else if (month<9 && day>9)
                        {
                            editTextDateTo.setText(year + "-"  + "0"+ (month + 1) + "-" +day);
                        }
                        else
                        {
                            editTextDateTo.setText(year + "-"  + (month + 1) + "-" +day);
                        }
                    }
                },mYear, mMonth,mDay);
                mDatePickerDialogTo.show();
                dateTo = editTextDateTo.getText().toString();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mURL);
    }

    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK) {

            try {
                JSONObject jsonData = new JSONObject(data);



                itemsArray = jsonData.getJSONArray("requisitions");

                //get dept name
                int barEntryIndex = 0;
                for(int i=0; i<itemsArray.length(); i++) {
                    JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(i);
                    String deptName = jsonDisbursementDetail.getString("DeptName");
                   if(!labels.contains(deptName)){
                        labels.add(deptName);
                        entries.add(new BarEntry(0f,barEntryIndex));
                        barEntryIndex++;
                    }

                    String stationeryName = jsonDisbursementDetail.getString("StationeryName");
                   if(!stationeries.contains(stationeryName)){
                       stationeries.add(stationeryName);
                    }
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, stationeries);
                spinnerStationary.setAdapter(spinnerAdapter);
                //base on dept name group quantity disbursed
                for(int i=0; i<labels.size(); i++) {
                    String selectedDepartment=labels.get(i);
                    //String date = labels.get(i)
                    for(int j=0; j<itemsArray.length(); j++) {
                        JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(j);
                        String deptName = jsonDisbursementDetail.getString("DeptName");
                        if(selectedDepartment.equals(deptName)){
                            BarEntry entry = entries.get(i);
                            entry.setVal(entry.getVal() + jsonDisbursementDetail.getInt("Qty"));
                        }
                    }
                }

                BarDataSet bardataset = new BarDataSet(entries, "Cells");
                BarData bardata = new BarData(labels, bardataset);
                barChart.setData(bardata);
                barChart.animateY(1250);
                barChart.notifyDataSetChanged();

                barChart.invalidate();

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == submit){
            selectedItemName = stationeries.get(selectedItemId);
            dateFrom = editTextDateFrom.getText().toString();
            dateTo = editTextDateTo.getText().toString();
            //Toast.makeText(v.getContext(),"From " + dateFrom + " To " + dateTo,Toast.LENGTH_LONG).show();
            //Toast.makeText(v.getContext(),"stationery selected",Toast.LENGTH_LONG).show();
                try {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fromDate = LocalDate.parse(dateFrom, formatter);
                    LocalDate toDate = LocalDate.parse(dateTo, formatter);

                    resetEntry();

                    //base on dept name group quantity disbursed
                    for (int i = 0; i < labels.size(); i++) {
                        String selectedDepartment = labels.get(i);
                        for (int j = 0; j < itemsArray.length(); j++) {
                            JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(j);
                            String deptName = jsonDisbursementDetail.getString("DeptName");
                            String stationary = jsonDisbursementDetail.getString("StationeryName");
                            String disbursementDateStr = jsonDisbursementDetail.getString("A_Date").split("T")[0];


                            //convert String to LocalDate
                            LocalDate disbursementDate = LocalDate.parse(disbursementDateStr, formatter);


                            if (selectedDepartment.equals(deptName) && stationary.equals(selectedItemName)
                                    && (disbursementDate.isAfter(fromDate) && disbursementDate.isBefore(toDate))) {
                                BarEntry entry = entries.get(i);
                                entry.setVal(jsonDisbursementDetail.getInt("Qty") + entry.getVal());
                            }
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    resetEntry();
                    Toast.makeText(v.getContext(),"No data found!",Toast.LENGTH_LONG).show();
                }

                BarDataSet bardataset2 = new BarDataSet(entries, "Cells");
                BarData bardata2 = new BarData(labels, bardataset2);
                barChart.clear();
                barChart.setData(bardata2);
                barChart.animateY(1250);

            barChart.notifyDataSetChanged();
            barChart.invalidate();

 }
    }

    public void resetEntry(){
        for (int i = 0; i < labels.size(); i++) {
            BarEntry entry = entries.get(i);
            entry.setVal(0);
            //  Option Menu
//            @Override
//            public boolean onCreateOptionsMenu (Menu menu){
//                getMenuInflater().inflate(R.menu.storeclerk_options_menu, menu);
//                return true;
//            }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        Intent intent;
//
//        switch (item.getItemId()) {
//            case R.id.Bar_Chart_List_item:
//                intent = new Intent(this, BarChartActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.Requisition_List_item:
//                intent = new Intent(this, StoreClerkRequisitionListActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.Disbursement_List_item:
//                intent = new Intent(this, StoreClerkDisbursementListActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.Disbursement_Packing_item:
//                intent = new Intent(this, StoreClerkDisbursementPackingActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.Stock_List_item:
//                intent = new Intent(this, StockListActivity.class);
//                startActivity(intent);
//                return true;
//            case R.id.PO_List_item:
//                intent = new Intent(this,POList.class);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
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
                break;
            case R.id.Requisition_List_item:
                intent = new Intent(this, StoreClerkRequisitionListActivity.class);
                break;
            case R.id.Disbursement_List_item:
                intent = new Intent(this, StoreClerkDisbursementListActivity.class);
                break;
            case R.id.Disbursement_Packing_item:
                intent = new Intent(this, StoreClerkDisbursementPackingActivity.class);
                break;
            case R.id.Stock_List_item:
                intent = new Intent(this, StockListActivity.class);
                break;
            case R.id.PO_List_item:
                intent = new Intent(this,POList.class);
                break;
            case R.id.Store_Clerk_Logout_item:
                GetRawData getRawData = new GetRawData(this);
                getRawData.execute(mLogoutURL);
                intent = new Intent(this, LoginActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);

        return true;
    }
}
