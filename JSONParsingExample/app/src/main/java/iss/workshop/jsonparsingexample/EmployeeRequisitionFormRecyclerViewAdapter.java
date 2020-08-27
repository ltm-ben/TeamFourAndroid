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

public class EmployeeRequisitionFormRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeRequisitionFormRecyclerViewAdapter.RequisitionFormViewHolder> {

    private DeptRequisition mRequisition;
    private Context mContext;

    public EmployeeRequisitionFormRecyclerViewAdapter(Context context, DeptRequisition requisition) {
        setHasStableIds(true);
        mContext = context;
        mRequisition = requisition;
    }

    @NonNull
    @Override
    public EmployeeRequisitionFormRecyclerViewAdapter.RequisitionFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_requisition_form_row, parent, false);
        return new EmployeeRequisitionFormRecyclerViewAdapter.RequisitionFormViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeRequisitionFormRecyclerViewAdapter.RequisitionFormViewHolder holder, final int position) {
        // Called by the layout manager when it wants new data in an existing row

        final RequisitionDetail requisitionDetailItem = mRequisition.getRequisitionDetails().get(position);

        // set view widgets in store clerk requisition detail row here
        holder.requisitionFormItemId.setText(String.valueOf(requisitionDetailItem.getStationeryId()));
        holder.requisitionFormItemName.setText(requisitionDetailItem.getStationeryName());

        holder.requisitionFormQtyInput.setText(String.valueOf(requisitionDetailItem.getQty()));
        holder.requisitionFormQtyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 || Integer.parseInt(s.toString()) <= 0) {
                    mRequisition.getRequisitionDetails().get(position).setQty(0);
                }
                try {
                    mRequisition.getRequisitionDetails().get(position).setQty(Integer.parseInt(s.toString()));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
        //return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
        //return super.getItemViewType(position);
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

    public class RequisitionFormViewHolder extends RecyclerView.ViewHolder {

        TextView requisitionFormItemId = null;
        TextView requisitionFormItemName = null;
        EditText requisitionFormQtyInput = null;

        public RequisitionFormViewHolder(@NonNull View itemView) {
            super(itemView);
            this.requisitionFormItemId = (TextView) itemView.findViewById(R.id.requisitionFormItemId);
            this.requisitionFormItemName = (TextView) itemView.findViewById(R.id.requisitionFormItemName);
            this.requisitionFormQtyInput = (EditText) itemView.findViewById(R.id.requisitionFormQtyInput);

        }
    }
}
