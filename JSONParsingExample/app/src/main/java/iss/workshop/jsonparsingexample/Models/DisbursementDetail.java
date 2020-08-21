package iss.workshop.jsonparsingexample.Models;

import java.util.Date;

public class DisbursementDetail {
        private int id;
        private int StationeryId;
        private String StationeryName;
        private int Qty;
        private String A_Date;
        public int DisbursementId;
        public String DeptName;

        public DisbursementDetail() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStationeryId() {
            return StationeryId;
        }

        public void setStationeryId(int stationeryId) {
            StationeryId = stationeryId;
        }

        public String getStationeryName() {
            return StationeryName;
        }

        public void setStationeryName(String stationeryName) {
            StationeryName = stationeryName;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int qty) {
            Qty = qty;
        }

        public String getA_Date(){return A_Date;}

        public void setA_Date(String a_date){A_Date= a_date;}

        public int getDisbursementId(){return DisbursementId;}

        public void setDisbursementId(int disbursementId){DisbursementId= disbursementId;}

        public String getDeptName(){return DeptName;}

        public void setDeptName(String deptName){DeptName= deptName;}

        @Override
        public String toString() {
            return "DisbursementDetail{" +
                    "id=" + id +
                    ", StationeryId=" + StationeryId +
                    ", StationeryName='" + StationeryName + '\'' +
                    ", Qty=" + Qty  +'\'' +
                    ", A_Date=" + A_Date + '\'' +
                    ", DisbursementId=" + DisbursementId + '\'' +
                    ", DeptName=" + DeptName +
                    '}';

        }
}
