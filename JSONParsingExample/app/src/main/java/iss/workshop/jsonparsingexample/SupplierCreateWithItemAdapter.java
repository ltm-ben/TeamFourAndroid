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

import iss.workshop.jsonparsingexample.Models.Item;

public class SupplierCreateWithItemAdapter extends RecyclerView.Adapter<SupplierCreateWithItemAdapter.SupplierCreateWithItemHolder> {

    private static final String TAG = "SupplierCreateAdapter";
   List<Item> itemList;
    Context context;

    public SupplierCreateWithItemAdapter(Context ct,List<Item> il){
        context = ct;
        itemList = il;
    }


    @NonNull
    @Override
    public SupplierCreateWithItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplier_create_with_item_row,parent,false);

        return new SupplierCreateWithItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierCreateWithItemHolder holder, int position) {

        Item item = itemList.get(position);
        Log.d(TAG, "onBindViewHolder: " + item.getName() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + item.getPrediction() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + item.getUnitPrice() + " --> " + position);
        holder.itemName.setText(item.getName());
        holder.unitPrice.setText(String.valueOf(item.getUnitPrice()));
        holder.prediction.setText(String.valueOf(item.getPrediction()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class SupplierCreateWithItemHolder extends RecyclerView.ViewHolder{
        TextView itemName = null;
        TextView unitPrice = null;
        TextView prediction = null;

        public SupplierCreateWithItemHolder(@NonNull View itemView) {
            super(itemView);

            this.itemName = itemView.findViewById(R.id.itemName);
            this.unitPrice = itemView.findViewById(R.id.unitPrice);
            this.prediction = itemView.findViewById(R.id.prediction);
        }
    }
}
