package controller;

import entity.Employee;
import func.EmployeeManager;
import view.EmployeeView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeController {
    private EmployeeManager employeeManager;
    private EmployeeView employeeView;

    public EmployeeController(EmployeeManager employeeManager, EmployeeView employeeView) {
        this.employeeManager = employeeManager;
        this.employeeView = employeeView;
    }

    public void loadEmployees() {
        employeeManager.getAllEmployees().clear();
        List<Employee> employees = employeeManager.getAllEmployees();

        // Sắp xếp các nhân viên theo tên
        Collections.sort(employees, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return e1.getName().compareTo(e2.getName());
            }
        });

        employeeView.displayEmployees(employees);
    }

    public void addEmployee(Employee employee) {
        employeeManager.addEmployee(employee);
        loadEmployees(); // Reload the employee list after adding
    }

    public void updateEmployee(Employee oldEmployee, Employee newEmployee) {
        employeeManager.updateEmployee(oldEmployee, newEmployee);
        loadEmployees(); // Reload the employee list after editing
    }

    public void deleteEmployee(Employee employee) {
        employeeManager.removeEmployee(employee);
        loadEmployees(); // Reload the employee list after deleting
    }

    public boolean isEmployeeIdUnique(String id) {
        for (Employee emp : employeeManager.getAllEmployees()) {
            if (emp.getId().equals(id)) {
                return false; // ID already exists
            }
        }
        return true; // ID is unique
    }

}
