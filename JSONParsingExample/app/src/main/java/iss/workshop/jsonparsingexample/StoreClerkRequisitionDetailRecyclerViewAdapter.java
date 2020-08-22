package iss.workshop.jsonparsingexample;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import iss.workshop.jsonparsingexample.Models.DeptRequisition;
import iss.workshop.jsonparsingexample.Models.RequisitionDetail;

public class StoreClerkRequisitionDetailRecyclerViewAdapter extends RecyclerView.Adapter<StoreClerkRequisitionDetailRecyclerViewAdapter.RequisitionDetailViewHolder>{

    private DeptRequisition mRequisition;
    private Context mContext;

    public StoreClerkRequisitionDetailRecyclerViewAdapter(Context context, DeptRequisition requisition) {
        mContext = context;
        mRequisition = requisition;
    }

    @NonNull
    @Override
    public StoreClerkRequisitionDetailRecyclerViewAdapter.RequisitionDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_clerk_requisition_detail_row, parent, false);
        return new StoreClerkRequisitionDetailRecyclerViewAdapter.RequisitionDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreClerkRequisitionDetailRecyclerViewAdapter.RequisitionDetailViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        final RequisitionDetail requisitionDetailItem = mRequisition.getRequisitionDetails().get(position);

        // set view widgets in store clerk requisition detail row here
        holder.requisitionDetailItemId.setText(String.valueOf(requisitionDetailItem.getId()));
        holder.requisitionDetailItemName.setText(requisitionDetailItem.getStationeryName());
        holder.requisitionDetailRequestedQty.setText(String.valueOf(requisitionDetailItem.getQty()));
        holder.requisitionDetailCollectedQty.setText(String.valueOf(requisitionDetailItem.getCollectedQty()));

        holder.disburseQtyInput.setText(String.valueOf(requisitionDetailItem.getDisbursedQty()));
        holder.disburseQtyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                requisitionDetailItem.setDisbursedQty(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return ((mRequisition.getRequisitionDetails() != null) && (mRequisition.getRequisitionDetails().size() != 0) ? mRequisition.getRequisitionDetails().size() : 0);
    }

    void loadNewData(DeptRequisition newRequisition) {
        mRequisition = newRequisition;
        notifyDataSetChanged();
    }

    public RequisitionDetail getRequisitionDetail(int position) {
        return ((mRequisition.getRequisitionDetails() != null) && (mRequisition.getRequisitionDetails().size() != 0) ? mRequisition.getRequisitionDetails().get(position) : null);
    }

    public class RequisitionDetailViewHolder extends RecyclerView.ViewHolder {

        TextView requisitionDetailItemId = null;
        TextView requisitionDetailItemName = null;
        TextView requisitionDetailRequestedQty = null;
        TextView requisitionDetailCollectedQty = null;
        EditText disburseQtyInput = null;

        public RequisitionDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.requisitionDetailItemId = (TextView) itemView.findViewById(R.id.requisitionDetailItemId);
            this.requisitionDetailItemName = (TextView) itemView.findViewById(R.id.requisitionDetailItemName);
            this.requisitionDetailRequestedQty = (TextView) itemView.findViewById(R.id.requisitionDetailRequestedQty);
            this.requisitionDetailCollectedQty = (TextView) itemView.findViewById(R.id.requisitionDetailCollectedQty);
            this.disburseQtyInput = (EditText) itemView.findViewById(R.id.disburseQtyInput);

        }
    }
}
