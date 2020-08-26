package iss.workshop.jsonparsingexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;

public class DeptHeadReqListRecyclerViewAdapter extends RecyclerView.Adapter<DeptHeadReqListRecyclerViewAdapter.DeptHeadReqListViewHolder> {

    private List<DeptRequisition> mRequisitionList;
    private Context mContext;
    private DeptHeadReqListRecyclerViewClickListener listener;


    public DeptHeadReqListRecyclerViewAdapter(Context ct,List<DeptRequisition> requisitionList, DeptHeadReqListRecyclerViewClickListener listener){
        mContext = ct;
        mRequisitionList = requisitionList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public DeptHeadReqListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater deptHeadReqListinflater = LayoutInflater.from(mContext);
        View view = deptHeadReqListinflater.inflate(R.layout.activity_dept_head_req_list_row, parent,false);
        return new DeptHeadReqListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeptHeadReqListViewHolder holder, int position) {

        DeptRequisition requisitionItem = mRequisitionList.get(position);
        holder.requisitionId.setText(String.valueOf(requisitionItem.getId()));
        holder.requisitionApprovalStatus.setText(String.valueOf(requisitionItem.getRequisitionApprovalStatus()));

    }

    @Override
    public int getItemCount() {
        return ((mRequisitionList != null) && (mRequisitionList.size() != 0) ? mRequisitionList.size() : 0);
    }

    void loadNewData(List<DeptRequisition> newRequisitions) {
        mRequisitionList = newRequisitions;
        notifyDataSetChanged();
    }

    public DeptRequisition getRequisition(int position) {
        return ((mRequisitionList != null) && (mRequisitionList.size() != 0) ? mRequisitionList.get(position) : null);
    }

    public class DeptHeadReqListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView requisitionId = null;
        TextView requisitionApprovalStatus = null;

        public DeptHeadReqListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.requisitionId = itemView.findViewById(R.id.DeptHeadReqListreqId);
            this.requisitionApprovalStatus = itemView.findViewById(R.id.DepHeadReqListapprovalStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {listener.onClick(v, getAdapterPosition());}
    }

    public interface DeptHeadReqListRecyclerViewClickListener {
        void onClick(View v, int position);
    }
}