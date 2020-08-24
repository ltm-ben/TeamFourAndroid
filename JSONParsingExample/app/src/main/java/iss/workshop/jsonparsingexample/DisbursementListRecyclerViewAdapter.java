package iss.workshop.jsonparsingexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iss.workshop.jsonparsingexample.Models.DTOs.DisbursementDTO;
import iss.workshop.jsonparsingexample.Models.DeptRequisition;

public class DisbursementListRecyclerViewAdapter extends RecyclerView.Adapter<DisbursementListRecyclerViewAdapter.DisbursementViewHolder> {

    private List<DisbursementDTO> mDisbursementList;
    private Context mContext;
    private DisbursementListRecyclerViewAdapter.RecyclerViewClickListener listener;


    public DisbursementListRecyclerViewAdapter(Context context, List<DisbursementDTO> disbursementList, DisbursementListRecyclerViewAdapter.RecyclerViewClickListener listener) {
        mContext = context;
        mDisbursementList = disbursementList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DisbursementListRecyclerViewAdapter.DisbursementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_clerk_disbursement_list_row, parent, false);
        return new DisbursementListRecyclerViewAdapter.DisbursementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DisbursementListRecyclerViewAdapter.DisbursementViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        DisbursementDTO disbursementItem = mDisbursementList.get(position);
        holder.StoreClerkDisbursementId.setText(String.valueOf(disbursementItem.getId()));
        holder.StoreClerkDisbursementStatus.setText(String.valueOf(disbursementItem.getDisbursementStatus()));
    }

    @Override
    public int getItemCount() {
        return ((mDisbursementList != null) && (mDisbursementList.size() != 0) ? mDisbursementList.size() : 0);
    }

    void loadNewData(List<DisbursementDTO> data) {
        mDisbursementList = data;
        notifyDataSetChanged();
    }

    public DisbursementDTO getDisbursement(int position) {
        return ((mDisbursementList != null) && (mDisbursementList.size() != 0) ? mDisbursementList.get(position) : null);
    }

    public class DisbursementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView StoreClerkDisbursementId = null;
        TextView StoreClerkDisbursementStatus = null;

        public DisbursementViewHolder(@NonNull View itemView) {
            super(itemView);
            this.StoreClerkDisbursementId = (TextView) itemView.findViewById(R.id.StoreClerkDisbursementId);
            this.StoreClerkDisbursementStatus = (TextView) itemView.findViewById(R.id.StoreClerkDisbursementStatus);
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
