package org.example;

public class EmployeePayrollData {
    private int employeeId;
    private String employeeName;
    private double employeeSalary;

    public EmployeePayrollData(int employeeId, String employeeName, double employeeSalary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
    }

    /**
     * This method is Collecting the data From Employee
     */

    @Override
    public String toString() {
        return "EmployeePayrollData [\nemployeeId=" + employeeId + "\nemployeeName=" + employeeName + "\nemployeeSalary="
                + employeeSalary + "]";
    }
}
