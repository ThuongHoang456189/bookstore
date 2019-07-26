package pq.jdev.b001.bookstore.users.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.users.model.PasswordResetToken;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.repository.PasswordResetTokenRepository;
import pq.jdev.b001.bookstore.users.service.UserService;
import pq.jdev.b001.bookstore.users.web.dto.PasswordResetDto;

@PreAuthorize("!(hasRole('EMPLOYEE') OR hasRole('ADMIN'))")
@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordResetTokenRepository tokenRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("passwordResetForm")
	public PasswordResetDto passwordReset() {
		return new PasswordResetDto();
	}

	@GetMapping
	public String displayResetPasswordPage(@RequestParam(required = false) String token, Model model) {

		PasswordResetToken resetToken = tokenRepository.findByToken(token);
		if (resetToken == null) {
			model.addAttribute("error", "Could not find password reset token.");
		} else if (resetToken.isExpired()) {
			model.addAttribute("error", "Token has expired, please request a new password reset.");
		} else {
			model.addAttribute("token", resetToken.getToken());
		}

		return "reset-password";
	}

	@PostMapping
	@Transactional
	public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDto form,
			BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
			redirectAttributes.addFlashAttribute("passwordResetForm", form);
			return "redirect:/reset-password?token=" + form.getToken();
		}

		PasswordResetToken token = tokenRepository.findByToken(form.getToken());
		Person person = token.getPerson();
		String updatedPassword = passwordEncoder.encode(form.getPassword());
		userService.updatePassword(updatedPassword, person.getId());
		tokenRepository.delete(token);
		return "redirect:/login?resetSuccess";
	}
}
