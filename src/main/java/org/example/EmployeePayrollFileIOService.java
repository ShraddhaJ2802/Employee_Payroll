package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollFileIOService {
    public static String PAYROLL_FILE_NAME = "C:\\Users\\Icon\\IdeaProjects\\Employee_Payroll_Service\\src\\main\\resources\\Employee_Payroll.txt";

    /*
     * Create a method WriteData ListOf EmployeePayrollDAta
     */

    public void writeData(List<EmployeePayrollData> employeePayrollList) {
        StringBuffer empBuffer = new StringBuffer();
        employeePayrollList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });
        /*
         * try and catch block IOExcepation
         */
        try {
            Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * try and catch block IOExcepation forEach method
     */
    public void printData() {
        try {
            Files.lines(new File(PAYROLL_FILE_NAME).toPath()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * try and catch block IOExcepation countEntries method
     */

    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    /*
     * try and catch block IOExcepation List<EmployeePayrollData> readData() method
     */

    public List<EmployeePayrollData> readData() {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        try {
            Files.lines(new File(PAYROLL_FILE_NAME).toPath()).map(line -> line.trim())
                    .forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    public static void main(String[] args) {
        List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollFileIOService emp = new EmployeePayrollFileIOService();
        emp.writeData(employeePayrollList);
        emp.readData();
        emp.printData();
        emp.countEntries();
    }

}
