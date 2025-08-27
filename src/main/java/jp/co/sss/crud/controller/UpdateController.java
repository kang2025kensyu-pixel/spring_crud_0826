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

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
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

	        model.addAttribute("employee", employeeBean);
	        model.addAttribute("departments", departmentRepository.findAll()); 
	        return "/update/update_input";
	    } else {
	        return "redirect:/list";
	    }
	}


	@PostMapping("/update/complete_check/{empId}")
	public String checkUpdate(@PathVariable("empId") Integer empId, @ModelAttribute("employee") EmployeeBean bean, Model model) {
	    // フォームデータの検証
	    if (!StringUtils.hasText(bean.getEmpName()) || !StringUtils.hasText(bean.getEmpPass())
	            || !StringUtils.hasText(bean.getAddress()) || !StringUtils.hasText(bean.getBirthday())) {
	        model.addAttribute("error", "全ての項目を入力してください。");
	        model.addAttribute("departments", departmentRepository.findAll());
	        return "/update/update_input";
	    }

	    Optional<Department> deptOptional = departmentRepository.findById(bean.getDeptId());
	    if (deptOptional.isPresent()) {
	        bean.setDepartment(deptOptional.get());
	        bean.setDeptName(bean.getDepartment().getDeptName());
	        
	        model.addAttribute("employee", bean);
	        return "/update/update_check";
	    } else {
	        model.addAttribute("error", "指定された部署が見つかりません。");
	        model.addAttribute("departments", departmentRepository.findAll());
	        return "/update/update_input";
	    }
	}
	
	@PostMapping("/update/complete/{empId}")
	public String updateRecord(@PathVariable("empId") Integer empId, @ModelAttribute EmployeeBean bean) {
	    if (empId == null) {
	        throw new IllegalArgumentException("情報がありません");
	    }

	    // 社員情報の取得と存在チェック
	    Employee existingEmp = employeeRepository.findById(empId)
	        .orElseThrow(() -> new RuntimeException("情報がありません"));

	    // 部署情報の取得と存在チェック
	    Department dept = departmentRepository.findById(bean.getDeptId())
	        .orElseThrow(() -> new RuntimeException("Department not found"));

	    // プロパティのコピー（部署は除外）
	    BeanUtils.copyProperties(bean, existingEmp, "empId", "department");

	    // 部署の設定
	    existingEmp.setDepartment(dept);

	 
	    employeeRepository.save(existingEmp);

	   
	    return "redirect:/list";
	}
	
	@GetMapping("/update/complete_page/{empId}")
	public String showUpdateComplete() {
		return "/update/update_complete";
	}



	@PostMapping("/update/back")
	public String returnToInput(@ModelAttribute("employee") EmployeeBean bean, Model model) {
	    model.addAttribute("employee", bean);
	    model.addAttribute("departments", departmentRepository.findAll());
	    return "/update/update_input";
	}
}