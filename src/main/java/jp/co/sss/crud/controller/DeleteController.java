package jp.co.sss.crud.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class DeleteController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/delete/confirm/{id}")
    public String showConfirmDeletePage(@PathVariable Integer id, Model model) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id); 

        if (employeeOpt.isPresent()) {
            model.addAttribute("employee", employeeOpt.get());
            return "delete/delete_check"; 
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        employeeRepository.deleteById(id);
        
        redirectAttributes.addFlashAttribute("message", "社員情報の削除が完了しました。");
        return "redirect:/delete_complete";
    }
    @GetMapping("/delete_complete")
    public String showDeleteCompletePage() {
        return "delete/delete_complete";
    }
}