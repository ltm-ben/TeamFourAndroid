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
    private RecyclerViewClickListener listener;


    public RequisitionRecyclerViewAdapter(Context context, List<DeptRequisition> requisitionList, RecyclerViewClickListener listener) {
        mContext = context;
        mRequisitionList = requisitionList;
        this.listener = listener;
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
        holder.requisitionFulfillmentStatus.setText(String.valueOf(requisitionItem.getRequisitionFulfillmentStatus()));
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

    public class RequisitionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView requisitionId = null;
        TextView requisitionFulfillmentStatus = null;

        public RequisitionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.requisitionId = (TextView) itemView.findViewById(R.id.requisitionId);
            this.requisitionFulfillmentStatus = (TextView) itemView.findViewById(R.id.requisitionFulfillmentStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}
