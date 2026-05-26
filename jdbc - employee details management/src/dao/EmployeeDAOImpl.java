package dao;
import model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static util.DBConnection.getConnectionObj;

public class EmployeeDAOImpl implements EmployeeDAO{
    private static EmployeeDAOImpl impl = null;
    public static EmployeeDAOImpl getInstance(){
        if(impl == null)
            impl = new EmployeeDAOImpl();
        return impl;
    }
    //singleton class
    private EmployeeDAOImpl(){}


    @Override
    public int addEmployee(Employee emp) {
        String addQuery = "insert into employee_details values(?,?,?,?)";
        try(Connection conn = getConnectionObj()){
            PreparedStatement preStatement = conn.prepareStatement(addQuery);
            preStatement.setInt(1,emp.getId());
            preStatement.setString(2,emp.getName());
            preStatement.setDouble(3,emp.getSalary());
            preStatement.setString(4,emp.getDesignation());
            return preStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateEmployee(Employee emp) {
        String updateQuery = "update employee_details set name = ?, salary = ?, designation = ?";
        try(Connection conn = getConnectionObj())
        {
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1,emp.getName());
            updateStatement.setDouble(2,emp.getSalary());
            updateStatement.setString(3,emp.getDesignation());
            return updateStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public Employee getEmployee(int empId) {
        String getQuery = "select * from employee_details where emp_id = ?";
        try(Connection conn = getConnectionObj()){
            PreparedStatement getStatement = conn.prepareStatement(getQuery);
            getStatement.setInt(1,empId);
            ResultSet rs = getStatement.executeQuery();
            if(rs.next()){
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                String designation = rs.getString("designation");
                Employee emp = new Employee(empId,name,salary,designation);
                return emp;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployee() {
        String getAllQuery = "select * from employee_details";
        try(Connection conn = getConnectionObj()) {
            PreparedStatement getAllStatement = conn.prepareStatement(getAllQuery);
            ResultSet rs = getAllStatement.executeQuery();
            List<Employee> empList = new ArrayList<>();
            while(rs.next()){
                int empId = rs.getInt("emp_id");
                String name = rs.getString("name");
                double salary = rs.getDouble("salary");
                String designation = rs.getString("designation");
                empList.add(new Employee(empId,name,salary,designation));
            }
            return empList;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public int removeEmployee(int employeeId) {
        String removeQuery = "delete from employee_details where emp_id = ?";
        try(Connection conn = getConnectionObj()){
            PreparedStatement deleteStatement = conn.prepareStatement(removeQuery);
            deleteStatement.setInt(1,employeeId);
            return deleteStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
