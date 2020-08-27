package iss.workshop.jsonparsingexample;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Category;
import iss.workshop.jsonparsingexample.Models.DelegatedEmployee;
import iss.workshop.jsonparsingexample.Models.DeptRole;
import iss.workshop.jsonparsingexample.Models.Employee;
import iss.workshop.jsonparsingexample.Models.Stock;
import iss.workshop.jsonparsingexample.Models.SupplierList;

public class CreateNewDelegateEmployee extends AppCompatActivity implements View.OnClickListener,GetRawData.OnDownloadComplete,PostJsonData.OnDownloadComplete{

    private List<Employee> employeeList;
    int selectedEmpId;
    Button create;
    EditText startDate, endDate;
    Spinner empName;
    private int mYear, mMonth, mDay;
    private List<DelegatedEmployee> mDelegatedEmployeeList = null;
    public String mURL = "https://logicuniversity.nusteamfour.online/Delegate/EmployeeListApi";
    PostJsonData mPostJsonData;
    String mURLsend;
    //public String mURL = "http://192.168.1.30/Delegate/DelegatedEmployeeListApi";
    //Spinner empName = (Spinner) findViewById((R.id.delegatedEmployee));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_delegate_employee);

//        GetRawData getRawData = new GetRawData(this);
//        getRawData.execute(mURL);
        // Get the widget reference from XML layout

        // btnDatePicker=(Button)findViewById(R.id.btn_date);
        // btnTimePicker=(Button)findViewById(R.id.btn_time);
        empName = (Spinner)findViewById(R.id.delegatedEmployee);
        startDate=(EditText)findViewById(R.id.startDate);
        endDate = (EditText)findViewById(R.id.endDate);
        create = (Button)findViewById(R.id.btnCreate);
        //txtTime=(EditText)findViewById(R.id.in_time);

        // btnDatePicker.setOnClickListener(this);
        // btnTimePicker.setOnClickListener(this);

        //try to send data
        mPostJsonData = new PostJsonData(this);
        mURLsend = "https://logicuniversity.nusteamfour.online/Delegate/PostSelectedEmp";

        startDate.setOnClickListener(this);
        //startDate.requestFocus();
        endDate.setOnClickListener(this);
        //endDate.requestFocus();
        create.setOnClickListener(this);
        empName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEmpId = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
//    public void addListenerOnSpinnerItemSelection() {
//        spinner1 = (Spinner) findViewById(R.id.delegatedEmployee);
//        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//    }
//    // get the selected dropdown list value
//    public void addListenerOnButton() {
//
//        spinner1 = (Spinner) findViewById(R.id.delegatedEmployee);
//        btnSubmit = (Button) findViewById(R.id.btnCreate);
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MyAndroidAppActivity.this,
//                        "OnClickListener : " +
//                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem())
//                                ,
//                        Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (v == startDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if (monthOfYear<9  && dayOfMonth <9) {
                                startDate.setText("0" + dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year);
                            }
                            else if (monthOfYear<9 && dayOfMonth>9)
                            {
                                startDate.setText(dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year);
                            }
                            else if (monthOfYear>9 && dayOfMonth<9)
                            {
                                startDate.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                            else
                            {
                                startDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == endDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if (monthOfYear<9  && dayOfMonth <9) {
                                endDate.setText("0" + dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year);
                            }
                            else if (monthOfYear<9 && dayOfMonth>9)
                            {
                                endDate.setText(dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year);
                            }
                            else if (monthOfYear>9 && dayOfMonth<9)
                            {
                                endDate.setText("0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                            else
                            {
                                endDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == create){
            String empName = employeeList.get(selectedEmpId).getName();
            String StartDate = startDate.getText().toString();
            String EndDate = endDate.getText().toString();
            if(empName!= null && StartDate!= null && EndDate!=null) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date Start, End;
                try {
                    Start = format.parse(StartDate);
                    End = format.parse(EndDate);
                    if (Start.after(End)) {
                        Toast.makeText(this, "Your Date Selection is WRONG", Toast.LENGTH_LONG).show();
                    } else {
                        JSONObject demoObject = new JSONObject();
                        try {

                            demoObject.put("EmpId", selectedEmpId + 1);
                            demoObject.put("StartDate", StartDate);
                            demoObject.put("EndDate", EndDate);

                            mPostJsonData.loadJsonData(demoObject.toString());
                            mPostJsonData.execute(mURLsend);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, DelegateEmployeeMainActivity.class);
                startActivity(intent);
                //sending data to C#
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(mURL);
    }
    @Override
    public void getRawDataOnDownloadComplete(String data, DownloadStatus status) {

        if(status == DownloadStatus.OK){
            employeeList = new ArrayList<Employee>();

            try {
                JSONObject jobj = new JSONObject(data);
                JSONArray jarr = jobj.getJSONArray("EmployeeList");

                for(int i = 0 ; i< jarr.length(); i++){
                    JSONObject jsonObject = jarr.getJSONObject(i);
                    Employee emp = new Employee();

                    emp.setId(jsonObject.getInt("Id"));
                    emp.setName(jsonObject.getString("Name"));
                    emp.setDeptId(jsonObject.getInt("DeptId"));
                    emp.setRole(DeptRole.valueOf(jsonObject.getInt("Role")));
                    employeeList.add(emp);



                }
                Spinner mSpinner =(Spinner) findViewById(R.id.delegatedEmployee);
                // Bind Spinner to states enum
                List<String> ename = new ArrayList<>();
                for(int i = 0 ; i<employeeList.size();i++) {
                    ename.add(employeeList.get(i).getName());
                }
                mSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ename));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void postJsonDataOnDownloadComplete(String data, DownloadStatus status) {
        //tbd
    }
}
