package iss.workshop.jsonparsingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

import iss.workshop.jsonparsingexample.Models.DisbursementDetail;

public class BarChartActivity extends AppCompatActivity implements GetRawData.OnDownloadComplete  {

    ArrayList<BarEntry> entries = null;
    ArrayList<String> labels = null;
    ArrayList<String> stationeries = null;
    public String mURL;
    BarChart barChart;
    Spinner spinnerStationary;
    JSONArray itemsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = (BarChart) findViewById(R.id.barchart);
        mURL = "http://169.254.105.22:8080/store/storeclerkdisbursementdetailslistapi" ;

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
        spinnerStationary = findViewById(R.id.spinner1);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stationeries);
        spinnerStationary.setAdapter(spinnerAdapter);
//        spinnerStationary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int o, long l) {
//
//                String item = spinnerStationary.getSelectedItem().toString();
//                Toast.makeText(view.getContext(),"Test",Toast.LENGTH_LONG).show();
//                try {
//                    //base on dept name group quantity disbursed
//                    for (int i = 0; i < labels.size(); i++) {
//                        String selectedDepartment = labels.get(i);
//                        for (int j = 0; j < itemsArray.length(); j++) {
//                            JSONObject jsonDisbursementDetail = itemsArray.getJSONObject(j);
//                            String deptName = jsonDisbursementDetail.getString("DeptName");
//                            String stationary = jsonDisbursementDetail.getString("StationeryName");
//                            if (selectedDepartment.equals(deptName) && item.equals(stationary)) {
//                                BarEntry entry = entries.get(i);
//                                entry.setVal(entry.getVal() + jsonDisbursementDetail.getInt("Qty"));
//                            }
//                        }
//                    }
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//
//                BarDataSet bardataset = new BarDataSet(entries, "Cells");
//                BarData bardata = new BarData(labels, bardataset);
//                barChart.setData(bardata);
//                barChart.animateY(1250);
//                barChart.notifyDataSetChanged();
//                barChart.invalidate();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
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

            } catch(JSONException jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}