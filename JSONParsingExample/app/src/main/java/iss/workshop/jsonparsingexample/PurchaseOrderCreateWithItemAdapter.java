package iss.workshop.jsonparsingexample;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.jsonparsingexample.Models.Item;
import iss.workshop.jsonparsingexample.Models.PO;
import iss.workshop.jsonparsingexample.Models.PODetails;
import iss.workshop.jsonparsingexample.Models.POItems;

public class PurchaseOrderCreateWithItemAdapter extends RecyclerView.Adapter<PurchaseOrderCreateWithItemAdapter.SupplierCreateWithItemHolder> {

    //private static final String TAG = "SupplierCreateAdapter";
   //List<Item> itemList;
    Context context;
    private static final String TAG = "Purchase Order Item Adapter";
    //PO poList = new ArrayList<>();
    POItems mPoItem;

    public PurchaseOrderCreateWithItemAdapter(Context ct, POItems il){
        context = ct;
        mPoItem = il;
    }

    protected POItems getPoDetailsList() {
        return mPoItem;
    }


    @NonNull
    @Override
    public SupplierCreateWithItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_create_with_item_row,parent,false);

        return new SupplierCreateWithItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SupplierCreateWithItemHolder holder, final int position) {

        final PODetails pdDetail = mPoItem.getPoDetailsList().get(position);

        Log.d(TAG, "onBindViewHolder: " + pdDetail.getStationaryDescription() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + pdDetail.getUnitPrice() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + pdDetail.getPredictionQty() + " --> " + position);
        holder.itemName.setText(pdDetail.getStationaryDescription());
        holder.unitPrice.setText(String.valueOf(pdDetail.getUnitPrice()));
        holder.prediction.setText(String.valueOf(pdDetail.getPredictionQty()));
        holder.qty.setText(String.valueOf(pdDetail.getQty()));
        holder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){

                    mPoItem.getPoDetailsList().get(position).setQty(0);
                }
                try{
                    mPoItem.getPoDetailsList().get(position).setQty(Integer.parseInt(s.toString()));
                }catch(Exception e){
                    Log.e(TAG, "edittext data : " +e.getMessage()+ " --> " + position);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPoItem.getPoDetailsList().size();
    }

    public class SupplierCreateWithItemHolder extends RecyclerView.ViewHolder{
        TextView itemName = null;
        TextView unitPrice = null;
        TextView prediction = null;
        EditText qty = null;

        public SupplierCreateWithItemHolder(@NonNull View itemView) {
            super(itemView);

            this.itemName = itemView.findViewById(R.id.itemName);
            this.unitPrice = itemView.findViewById(R.id.unitPrice);
            this.prediction = itemView.findViewById(R.id.prediction);
            this.qty = itemView.findViewById(R.id.suppliedQty);
        }
    }


    void loadNewData(POItems newStocks) {
        mPoItem = newStocks;
        notifyDataSetChanged();
    }

}
