package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.repository.UserRepository;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/accountAdmin")
public class AdminUpdateInfoController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("person")
	public UserUpdateInfoDto updateInfoDto(Principal principal) {
		String username = principal.getName(); 
		Person p = userRepository.findByUsername(username);
		return userService.updateInfo(p);
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
	
	@RequestMapping(value = "/changePassAdmin", method = RequestMethod.GET)
	public String showChangePassForm(Model model) {
		return "/changePassAdmin";
	}
	
	@RequestMapping(value = "/changePassAdmin", method = RequestMethod.POST)
	public String UpdatePassUserAccount(@ModelAttribute("person") @Valid UserUpdateInfoDto userDto,
			BindingResult result) {

	    if (result.hasErrors()) {
            return "/accountAdmin/changePassAdmin";
	    }
	    
	    String updatedPassword = passwordEncoder.encode(userDto.getPassword());
		userService.updatePassword(updatedPassword, userDto.getId());
		userService.loadUserByUsername(userDto.getUserName());
		return "redirect:/accountAdmin/changePassAdmin?success";
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
