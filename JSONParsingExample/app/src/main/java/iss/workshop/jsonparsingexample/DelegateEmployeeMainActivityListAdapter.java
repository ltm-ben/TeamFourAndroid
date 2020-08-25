package iss.workshop.jsonparsingexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.DelegatedEmployee;
import iss.workshop.jsonparsingexample.Models.DeptRole;
import iss.workshop.jsonparsingexample.Models.Employee;
import iss.workshop.jsonparsingexample.Models.Item;
import iss.workshop.jsonparsingexample.Models.PO;

public class DelegateEmployeeMainActivityListAdapter extends RecyclerView.Adapter<DelegateEmployeeMainActivityListAdapter.MyViewHolder> {

    List<DelegatedEmployee> empList;
    Context context;

    public DelegateEmployeeMainActivityListAdapter(Context ct, List<DelegatedEmployee> eList){
        context = ct;
        empList = eList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delegate_employee_list_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DelegatedEmployee delegatedEmployeeemployee = empList.get(position);

        //holder.id.setText(String.valueOf(employee.getId()) );
        holder.name.setText(delegatedEmployeeemployee.getName());
        holder.startDate.setText(delegatedEmployeeemployee.getStartDate());
        holder.endDate.setText(delegatedEmployeeemployee.getEndDate());
        holder.status.setText(delegatedEmployeeemployee.getDelegationStatus().toString());
    }

    @Override
    public int getItemCount() {
        return empList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name= null;TextView startDate = null ; TextView endDate = null; TextView status = null;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.id = itemView.findViewById(R.id.poId);
            this.name = itemView.findViewById(R.id.delegateEmployeeName);
            this.startDate = itemView.findViewById(R.id.startdate);
            this.endDate = itemView.findViewById(R.id.enddate);
            this.status = itemView.findViewById(R.id.Status);


        }
    }

    void loadNewData(List<DelegatedEmployee> newEmp) {
        empList = newEmp;
        notifyDataSetChanged();
    }
}
