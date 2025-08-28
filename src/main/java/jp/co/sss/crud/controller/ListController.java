package jp.co.sss.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
   
        List<Employee> employees = employeeRepository.findAllByOrderByEmpIdAsc();

        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentRepository.findAll());
        return "list/list";
    }


    @GetMapping("/list/empName")
    public String findByEmpName(@RequestParam(value = "empName", required = false) String empName, Model model) {
        List<Employee> employees;
        if (empName != null && !empName.isEmpty()) {
            // Use the newly defined descending sort method
            employees = employeeRepository.findByEmpNameLikeOrderByEmpIdAsc("%" + empName + "%");
        } else {
            // If the search field is empty, return all employees in descending order
            employees = employeeRepository.findAllByOrderByEmpIdAsc();
        }

        model.addAttribute("employees", employees);
        if (employees.isEmpty()) {
            model.addAttribute("message", "該当する社員情報はありません。");
        }
        model.addAttribute("empName", empName);
        model.addAttribute("departments", departmentRepository.findAll());
        return "list/list";
    }

//    @GetMapping("/list/empName")
//    public String findByEmpName(@RequestParam(value = "empName", required = false) String empName, Model model) {
//        List<Employee> employees;
//        // Search by name if the name is not null or empty
//        if (empName != null && !empName.isEmpty()) {
//        	
//        	employees = ((Object) employeeRepository).findByEmpNameLikeOrderByEmpIdDesc("%" + empName + "%");
//        } else {
//            // If the search field is empty, return all employees
//            employees = employeeRepository.findAllByOrderByEmpIdAsc();
//        }
//
//        model.addAttribute("employees", employees);
//        if (employees.isEmpty()) {
//            model.addAttribute("message", "該当する社員情報はありません。");
//        }
//        model.addAttribute("empName", empName);
//        model.addAttribute("departments", departmentRepository.findAll());
//        return "list/list";
//    }
    @GetMapping("/list/deptId")
    public String findByDeptId(@RequestParam(value = "deptId", required = false) Integer deptId, Model model) {
        List<Employee> employees;
        if (deptId != null) {
            // Use the newly defined descending sort method
            employees = employeeRepository.findByDepartment_DeptIdOrderByEmpIdAsc(deptId);
        } else {
            // Case for no search condition, use descending sort
            employees = employeeRepository.findAllByOrderByEmpIdAsc();
        }

        model.addAttribute("employees", employees);
        if (employees.isEmpty()) {
            model.addAttribute("message", "該当する社員情報はありません。");
        }
        model.addAttribute("selectedDeptId", String.valueOf(deptId));
        model.addAttribute("departments", departmentRepository.findAll());
        return "list/list";
    }

//    @GetMapping("/list/deptId")
//    public String findByDeptId(@RequestParam(value = "deptId", required = false) Integer deptId, Model model) {
//        List<Employee> employees;
//        if (deptId != null) {
//        
//            employees = employeeRepository.findByDepartment_DeptIdOrderByEmpIdAsc(deptId);
//        } else {
//           
//            employees = employeeRepository.findAllByOrderByEmpIdAsc();
//        }
//
//        model.addAttribute("employees", employees);
//        if (employees.isEmpty()) {
//            model.addAttribute("message", "該当する社員情報はありません。");
//        }
//        model.addAttribute("selectedDeptId", String.valueOf(deptId));
//        model.addAttribute("departments", departmentRepository.findAll());
//        return "list/list";
//    }
}