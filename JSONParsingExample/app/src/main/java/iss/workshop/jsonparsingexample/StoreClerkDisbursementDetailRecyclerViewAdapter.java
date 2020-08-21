package iss.workshop.jsonparsingexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import iss.workshop.jsonparsingexample.Models.DisbursementDetail;

public class StoreClerkDisbursementDetailRecyclerViewAdapter extends RecyclerView.Adapter<StoreClerkDisbursementDetailRecyclerViewAdapter.DisbursementDetailViewHolder>{

    private ArrayList<DisbursementDetail>  disbursementDetails;
    private Context mContext;

    public StoreClerkDisbursementDetailRecyclerViewAdapter(Context context, ArrayList<DisbursementDetail> disbursementList) {
        mContext = context;
        this.disbursementDetails = disbursementList;
    }

    @NonNull
    @Override
    public StoreClerkDisbursementDetailRecyclerViewAdapter.DisbursementDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_clerk_disbursement_detail_row, parent, false);
        return new StoreClerkDisbursementDetailRecyclerViewAdapter.DisbursementDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreClerkDisbursementDetailRecyclerViewAdapter.DisbursementDetailViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        DisbursementDetail disbursementDetailItem =  disbursementDetails.get(position);

        // set view widgets in store clerk requisition detail row here
        holder.stationeryname.setText(disbursementDetailItem.getStationeryName());
        holder.qty.setText(String.valueOf(disbursementDetailItem.getQty()));
        holder.A_Date.setText(disbursementDetailItem.getA_Date());
        holder.DeptName.setText(disbursementDetailItem.getDeptName());
    }

    @Override
    public int getItemCount() {
        return disbursementDetails.size();
    }

    void loadNewData(ArrayList<DisbursementDetail>  disbursementDetails) {
        this.disbursementDetails = disbursementDetails;
        notifyDataSetChanged();
    }

    public class DisbursementDetailViewHolder extends RecyclerView.ViewHolder {

        TextView stationeryname = null;
        TextView qty = null;
        TextView A_Date = null;
        TextView DeptName = null;

        public DisbursementDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.stationeryname = (TextView) itemView.findViewById(R.id.stationeryname);
            this.qty = (TextView) itemView.findViewById(R.id.qty);
            this.A_Date  = (TextView) itemView.findViewById(R.id.A_Date);
            this.DeptName = (TextView) itemView.findViewById(R.id.DeptName);
        }
    }
}
