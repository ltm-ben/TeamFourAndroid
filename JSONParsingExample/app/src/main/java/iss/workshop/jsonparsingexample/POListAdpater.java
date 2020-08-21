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

import iss.workshop.jsonparsingexample.Models.Item;
import iss.workshop.jsonparsingexample.Models.PO;

public class POListAdpater extends RecyclerView.Adapter<POListAdpater.MyViewHolder> {

    private static final String TAG = "Purchase Order Adapter";
    List<PO> poList = new ArrayList<>();
    Context context;

    public POListAdpater(Context ct, List<PO> pList){
        context = ct;
        poList = pList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.po_list_row,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PO purchaseOrder = poList.get(position);
        Log.d(TAG, "onBindViewHolder: " + purchaseOrder.getId() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + purchaseOrder.getSupplierName() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + purchaseOrder.getOrderDate() + " --> " + position);

        holder.id.setText(String.valueOf(purchaseOrder.getId()) );
        holder.orderDate.setText(purchaseOrder.getOrderDate());
        holder.supplierName.setText(purchaseOrder.getSupplierName());
        holder.status.setText(purchaseOrder.getStatus());
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,orderDate= null;TextView supplierName = null ; TextView status= null;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.poId);
            this.orderDate = itemView.findViewById(R.id.poOrderDate);
            this.supplierName = itemView.findViewById(R.id.poSupplierName);
            this.status = itemView.findViewById(R.id.poStatus);


        }
    }
}
