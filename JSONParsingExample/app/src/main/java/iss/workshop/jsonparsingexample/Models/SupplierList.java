package iss.workshop.jsonparsingexample.Models;

public enum SupplierList {
    FairPrice ("FairPrice"),
    ColdStorage ("ColdStorage"),
    Giant ("Giant"),
    Metro ("Metro");

    private String mSupplier;

    SupplierList(String mSupplier) {
        this.mSupplier = mSupplier;
    }

    @Override
    public String toString() {
        return mSupplier;
    }
}