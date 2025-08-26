package jp.co.sss.crud.controller;

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

import jp.co.sss.crud.bean.EmployeeBean;
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

			EmployeeBean employeeBean = new EmployeeBean();
			BeanUtils.copyProperties(employee, employeeBean);
			//BeanUtils.copyProperties(form, employee, "empId");

			model.addAttribute("employee", employeeBean);
			return "/update/update_input";
		} else {
			return "redirect:/list";
		}
	}

	@PostMapping("/update/complete_check/{empId}")
	public String checkUpdate(@PathVariable("empId") Integer empId, @ModelAttribute("employee") EmployeeForm form,
			Model model) {
		Employee employee = employeeRepository.findByEmpId(empId).get();
		BeanUtils.copyProperties(form, employee, "empId");
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
	@PostMapping("/update/complete/{empId}")
	public String updateRecord(@PathVariable("empId") Integer empId, @ModelAttribute EmployeeBean bean) {

	
	    Employee existingEmp = employeeRepository.findById(empId)
	        .orElseThrow(() -> new RuntimeException("Employee not found"));

	    // IDは変更しないよう除外してコピー
	    BeanUtils.copyProperties(bean, existingEmp, "empId", "department");

	    // 部署情報を取得してセット
	    Department dept = departmentRepository.findById(bean.getDeptId())
	        .orElseThrow(() -> new RuntimeException("Department not found"));
	    existingEmp.setDepartment(dept);

	    // 更新保存
	    employeeRepository.save(existingEmp);

	    return "redirect:/update/complete";
	}


	@PostMapping("/update/back")
	public String returnToInput(@ModelAttribute("employee") EmployeeForm form, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("employee", form);
		return "redirect:/update/input/" + form.getEmpId();
	}
}