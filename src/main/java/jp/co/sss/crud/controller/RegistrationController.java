package jp.co.sss.crud.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String checkRegistration(@ModelAttribute("employee") EmployeeForm form, Model model) {
        Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());
        if (deptOptional.isPresent()) {
            form.setDepartment(deptOptional.get());
            model.addAttribute("employee", form);
            return "/regist/regist_check";
        } else {
           
            return "redirect:/regist/input";
        }
    }


    @PostMapping("/regist/confirm")
    public String processRegistrationForm(@ModelAttribute("employee") EmployeeForm form, RedirectAttributes redirectAttributes) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(form, employee);

        // 日付変換処理を追加
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate parsedDate = LocalDate.parse(form.getBirthday(), formatter);
        String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // H2が受け入れる形式
        employee.setBirthday(formattedDate);

        Integer maxEmpId = employeeRepository.findTopByOrderByEmpIdDesc().map(Employee::getEmpId).orElse(0);
        employee.setEmpId(maxEmpId + 1);

        Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());
        
        if (deptOptional.isPresent()) {
            employee.setDepartment(deptOptional.get());
        } else {
            return "redirect:/regist/input";
        }

        employeeRepository.save(employee);
        redirectAttributes.addFlashAttribute("message", "社員の登録が完了しました。");

        return "/regist/regist_complete";
    }



    @GetMapping("/regist/complete")
    public String showRegistrationComplete() {
        return "/regist/regist_complete";
    }

    @PostMapping("/regist/return")
    public String returnToInput(@ModelAttribute("employee") EmployeeForm form, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("employee", form);
        return "redirect:/regist/input";
    }
}