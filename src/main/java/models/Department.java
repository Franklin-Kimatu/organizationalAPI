package models;

import java.util.Objects;

public class Department {
    //getters and setters for department name

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    //getters and setters for department description

    public String getDepartmentDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //getters and setters of employees numbers
    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return employeesNumber == that.employeesNumber &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName, description, employeesNumber);
    }

    private  String departmentName;
    private String description;
    private int employeesNumber;

    public Department(String departmentName,String description,int employeesNumber){
        this.departmentName =departmentName;
        this.description =description;
        this.employeesNumber =employeesNumber;
    }


}
