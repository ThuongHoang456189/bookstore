package pq.jdev.b001.bookstore.books.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.UserService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

	@ModelAttribute("dto")
	public UploadInformationDTO dTO() {
		return new UploadInformationDTO();
	}

	@PostMapping("/bookview")
	public String loadGuestView(Model model) {
		model.addAttribute("books", bookService.viewAllBooks());
		return "/bookview/books";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview")
	public String loadUserView(@AuthenticationPrincipal User user, Model model) throws Exception {
		String role = "guest";
		Person currentUser = userService.findByUsername(user.getUsername());
		if (currentUser.getPower() == 2) {
			role = "admin";
		} else if (currentUser.getPower() == 1) {
			role = "employee";
		}
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("role", role);
		model.addAttribute("books", bookService.viewAllBooks());
		return "/bookview/information";
	}

	@PostMapping("/bookview/books")
	public String loadInformationGuestView(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("books", bookService.viewAllBooks());
		return "bookview/books";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/information")
	public String loadInformationUserView(@AuthenticationPrincipal User user, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		String role = "guest";
		Person currentUser = userService.findByUsername(user.getUsername());
		if (currentUser.getPower() == 2) {
			role = "admin";
		} else if (currentUser.getPower() == 1) {
			role = "employee";
		}
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("role", role);
		model.addAttribute("books", bookService.viewAllBooks());
		return "bookview/information";
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/createform")
	public String initializeCreating(@AuthenticationPrincipal User user, UploadInformationDTO dto, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Person currentUser = new Person();
			currentUser = userService.findByUsername(user.getUsername());
			dto.setPublishers(bookService.showAllPublishers());
			dto.setCategories(bookService.showAllCategories());
			model.addAttribute("dto", dto);
			model.addAttribute("currentUser", currentUser);
			return "bookview/createform";
		} catch (Exception e) {
			return "bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PostMapping("/bookview/createform")
	public String createBook(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@RequestParam(value = "idChecked", required = false) List<String> categoriesId, Model model) {
		try {
			Person currentUser = userService.findByUsername(user.getUsername());
			if (!bookService.checkInput(dto)) {
				return "bookview/error";
			}
			bookService.save(dto, currentUser, categoriesId);
			return "bookview/success";
		} catch (Exception e) {
			return "bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@GetMapping("/bookview/editform/{id}")
	public String initializeEditing(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@PathVariable("id") String id, Model model) {
		try {
			dto = new UploadInformationDTO();
			Person currentUser = new Person();
			Long idEditBook = Long.parseLong(id);
			Book editBook = bookService.findBookByID(idEditBook);
			currentUser = userService.findByUsername(user.getUsername());
			if (!bookService.checkRightInteraction(user, editBook)) {
				return "/bookview/error";
			}
			model.addAttribute("currentUser", currentUser);
			dto.setTitle(editBook.getTitle());
			dto.setPrice(editBook.getPrice());
			dto.setDomain(editBook.getDomain());
			dto.setAuthors(editBook.getAuthors());
			dto.setPublishers(bookService.showAllPublishers());
			String currentSelected = editBook.getPublisher().getUpdateName();
			model.addAttribute("currentSelected", currentSelected);
			dto.setSelectCategories(bookService.showAllCategoriesWithFlag(editBook));
			model.addAttribute("id", editBook.getId());
			model.addAttribute("dto", dto);
			return "bookview/editform";
		} catch (Exception e) {
			return "/bookview/error";
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
	@PostMapping("/bookview/editform")
	public String editBook(@AuthenticationPrincipal User user, UploadInformationDTO dto,
			@RequestParam("id") String editBookId,
			@RequestParam(value = "idChecked", required = false) List<String> categoriesId, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Person currentUser = new Person();
			Long id = Long.parseLong(editBookId);
			Book editBook = bookService.findBookByID(id);
			currentUser = userService.findByUsername(user.getUsername());
			if (!bookService.checkRightInteraction(user, editBook)) {
				return "/login";
			}
			bookService.update(dto, currentUser, categoriesId, editBook);
			return "bookview/success";
		} catch (Exception e) {
			return "bookview/error";
		}
	}
}
