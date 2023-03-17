package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.example.EmployeePayrollService.IOService.*;

public class EmployeePayrollService {

    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO, REST_IO
    }

    private List<EmployeePayrollData> employeePayrollList;
    private static final String FILE_PATH = "C:\\Users\\Icon\\IdeaProjects\\Employee_Payroll_Service\\src\\main\\resources\\Employee_Payroll.txt";

    public EmployeePayrollService() {

        employeePayrollList = new ArrayList<>();
    }

    public static void main(String[] args) {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData();

        for (int i = 0; i < 2; i++) {
            employeePayrollService.readEmployeePayrollData(consoleInputReader);
            employeePayrollService.writeEmployeePayrollData();
            employeePayrollService.printData(FILE_IO);
        }

        System.out.println(employeePayrollService.countEntries(CONSOLE_IO));
    }

    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.println("Enter Employee Name: ");
        String name = consoleInputReader.next();
        System.out.println("Enter Employee Salary: ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new EmployeePayrollData(id, name, salary));
    }

    public void writeEmployeePayrollData() {
        System.out.println("\nWriting Employee Payroll to console\n" + employeePayrollList);
        // public void writeEmployeePayrollData() {
        checkFile();
        StringBuffer empBuffer = new StringBuffer();
        employeePayrollList.forEach(employee -> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });
        try {
            Files.write(Paths.get(FILE_PATH), empBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        private void checkFile() {
            File file = new File(FILE_PATH);
            try {

                if (!file.exists()) {

                    file.createNewFile();
                    System.out.println("Created a file at " + FILE_PATH);
                }
            } catch (IOException e1) {
                System.err.println("Problem encountered while creating a file");
            }
        }
        public void printData(IOService ioService) {
            if (ioService.equals(FILE_IO))
                if (ioService.equals(IOService.FILE_IO))
                    new EmployeePayrollFileIOService().printData();

        }

        public long countEntries(IOService ioService) {
            long entries = 0;
            try {
                entries = Files.lines(new File(FILE_PATH).toPath()).count();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return entries;
        }
}
