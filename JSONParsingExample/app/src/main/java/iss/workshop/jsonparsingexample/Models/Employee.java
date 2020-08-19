package iss.workshop.jsonparsingexample.Models;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String username;
    private String password;
    private DeptRole jobTitle;
    private DeptRole role;
    private Department dept;
    private List<DelegatedEmployee> delegatedEmployees;

    public Employee() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DeptRole getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(DeptRole jobTitle) {
        this.jobTitle = jobTitle;
    }

    public DeptRole getRole() {
        return role;
    }

    public void setRole(DeptRole role) {
        this.role = role;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    public List<DelegatedEmployee> getDelegatedEmployees() {
        return delegatedEmployees;
    }

    public void setDelegatedEmployees(List<DelegatedEmployee> delegatedEmployees) {
        this.delegatedEmployees = delegatedEmployees;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", jobTitle=" + jobTitle +
                ", role=" + role +
                '}';
    }
}
