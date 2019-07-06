package pq.jdev.b001.bookstore.books.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.books.repository.CategoryRepository;
import pq.jdev.b001.bookstore.books.repository.NewBookRepository;
import pq.jdev.b001.bookstore.books.repository.PublisherRepository;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.books.model.Category;
import pq.jdev.b001.bookstore.books.model.NewBook;
import pq.jdev.b001.bookstore.books.model.Publisher;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.model.SelectPublisher;

@Controller
public class BookController {
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private NewBookRepository newBookRepository;
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public String home(Model model)
	{
		return "index";
	}
	
	@GetMapping("/bookview")
	public String homeBook(Model model)
	{
		return "bookview/indextemp";
	}
	
	@ModelAttribute("dto")
	public UploadInformationDTO dTO ()
	{
		return new UploadInformationDTO();
	}
	
	@GetMapping("/bookview/createform")
	public String InitializeCreateBook(UploadInformationDTO dto, RedirectAttributes redirectAttributes)
	{
		List<Publisher> publishers = publisherRepository.findAll();
		
		for(int i = 0; i < publishers.size(); i++) {
            System.out.println(publishers.get(i).getName_publisher());
        }
		
		dto.setListpublishers(publishers);
		
		List<Category> categories = categoryRepository.findAll();
		
		for(int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i).getName());
        }
		
		dto.setListcategories(categories);
		
		redirectAttributes.addAttribute("dto", dto);
		return "redirect:/bookview/createform";
	}
	
	@PostMapping("/bookview/createform")
	public String CreatBookInputHandler( UploadInformationDTO dto, Model model, RedirectAttributes redirectAttributes)
	{
		try {
			long personid = -1;
			//Connect to function to get the personid after login from user.
			bookService.uploadBook(personid, dto);
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
	
	@GetMapping("/bookview/editbook/{id}")
	public String BookEditInitialize(@PathVariable(name = "id", required = true) long bookid, UploadInformationDTO dto, Model model, RedirectAttributes redirectAttributes)
	{
		try {
			NewBook bookfind = newBookRepository.findByID(bookid);
			long personid = -1;
			//Connect to function to get the personid after login from user.
			if(bookfind.getPersonid()==personid)
			{
				try {
					
					dto.setTitle(bookfind.getTitle());
					dto.setPrice(bookfind.getPrice());
					dto.setDomain(bookfind.getDomain());
					dto.setAuthors(bookfind.getAuthors());
					
					List<Publisher> publishers = publisherRepository.findAll();
					
					List<SelectPublisher> selectPublishers = new ArrayList<>();
					
					SelectPublisher temp = new SelectPublisher();
					
					for(int i = 0; i < publishers.size(); i++) {
			            System.out.println(publishers.get(i).getName_publisher());
			            temp.setPublisher(publishers.get(i));
			            
			            if(bookfind.getPublisher().getName_publisher().equals(publishers.get(i).getName_publisher()))
			            {
			            	temp.setFlag(1);
			            }
			            selectPublishers.add(temp);
			        }
					
					dto.setSelectPublishers(selectPublishers);
					
					List<Category> categories = categoryRepository.findAll();
					
					List<SelectCategory> selectCategories = new ArrayList<>();
					
					SelectCategory temp1 = new SelectCategory();
					
					for(int i = 0; i < categories.size(); i++) {
			            System.out.println(categories.get(i).getName());
			            for(Category o : bookfind.getCategories())
			            {
			            	if(o.getCategoryid()==categories.get(i).getCategoryid())
			            	{
			            		temp1.setFlag(1);
			            	}
			            }
			            selectCategories.add(temp1);
			        }
					
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
	
	@PostMapping("/bookview/editform/{id}")
	public String editBookInputHandler(Model model, UploadInformationDTO dto, RedirectAttributes redirectAttributes)
	{
		try {
			long personid = -1;
			//Connect to function to get the personid after login from user.
			bookService.uploadBook(personid, dto);
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
