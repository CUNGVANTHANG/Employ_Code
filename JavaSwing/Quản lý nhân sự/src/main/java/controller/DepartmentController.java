package controller;

import entity.Department;
import func.DepartmentManager;
import view.DepartmentView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DepartmentController {
    private DepartmentManager departmentManager;
    private DepartmentView departmentView;

    public DepartmentController(DepartmentManager departmentManager, DepartmentView departmentView) {
        this.departmentManager = departmentManager;
        this.departmentView = departmentView;
    }

    public void loadDepartments() {
        departmentManager.getAllDepartments().clear();
        List<Department> departments = departmentManager.getAllDepartments();

        Collections.sort(departments, new Comparator<Department>() {
            @Override
            public int compare(Department d1, Department d2) {
                return d1.getDepartmentName().compareTo(d2.getDepartmentName());
            }
        });

        departmentView.displayDepartments(departments);
    }

    public void addDepartment(Department department) {
        departmentManager.addDepartment(department);
        loadDepartments();
    }

    public void editDepartment(Department oldDepartment, Department newDepartment) {
        departmentManager.updateDepartment(oldDepartment, newDepartment);
        loadDepartments();
    }

    public void deleteDepartment(Department department) {
        departmentManager.removeDepartment(department);
        loadDepartments(); // Reload departments after deleting
    }

    public boolean isDepartmentIdUnique(String id) {
        for (Department department : departmentManager.getAllDepartments()) {
            if (department.getDepartmentID().equals(id)) {
                return false;
            }
        }
        return true;
    }

}