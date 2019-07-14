package pq.jdev.b001.bookstore.books.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.repository.BookRepository;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.repository.UserRepository;
import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.repository.CategoryRepository;
import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.model.SelectPublisher;

@Controller 
public class BookController {
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookService bookService;
	
	@ModelAttribute("dto")
	public UploadInformationDTO dTO ()
	{
		return new UploadInformationDTO();
	}
	
	@GetMapping("/")
	public String home(Model model)
	{
		return "index";
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/bookview/{personid}")
	public String showAddEditView(@PathVariable Long personid, Model model)
	{
		//userservice find by id
		//Getrole user
		//userService getRoleById and userServiceGetCurrentUserId
		//check session personid and personid passed through URL
		
		int role = -1;
		List<Book> books = new ArrayList<Book>();
		if(role==2)
		{
			books = bookRepository.findAll();
		}
		else
		{
			//person = userRepository.findById(personid)
			books = bookRepository.findBooksByPersonId(personid);
		}
		
		//model.addAttribute("person", person);
		model.addAttribute("books", books);
		
		
		//redirect /bookview/{personid}
		return null;
		
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/bookview/{personid}/createform")
	public String InitializeCreateBook(@PathVariable Long personid, UploadInformationDTO dto, Model model, RedirectAttributes redirectAttributes)
	{
		//check the same person_person through URL and person_session
		List<Publishers> publishers = publisherRepository.findAll();
		List<SelectPublisher> selectPublishers = new ArrayList<>();
		
		for(int i = 0; i < publishers.size(); i++) {
            System.out.println(publishers.get(i).getUpdateName());
            SelectPublisher temp = new SelectPublisher(publishers.get(i), 0);
            selectPublishers.add(temp);
        }
		
		dto.setSelectPublishers(selectPublishers);
		
		List<Category> categories = categoryRepository.findAll();
//		List<SelectCategory> selectCategories = new ArrayList<>();
		
		for(int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i).getName());
//            SelectCategory temp = new SelectCategory(categories.get(i), 0);
//            selectCategories.add(temp);
        }
		
//		dto.setSelectCategories(selectCategories);
		dto.setCategories(categories);
		
		model.addAttribute("dto", dto);
		
		redirectAttributes.addAttribute("dto", dto);
		
		return "redirect:/bookview/{personid}/createform";
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/bookview/{personid}/createform")
	public String CreatBookInputHandler(@PathVariable Long personid, UploadInformationDTO dto, Model model, RedirectAttributes redirectAttributes, Person person)
	{
		try {
			
				//Connect to function to get the personid after login from user.
				LocalDate date = LocalDate.now();			
				Date uploadedDate = new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
			
				dto.setUploadedDate(uploadedDate);
				
			
				bookService.save(dto, person);
			
				System.out.println("Successful insert new book");
				return "bookview/success";
			
			}catch (Exception e) {
			// TODO: handle exception
				System.out.println("Error Controller CreatBookInputHandler: ****" + e.getMessage());
				return "bookview/error";
			}
	}
	
//	@GetMapping("/bookview/editbookid")
//	public String FirstEditBookID()
//	{
//		return "bookview/editbookid";
//	}
	
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping("/bookview/{personid}/editform/{bookid}")
	public String BookEditInitialize(@PathVariable(name = "personid", required = true) long personid, @PathVariable(name = "bookid", required = true) long bookid, UploadInformationDTO dto, Model model, RedirectAttributes redirectAttributes, Person person)
	{
		try {
				Book bookFound = bookRepository.findByBookId(bookid);
				if(bookFound != null) {
					
					try {
						
						dto.setTitle(bookFound.getTitle());
						dto.setPrice(bookFound.getPrice());
						dto.setDomain(bookFound.getDomain());
						dto.setAuthors(bookFound.getAuthors());
						Person personUpdated = userRepository.findById(personid);
						model.addAttribute("person", personUpdated);
						
						List<Publishers> publishers = publisherRepository.findAll();
						List<SelectPublisher> selectPublishers = new ArrayList<>();
						
						SelectPublisher temp = new SelectPublisher();
						
						for(int i = 0; i < publishers.size(); i++) {
				            System.out.println(publishers.get(i).getUpdateName());
				            temp.setPublisher(publishers.get(i));
				            
//				            if(bookFound.getPublisher().get.equals(publishers.get(i).getName_publisher()))
//				            {
//				            	temp.setFlag(1);
//				            }
//				            selectPublishers.add(temp);
				        }
						
						dto.setSelectPublishers(selectPublishers);
						
						List<Category> categories = categoryRepository.findAll();
						
						List<SelectCategory> selectCategories = new ArrayList<>();
						
						SelectCategory temp1 = new SelectCategory();
						
//						for(int i = 0; i < categories.size(); i++) {
//				            System.out.println(categories.get(i).getName());
//				            for(Category o : bookfind.getCategories())
//				            {
//				            	if(o.getCategoryid()==categories.get(i).getCategoryid())
//				            	{
//				            		temp1.setFlag(1);
//				            	}
//				            }
//				            selectCategories.add(temp1);
//				        }
						
						dto.setSelectCategories(selectCategories);
						
						redirectAttributes.addAttribute("dto", dto);
						model.addAttribute("dto", dto);
						
						return "redirect:/bookview/editform";
					}
					catch (Exception e) {
						// TODO: handle exception
						System.out.println("Error Controller InitializeEditBook : ****" + e.getMessage());
						return "bookview/error";
					}
				}
				else 
				{
					redirectAttributes.addFlashAttribute("You do not have right to edit this book");
					return "redirect:/bookview/editbookid";
				}
			
			}
			catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error Controller InitializeEditBook : ****" + e.getMessage());
				return "bookview/error";
			}
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping("/bookview/editform/{id}")
	public String editBookInputHandler(Model model, UploadInformationDTO dto, RedirectAttributes redirectAttributes)
	{
		try {
			long personid = -1;
			//Connect to function to get the personid after login from user.
			//bookService.save(dto, person);
			System.out.println("Successful edit a certain book");
			return "bookview/success";
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Controller editBookInputHandler: ****" + e.getMessage());
			return "bookview/error";
		}
	}
	
	@GetMapping("/bookview/error")
	public String failedNoticeCreated(Model model)
	{
		return "bookview/error";
	}
	
	@GetMapping("/bookview/success")
	public String successfulNoticeCreated(Model model)
	{
		return "bookview/error";
	}
}
