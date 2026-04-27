package dao;
import model.Employee;

import java.util.List;

interface EmployeeDAO {
    int addEmployee(Employee emp);
    int updateEmployee(Employee emp);
    Employee getEmployee(int empId);
    List<Employee> getAllEmployee();
    int removeEmployee(int employeeId);
}
