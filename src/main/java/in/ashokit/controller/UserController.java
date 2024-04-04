package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.service.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl serviceImpl;

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";
	}

	@GetMapping("/forgot")
	public String forgotPasswordPage() {
		return "forgotPwd";
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		UnlockForm unlockForm = new UnlockForm();
		unlockForm.setEmail(email);

		model.addAttribute("unlock", unlockForm);
		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock, Model model) {

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {
			boolean status = serviceImpl.unlockAccount(unlock);
			if (status) {
				model.addAttribute("succMsg", "your account unlocked successfully");
			} else {
				model.addAttribute("errMsg", "Given Temporary password is incorrect, check your email");
			}
		} else {
			model.addAttribute("errMsg", "New Pwd and confirm password should be same");
		}
		return "unlock";
	}


	@PostMapping("/signup")
	public String signUpUserDetails(@ModelAttribute("user") SignUpForm form, Model model) {

		boolean status = serviceImpl.signUp(form);

		if (status) {
			model.addAttribute("successMsg", "user created, check your email");
		} else { 
			model.addAttribute("errorMsg", "Choose Unique Email");
		}

		return "signup";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm loginForm, Model model) {
		System.out.println("inside login form");

		System.out.println(loginForm);
		
		String status = serviceImpl.login(loginForm);
		
		if (status.contains("success")) {
			
			//display dashboard
			return "redirect:/dashboard";
		}
		
		model.addAttribute("errMsg", status);
		return "login";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotPassword(@RequestParam("email") String email, Model model) {
		System.out.println(email);
		
		boolean status = serviceImpl.forgotPwd(email);
		if (status) {
			model.addAttribute("succMsg", "Pwd sent to your email");
		} else {
			model.addAttribute("errMsg", "Invalid Email");
		}
		
		return "forgotPwd";
	}
}
