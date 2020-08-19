package iss.workshop.jsonparsingexample.Models;

public class Stock {

    private int mId;
    private Stationery mStationery;
    private int mQty;
    private double mUnitPrice;
    private int mReorderLevel;
    private int mReorderQty;
    private int mForecast;

    public Stock() {
    }

    public Stock(int id, Stationery stationery, int qty, double unitPrice, int reorderLevel, int reorderQty, int forecast) {
        mId = id;
        mStationery = stationery;
        mQty = qty;
        mUnitPrice = unitPrice;
        mReorderLevel = reorderLevel;
        mReorderQty = reorderQty;
        mForecast = forecast;
    }

    public int getId() {
        return mId;
    }

    public Stationery getStationery() {
        return mStationery;
    }

    public int getQty() {
        return mQty;
    }

    public double getUnitPrice() {
        return mUnitPrice;
    }

    public int getReorderLevel() {
        return mReorderLevel;
    }

    public int getReorderQty() {
        return mReorderQty;
    }

    public int getForecast() {
        return mForecast;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setStationery(Stationery stationery) {
        mStationery = stationery;
    }

    public void setQty(int qty) {
        mQty = qty;
    }

    public void setUnitPrice(double unitPrice) {
        mUnitPrice = unitPrice;
    }

    public void setReorderLevel(int reorderLevel) {
        mReorderLevel = reorderLevel;
    }

    public void setReorderQty(int reorderQty) {
        mReorderQty = reorderQty;
    }

    public void setForecast(int forecast) {
        mForecast = forecast;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "mId=" + mId +
                ", mQty=" + mQty +
                ", mUnitPrice=" + mUnitPrice +
                ", mReorderLevel=" + mReorderLevel +
                ", mReorderQty=" + mReorderQty +
                ", mForecast=" + mForecast +
                '}';
    }
}
