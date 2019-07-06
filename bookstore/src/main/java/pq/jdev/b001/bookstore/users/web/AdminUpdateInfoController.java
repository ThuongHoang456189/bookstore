package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

@Controller
@RequestMapping("/accountAdmin")
public class AdminUpdateInfoController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("person")
	public UserUpdateInfoDto updateInfoDto(Principal principal) {
		return userService.updateInfo(principal);
	}
	
	//update info
	
	@GetMapping
	public String showUpdateInfoForm(Model model) {
		return "/accountAdmin";
	}

	@PostMapping
	public String UpdateUserAccount(@ModelAttribute("person") @Valid UserUpdateInfoDto userDto,
			BindingResult result) throws Exception {

	    if (result.hasErrors()) {
            return "accountAdmin";
	    }
		userService.save(userDto);
		
		return "redirect:/accountAdmin?success";
	}
	
	//update pass
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String showChangePassForm(Model model) {
		return "/changePassword";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String UpdatePassUserAccount(@ModelAttribute("person") @Valid UserUpdateInfoDto userDto,
			BindingResult result) {

	    if (result.hasErrors()) {
            return "/myAccount/changePassword";
	    }
	    
	    String updatedPassword = passwordEncoder.encode(userDto.getPassword());
		userService.updatePassword(updatedPassword, userDto.getId());
		userService.loadUserByUsername(userDto.getUserName());
		return "redirect:/accountAdmin/changePassword?success";
	}
	
	// delete user
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
		userService.delete(id);
		return "redirect:/";
	}
}
