package iss.workshop.jsonparsingexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.Stock;

public class RequisitionRecyclerViewAdapter extends RecyclerView.Adapter<RequisitionRecyclerViewAdapter.RequisitionViewHolder> {

    private List<DeptRequisition> mRequisitionList;
    private Context mContext;

    public RequisitionRecyclerViewAdapter(Context context, List<DeptRequisition> requisitionList) {
        mContext = context;
        mRequisitionList = requisitionList;
    }

    @NonNull
    @Override
    public RequisitionRecyclerViewAdapter.RequisitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requisition_item, parent, false);
        return new RequisitionRecyclerViewAdapter.RequisitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequisitionRecyclerViewAdapter.RequisitionViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

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

    static class RequisitionViewHolder extends RecyclerView.ViewHolder {

        TextView requisitionId = null;
        TextView requisitionApprovalStatus = null;

        public RequisitionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.requisitionId = (TextView) itemView.findViewById(R.id.requisitionId);
            this.requisitionApprovalStatus = (TextView) itemView.findViewById(R.id.requisitionApprovalStatus);
        }
    }
}
