package iss.workshop.jsonparsingexample.Models;

import java.util.Date;
import java.util.List;

public class DelegatedEmployee {
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private DelegationStatus delegationStatus;
    private Employee employee;
    private List<DelegateEmployeeDetail> delegateEmployeeDetails;

    public DelegatedEmployee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public DelegationStatus getDelegationStatus() {
        return delegationStatus;
    }

    public void setDelegationStatus(DelegationStatus delegationStatus) {
        this.delegationStatus = delegationStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<DelegateEmployeeDetail> getDelegateEmployeeDetails() {
        return delegateEmployeeDetails;
    }

    public void setDelegateEmployeeDetails(List<DelegateEmployeeDetail> delegateEmployeeDetails) {
        this.delegateEmployeeDetails = delegateEmployeeDetails;
    }

    @Override
    public String toString() {
        return "DelegatedEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", delegationStatus=" + delegationStatus +
                '}';
    }
}
