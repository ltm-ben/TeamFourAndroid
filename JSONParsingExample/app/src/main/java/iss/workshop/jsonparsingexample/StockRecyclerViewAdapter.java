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

import iss.workshop.jsonparsingexample.Models.Stock;

public class StockRecyclerViewAdapter extends RecyclerView.Adapter<StockRecyclerViewAdapter.StockViewHolder> {

    private static final String TAG = "StockRecyclerViewAdapter";
    private List<Stock> mStockList;
    private Context mContext;

    public StockRecyclerViewAdapter(Context context, List<Stock> stockList) {
        mContext = context;
        mStockList = stockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        // Called by the layout manager when it wants new data in an existing row

        Stock stockItem = mStockList.get(position);
        Log.d(TAG, "onBindViewHolder: " + stockItem.getStationery().getDescription() + " --> " + position);
        Log.d(TAG, "onBindViewHolder: " + stockItem.getQty() + " --> " + position);
        holder.itemName.setText(stockItem.getStationery().getDescription());
        holder.stockQty.setText(String.valueOf(stockItem.getQty()));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return ((mStockList != null) && (mStockList.size() != 0) ? mStockList.size() : 0);
    }

    void loadNewData(List<Stock> newStocks) {
        mStockList = newStocks;
        notifyDataSetChanged();
    }

    public Stock getStock(int position) {
        return ((mStockList != null) && (mStockList.size() != 0) ? mStockList.get(position) : null);
    }

    static class StockViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "StockViewHolder";
        TextView itemName = null;
        TextView stockQty = null;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "StockViewHolder: starts");
            this.itemName = (TextView) itemView.findViewById(R.id.itemName);
            this.stockQty = (TextView) itemView.findViewById(R.id.stockQty);
        }
    }
}
