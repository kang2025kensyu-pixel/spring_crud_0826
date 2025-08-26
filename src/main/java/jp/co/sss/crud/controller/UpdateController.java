package jp.co.sss.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class UpdateController {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;
	

	@GetMapping("/update/input/{empId}")
	public String showUpdateForm(@PathVariable("empId") Integer empId, Model model) {
		Optional<Employee> employeeOpt = employeeRepository.findByEmpId(empId);

		if (employeeOpt.isPresent()) {
			Employee employee = employeeOpt.get();
			EmployeeForm form = new EmployeeForm();
			BeanUtils.copyProperties(employee, form);
			form.setDeptId(employee.getDepartment().getDeptId());
			model.addAttribute("employee", form);
			List<Department> departments = departmentRepository.findAll();
			model.addAttribute("departments", departments);

			return "/update/update_input";
		} else {
			return "redirect:/list";
		}
	}

	@PostMapping("/update/complete_check")
	public String checkUpdate(@PathVariable("empId") EmployeeForm form, Model model) {
//		Employee employee = employeeOpt.get();
//		Optional<Employee> employeeOpt = employeeRepository.findByEmpId(form.getEmpId());
		//       Employee employee = employeeOpt.get();
		//       Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());

		//====================================参考==========================

		//        Query query = entityManager.createNamedQuery("findByIdNamedQuery"); 
		//        query.setParameter("id", id); 
		//        model.addAttribute("items", query.getResultList()); 
		//        Optional<Department> findById(Integer deptId);

		//==============================================================
		 if (!StringUtils.hasText(form.getEmpName()) || !StringUtils.hasText(form.getEmpPass())
	                || !StringUtils.hasText(form.getAddress()) || !StringUtils.hasText(form.getBirthday())) {
	            model.addAttribute("error", "全ての項目を入力してください。");
	            model.addAttribute("departments", departmentRepository.findAll());
	            return "/update/update_input";
	     }
		 Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());
	        if (deptOptional.isPresent()) {
	            form.setDepartment(deptOptional.get());
	            model.addAttribute("employee", form);
	            return "/update/update_check";
	        } else {
	            model.addAttribute("error", "指定された部署が見つかりません。");
	            model.addAttribute("departments", departmentRepository.findAll());
	            return "/update/update_input";
	        }
	    }

	@PostMapping("/update/complete")
    public String updateRecord(@ModelAttribute("employee") EmployeeForm form, RedirectAttributes redirectAttributes) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmpId(form.getEmpId());
        
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate date = LocalDate.parse(inputDate, formatter);
            Integer maxEmpId = employeeRepository.findTopByOrderByEmpIdDesc().map(Employee::getEmpId).orElse(0);
            employee.setEmpId(maxEmpId + 1);
            System.out.println("setEmpId");
            Optional<Department> deptOptional = departmentRepository.findById(form.getDeptId());
            if (deptOptional.isPresent()) {
            	 System.out.println("deptOptionalです！！");
                employee.setDepartment(deptOptional.get());
            } else {
                return "redirect:/regist/input";
            }
            BeanUtils.copyProperties(form, employee, "empId");

            System.out.println("セーブマエです！！");
            employeeRepository.save(employee);
            System.out.println("SAVE完了");
            redirectAttributes.addFlashAttribute("message", "社員情報の変更が完了しました。");
            return "redirect:/update/complete_page";
        } else {
            return "redirect:/list";
        }
    }

	@GetMapping("/update/complete_page")
	public String showUpdateComplete() {
		return "/update/update_complete";
	}

	@PostMapping("/update/back")
	public String returnToInput(@ModelAttribute("employee") EmployeeForm form, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("employee", form);
		return "redirect:/update/input/" + form.getEmpId();
	}
}