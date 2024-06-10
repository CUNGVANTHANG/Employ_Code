package controller;

import entity.Department;
import func.DepartmentManager;
import view.DepartmentView;

import java.util.List;

public class DepartmentController {
    private DepartmentManager departmentManager;
    private DepartmentView departmentView;

    public DepartmentController(DepartmentManager departmentManager, DepartmentView departmentView) {
        this.departmentManager = departmentManager;
        this.departmentView = departmentView;
    }

    public void loadDepartments() {
        departmentView.displayDepartments(departmentManager.getAllDepartments());
    }

    public void addDepartment(Department department) {
        departmentManager.addDepartment(department);
        loadDepartments(); // Reload departments after adding
    }

    public void editDepartment(Department oldDepartment, Department newDepartment) {
        departmentManager.updateDepartment(oldDepartment, newDepartment);
        loadDepartments(); // Reload departments after editing
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