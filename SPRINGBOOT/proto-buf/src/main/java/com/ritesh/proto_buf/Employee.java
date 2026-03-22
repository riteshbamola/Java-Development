package com.ritesh.proto_buf;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Employee {



    private int id;
    private String name;
    private double salary;
    private List<Department> departments;
    private Map<String, String> addressMap;
    private boolean isActive;
    private byte[] profilePicture;
    private Instant joinDate;



    public Employee() {
        this.departments = new ArrayList<>();
        this.addressMap  = new HashMap<>();
    }

    public Employee(int id, String name, double salary) {
        this();
        this.id       = id;
        this.name     = name;
        this.salary   = salary;
        this.isActive = true;
        this.joinDate = Instant.now();
    }



    public void addDepartment(Department dept)     { this.departments.add(dept); }
    public void removeDepartment(Department dept)  { this.departments.remove(dept); }
    public void addAddress(String key, String val) { this.addressMap.put(key, val); }
    public boolean hasProfilePicture()             { return profilePicture != null && profilePicture.length > 0; }



    public int getId()                               { return id; }
    public void setId(int id)                        { this.id = id; }
    public String getName()                          { return name; }
    public void setName(String name)                 { this.name = name; }
    public double getSalary()                        { return salary; }
    public void setSalary(double salary)             { this.salary = salary; }
    public List<Department> getDepartments()         { return departments; }
    public void setDepartments(List<Department> d)   { this.departments = d; }
    public Map<String, String> getAddressMap()       { return addressMap; }
    public void setAddressMap(Map<String, String> m) { this.addressMap = m; }
    public boolean isActive()                        { return isActive; }
    public void setActive(boolean active)            { this.isActive = active; }
    public byte[] getProfilePicture()                { return profilePicture; }
    public void setProfilePicture(byte[] pic)        { this.profilePicture = pic; }
    public Instant getJoinDate()                     { return joinDate; }
    public void setJoinDate(Instant joinDate)        { this.joinDate = joinDate; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        return id == ((Employee) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", departments=" + departments +
                ", addressMap=" + addressMap +
                ", isActive=" + isActive +
                ", hasProfilePic=" + hasProfilePicture() +
                ", joinDate=" + joinDate +
                '}';
    }
}