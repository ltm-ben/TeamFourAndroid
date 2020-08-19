package iss.workshop.jsonparsingexample.Models;

public class Stationery {

    private int mId;
    private String mItemNumber;
    private Category mCategory;
    private String mDescription;
    private int mReorderLevel;
    private UOM mUom;

    public Stationery() {
    }

    public Stationery(int id, String itemNumber, Category category, String description, int reorderLevel, UOM uom) {
        mId = id;
        mItemNumber = itemNumber;
        mCategory = category;
        mDescription = description;
        mReorderLevel = reorderLevel;
        mUom = uom;
    }

    public int getId() {
        return mId;
    }

    public String getItemNumber() {
        return mItemNumber;
    }

    public Category getCategory() {
        return mCategory;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getReorderLevel() {
        return mReorderLevel;
    }

    public UOM getUom() {
        return mUom;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setItemNumber(String itemNumber) {
        mItemNumber = itemNumber;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setReorderLevel(int reorderLevel) {
        mReorderLevel = reorderLevel;
    }

    public void setUom(UOM uom) {
        mUom = uom;
    }

    @Override
    public String toString() {
        return "Stationery{" +
                "mId=" + mId +
                ", mItemNumber='" + mItemNumber + '\'' +
                ", mCategory=" + mCategory +
                ", mDescription='" + mDescription + '\'' +
                ", mReorderLevel=" + mReorderLevel +
                ", mUom=" + mUom +
                '}';
    }
}
