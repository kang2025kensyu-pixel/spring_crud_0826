package jp.co.sss.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class ListController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/list")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "list/list";
    }

    @GetMapping("/list/empName")
    public String findByEmpName(@RequestParam("empName") String empName, Model model) {
        List<Employee> employees;
        if (empName == null || empName.isEmpty()) {
            employees = employeeRepository.findAllByOrderByEmpIdAsc();
        } else {
            employees = employeeRepository.findByEmpNameLikeOrderByEmpIdAsc("%" + empName + "%");
        }

        model.addAttribute("employees", employees);
        if (employees.isEmpty()) {
            model.addAttribute("message", "該当する社員情報はありません。");
        }
        model.addAttribute("empName", empName);
        model.addAttribute("departments", departmentRepository.findAll());
        return "list/list";
    }
    
    @GetMapping("/list/deptId")
    public String findByDeptId(@RequestParam("deptId") Integer deptId, Model model) {
        List<Employee> employees;
        if (deptId == null) {
            // Case for no search condition
            employees = employeeRepository.findAllByOrderByEmpIdAsc();
        } else {
            // Case for department search
            employees = employeeRepository.findByDepartment_DeptIdOrderByEmpIdAsc(deptId);
        }

        model.addAttribute("employees", employees);
        if (employees.isEmpty()) {
            model.addAttribute("message", "該当する社員情報はありません。");
        }
        model.addAttribute("selectedDeptId", String.valueOf(deptId));
        model.addAttribute("departments", departmentRepository.findAll());
        return "list/list";
    }
}