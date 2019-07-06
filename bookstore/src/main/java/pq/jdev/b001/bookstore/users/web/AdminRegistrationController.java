package pq.jdev.b001.bookstore.users.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.AdminDto;

@Controller
@RequestMapping("/admin/registrationadmin")
public class AdminRegistrationController {

	@Autowired
	private UserService userService;

	@ModelAttribute("person")
	public AdminDto adminDto() {
		return new AdminDto();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registrationadmin";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("person") @Valid AdminDto userDto,
			BindingResult result) {
		
		Person existingUserName = userService.findByUsername(userDto.getUserName());
		Person existingEmail = userService.findByEmail(userDto.getEmail());
		if (existingEmail != null && existingUserName != null) {
			result.rejectValue("email", null, "There is already an account registered");
		}

		if (result.hasErrors()) {
			return "registrationadmin";
		}

		userService.save(userDto);
		return "redirect:/registrationadmin?success";
	}
}
