
package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class IndexController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Valid@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute LoginForm loginForm, HttpSession session) {
		session.invalidate();
		return "index";
	}

	@Valid @RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute LoginForm form, BindingResult result, HttpSession session, Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("errors", result.getAllErrors());
	        return "index";
	    }

	    Employee employee = employeeRepository.findById(form.getEmpId()).orElse(null);

	    if (employee != null && employee.getEmpPass().equals(form.getEmpPass())) {
	        session.setAttribute("user", employee);
	        System.out.println("ここまで");
	        model.addAttribute("user", employee);
	        System.out.println("ここまで2");
	        return "redirect:/list";
	    } else {
	        model.addAttribute("error", "社員IDまたはパスワードが正しくありません。");
	        return "index";
	    }
	}


	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
//	TODO　ログアウトボタン表示
//	エラーメッセージ表示
//	ログイン後名前確認
	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
	//		Optional<Employee> employeeOptional = employeeRepository.findByEmpId(form.getEmpId());
	//
	//
	//            Employee employee = employeeOptional.get();
	//            if (employee.getEmpPass().equals(form.getEmpPass())) {
	//                 return "redirect:/list"; // ログイン
	//               }
	//
	//      model.addAttribute("loginError", "社員IDまたはパスワードが正しくありません");
	//      return "index";
	//
	//		}

	@Valid@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/"; // Redirect to the login page
	}
}