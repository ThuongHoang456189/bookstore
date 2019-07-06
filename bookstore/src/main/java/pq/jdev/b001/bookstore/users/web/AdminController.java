package pq.jdev.b001.bookstore.users.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.repository.UserRepository;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.UserUpdateInfoDto;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/listUser")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute("list")
	public List<Person> getList(Principal principal) {
		String username = principal.getName(); 
		Person per = userRepository.findByUsername(username);
		List<Person> oldList = userService.findAll(); 
		List<Person> newList = new ArrayList<Person>();
		int key = per.getPower();
		for (Person p : oldList) 
		if(p.getPower() <= key){
			newList.add(p);
		}
		return newList;
	}
	
	@ModelAttribute("person")
	public UserUpdateInfoDto updateInfoDto(Principal principal) {
		return userService.updateInfo(principal);
	}
	
	@GetMapping
	public String showUpdateInfoForm(Model model, Principal principal) {
		model.addAttribute("list", getList(principal));
		return "/listUser";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = { "/edit-user-{id}" })
	public String editUser(@PathVariable long id, ModelMap model) {
	     Person person  = userService.findById(id);
	     model.addAttribute("person", person);
	     model.addAttribute("edit", true);
	     return "registration";
	 }
	 
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = { "/edit-user-{id}" })
	public String updateUser(Person person, ModelMap model, @PathVariable long id) {
	     //userService.updateUser(person);
	     model.addAttribute("success", "Person " + person.getFirstname() + person.getLastname() + " updated successfully");
	     return "success";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = { "/delete-user-{id}" })
	public String deleteUser(@PathVariable long id, Principal principal) {
		int key_user_delete = userRepository.findById(id).getPower();
		int key_admin  = userRepository.findByUsername(principal.getName()).getPower();
	
		if (key_admin >= key_user_delete)
				userService.delete(id);
	    
		return "redirect:/listUser";
	}
}
