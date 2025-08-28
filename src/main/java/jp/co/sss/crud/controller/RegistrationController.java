package jp.co.sss.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class RegistrationController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/regist/input")
    public String showRegistrationForm(@ModelAttribute("employee") EmployeeForm form, Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        return "/regist/regist_input";
    }

    @PostMapping("/regist/check")
    public String checkRegistration(@Valid @ModelAttribute("employee") EmployeeForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "/regist/regist_input";
        }
        Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());
        form.setDepartment(deptOptional.orElse(null));
        model.addAttribute("employee", form);
        return "/regist/regist_check";
    }

    @PostMapping("/regist/confirm")
    public String processRegistrationForm(@ModelAttribute("employee") EmployeeForm form, RedirectAttributes redirectAttributes) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(form, employee);

        Integer maxEmpId = employeeRepository.findTopByOrderByEmpIdDesc().map(Employee::getEmpId).orElse(0);
        employee.setEmpId(maxEmpId + 1);

        

        Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());
        employee.setDepartment(deptOptional.get());

        employeeRepository.save(employee);
        redirectAttributes.addFlashAttribute("message", "社員の登録が完了しました。");

        return "redirect:/regist/complete";
    }

    @GetMapping("/regist/complete")
    public String showRegistrationComplete() {
        return "/regist/regist_complete";
    }

    @PostMapping("/regist/return")
    public String returnToInput(@ModelAttribute("employee") EmployeeForm form, Model model) {
        model.addAttribute("employee", form);
        model.addAttribute("departments", departmentRepository.findAll());
        return "/regist/regist_input";
    }
}