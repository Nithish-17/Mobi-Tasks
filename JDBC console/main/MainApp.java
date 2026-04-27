package main;
import model.Employee;

import java.util.List;
import java.util.Scanner;
import dao.EmployeeDAOImpl;

import javax.swing.plaf.IconUIResource;

public class MainApp {
    public static void main(String[] args) {
        while(true){
            int choice;
            System.out.println("Enter 1 to add Employee ");
            System.out.println("Enter 2 to delete Employee ");
            System.out.println("Enter 3 to get Employee by id ");
            System.out.println("Enter 4 to get total Employees ");
            System.out.println("Enter 5 to update Employee ");
            System.out.println("Enter 6 to exit ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice){
                case 1 -> {
                    System.out.println("Enter the employee details to add : ");
                    System.out.print("Enter employee id : ");
                    int empId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter employee name : ");
                    String empName = scanner.nextLine();
                    System.out.print("Enter employee salary : ");
                    double empSalary = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter employee designation : ");
                    String empDesignation = scanner.nextLine();

                    Employee emp = new Employee(empId,empName,empSalary,empDesignation);
                    int result = EmployeeDAOImpl.getInstance().addEmployee(emp);
                    if(result > 0)
                        System.out.println("Employee added successfully");
                    else
                        System.out.println("Something error happened");

                }
                case 2 -> {
                    System.out.print("Enter the Employee id to delete : ");
                    int empId = scanner.nextInt();
                    int result = EmployeeDAOImpl.getInstance().removeEmployee(empId);
                    if(result > 0)
                        System.out.println("Employee deleted successfully");
                    else
                        System.out.println("employee not found");
                }
                case 3 -> {
                    System.out.print("Enter EmployeeId to get details : ");
                    int empId = scanner.nextInt();
                    Employee emp = EmployeeDAOImpl.getInstance().getEmployee(empId);
                    if(emp != null){
                        System.out.println("Employee id : " + emp.getId());
                        System.out.println("Employee name : " + emp.getName());
                        System.out.println("Employee salary : " + emp.getSalary());
                        System.out.println("Employee designation : " + emp.getDesignation());
                    }
                    else
                        System.out.println("Employee not found");
                }
                case 4 -> {
                    List<Employee> listOfEmployee = EmployeeDAOImpl.getInstance().getAllEmployee();
                    if(listOfEmployee.isEmpty()) {
                        System.out.println("no Employees to print");
                        return;
                    }
                    for(Employee emp : listOfEmployee){
                        System.out.println("Employee id : " + emp.getId());
                        System.out.println("Employee name : " + emp.getName());
                        System.out.println("Employee salary : " + emp.getSalary());
                        System.out.println("Employee designation : " + emp.getDesignation());
                    }

                }
                case 5 -> {
                    System.out.print("Enter Employee id : ");
                    int empId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Employee Name : ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Employee salary : ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter Employee designatin : ");
                    String designation = scanner.nextLine();
                    Employee emp = new Employee(empId,name,salary,designation);
                    int result = EmployeeDAOImpl.getInstance().updateEmployee(emp);
                    if(result > 0)
                        System.out.println("Employee updated successfully");
                    else
                        System.out.println("Employee not found");

                }
                case 6 -> {
                    System.out.println("Exiting.........");
                    return;
                }
                default -> System.out.println("------You have entered a wrong choice -------\n ----------try again---------");
            }

        }


    }
}
