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

import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDetailDto;
import iss.workshop.jsonparsingexample.Models.Stock;

public class DisbursementPackingRecyclerViewAdapter extends RecyclerView.Adapter<DisbursementPackingRecyclerViewAdapter.DisbursementPackingViewHolder> {

    private List<DisbursementDetailDto> mDisbursementDetails;
    private Context mContext;

    public DisbursementPackingRecyclerViewAdapter(Context context, List<DisbursementDetailDto> disbursementDetailList) {
        mContext = context;
        mDisbursementDetails = disbursementDetailList;
    }

    @NonNull
    @Override
    public DisbursementPackingRecyclerViewAdapter.DisbursementPackingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_clerk_disbursement_packing_row, parent, false);
        return new DisbursementPackingRecyclerViewAdapter.DisbursementPackingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisbursementPackingRecyclerViewAdapter.DisbursementPackingViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        DisbursementDetailDto disbursementDetailItem = mDisbursementDetails.get(position);
        holder.disbursementDetailItemCode.setText(disbursementDetailItem.getItemCode());
        holder.disbursementDetailItemName.setText(disbursementDetailItem.getItemName());
        holder.disbursementDetailQty.setText(String.valueOf(disbursementDetailItem.getQty()));
    }

    @Override
    public int getItemCount() {
        return ((mDisbursementDetails != null) && (mDisbursementDetails.size() != 0) ? mDisbursementDetails.size() : 0);
    }

    void loadNewData(List<DisbursementDetailDto> data) {
        mDisbursementDetails = data;
        notifyDataSetChanged();
    }

    public DisbursementDetailDto getDisbursementDetail(int position) {
        return ((mDisbursementDetails != null) && (mDisbursementDetails.size() != 0) ? mDisbursementDetails.get(position) : null);
    }

    static class DisbursementPackingViewHolder extends RecyclerView.ViewHolder {

        TextView disbursementDetailItemCode = null;
        TextView disbursementDetailItemName = null;
        TextView disbursementDetailQty = null;

        public DisbursementPackingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.disbursementDetailItemCode = (TextView) itemView.findViewById(R.id.disbursementDetailItemCode);
            this.disbursementDetailItemName = (TextView) itemView.findViewById(R.id.disbursementDetailItemName);
            this.disbursementDetailQty = (TextView) itemView.findViewById(R.id.disbursementDetailQty);
        }
    }
}
