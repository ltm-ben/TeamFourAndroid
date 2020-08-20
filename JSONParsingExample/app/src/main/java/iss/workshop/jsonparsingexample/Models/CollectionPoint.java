package iss.workshop.jsonparsingexample.Models;

public enum CollectionPoint {
    STATIONERY_STORE ("STATIONERY_STORE"),
    MANAGEMENT_SCHOOL ("MANAGEMENT_SCHOOL"),
    MEDICAL_SCHOOL ("MEDICAL_SCHOOL"),
    ENGINEERING_SCHOOL ("ENGINEERING_SCHOOL"),
    SCIENCE_SCHOOL ("SCIENCE_SCHOOL"),
    UNIVERSITY_HOSPITAL ("UNIVERSITY_HOSPITAL");
    private String mCollectionPoint;

    CollectionPoint(String mCollectionPoint) {
        this.mCollectionPoint = mCollectionPoint;
    }

    @Override
    public String toString() {
        return mCollectionPoint;
    }
}
