package iss.workshop.jsonparsingexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.DisbursementDetail;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;

public class DeptHeadReqListDetailsRecyclerViewAdapter extends RecyclerView.Adapter<DeptHeadReqListDetailsRecyclerViewAdapter.DeptHeadReqDtsListViewHolder> {

    private DeptRequisition mRequisition;
    private Context mContext;

    //constructor
    public DeptHeadReqListDetailsRecyclerViewAdapter(Context context, DeptRequisition requisition){
        mRequisition = requisition;
        mContext = context;
    }

    @NonNull
    @Override
    public DeptHeadReqDtsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater deptHeadReqListDetailsinflater = LayoutInflater.from(mContext);
        View view = deptHeadReqListDetailsinflater.inflate(R.layout.activity_dept_head_req_list_details_row,parent,false);
        return new DeptHeadReqDtsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptHeadReqDtsListViewHolder holder, int position) {
        RequisitionDetail requisitionDetailItem = mRequisition.getRequisitionDetails().get(position);
        holder.reqItemName.setText(String.valueOf(requisitionDetailItem.getStationeryName()));
        holder.reqQuantity.setText(String.valueOf(requisitionDetailItem.getQty()));
    }

    @Override
    public int getItemCount() {
        return ((mRequisition.getRequisitionDetails() != null) && (mRequisition.getRequisitionDetails().size() != 0) ? mRequisition.getRequisitionDetails().size() : 0);
    }

    void loadNewData(DeptRequisition newRequisition) {
        mRequisition = newRequisition;
        notifyDataSetChanged();
    }

    public class DeptHeadReqDtsListViewHolder extends RecyclerView.ViewHolder {

        TextView reqItemName = null;
        TextView reqQuantity = null;

        public DeptHeadReqDtsListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.reqItemName = (TextView)itemView.findViewById(R.id.reqItemName);
            this.reqQuantity = (TextView)itemView.findViewById(R.id.reqQuantity);
        }
    }
}