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

import java.util.List;

import iss.workshop.jsonparsingexample.Models.Item;

public class SupplierCreateWithItemAdapter extends RecyclerView.Adapter<SupplierCreateWithItemAdapter.SupplierCreateWithItemHolder> {

    private static final String TAG = "SupplierCreateAdapter";
   List<Item> itemList;
    Context context;

    private List<Item> mDataSet;
    private OnEditTextChanged onEditTextChanged;

    public SupplierCreateWithItemAdapter(Context ct,List<Item> il,OnEditTextChanged onEditTextChanged){
        context = ct;
        itemList = il;
        this.onEditTextChanged = onEditTextChanged;
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

        Item item = itemList.get(position);
        Log.d(TAG, "onBindViewHolder: " + item.getName() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + item.getPrediction() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + item.getUnitPrice() + " --> " + position);
        holder.itemName.setText(item.getName());
        holder.unitPrice.setText(String.valueOf(item.getUnitPrice()));
        holder.prediction.setText(String.valueOf(item.getPrediction()));
        holder.qty.setText(String.valueOf(item.getQty()));

        holder.qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onEditTextChanged.onTextChanged(R.id.suppliedQty,position, holder.qty.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

    public interface OnEditTextChanged {
        void onTextChanged(int suppliedQty, int position, String charSeq);
    }
}
