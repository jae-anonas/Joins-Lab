package com.example.roosevelt.joins_lab;

/**
 * Created by roosevelt on 7/15/16.
 */
public class Job {
    String empSSN;
    String company;
    int salary;
    int experience;

    public Job(String empSSN, String company, int salary, int experience) {

        this.empSSN = empSSN;
        this.company = company;
        this.salary = salary;
        this.experience = experience;
    }

    public String getEmpSSN() {
        return empSSN;
    }

    public void setEmpSSN(String empSSN) {
        this.empSSN = empSSN;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
