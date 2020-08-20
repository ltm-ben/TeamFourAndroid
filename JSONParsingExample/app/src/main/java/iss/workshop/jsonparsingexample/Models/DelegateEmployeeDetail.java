package iss.workshop.jsonparsingexample.Models;

import java.util.Date;

public class DelegateEmployeeDetail {
    private int id;
    private Date startDate;
    private Date endDate;
    private Employee employee;
    private DelegatedEmployee delegatedEmployee;

    public DelegateEmployeeDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public DelegatedEmployee getDelegatedEmployee() {
        return delegatedEmployee;
    }

    public void setDelegatedEmployee(DelegatedEmployee delegatedEmployee) {
        this.delegatedEmployee = delegatedEmployee;
    }

    @Override
    public String toString() {
        return "DelegateEmployeeDetail{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
