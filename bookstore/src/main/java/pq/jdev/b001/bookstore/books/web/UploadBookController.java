package pq.jdev.b001.bookstore.books.web;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pq.jdev.b001.bookstore.books.repository.NewBookRepository;
import pq.jdev.b001.bookstore.books.service.BookService;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.models.NewBook;

@Controller
@RequestMapping(value = "/upload")
public class UploadBookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	NewBookRepository bookRepository;
	
	@RequestMapping(value = "upload/creatbook", method = RequestMethod.POST)
	public ModelAndView addNewBook(@Valid @RequestBody UploadInformationDTO dto, BindingResult validation, ModelMap  model)
	{
		long personid = -1;
		String messages="";
		if(bookService.uploadBook(personid, dto))
		{
			messages+="You have just uploaded successfully";
		}
		else
		{
			messages+="Sorry, there are some occured mistakes";
		}
		model.addAttribute("message", messages);
		model.addAttribute("uploadUrl", "upload");
		return new ModelAndView("redirect:/{uploadUrl}", model);
	}
	
	@RequestMapping(value = "upload/editbookid", method = RequestMethod.POST)
	public ModelAndView editBook(@RequestParam(value = "bookid") long bookid, ModelMap  model)
	{
		model.addAttribute("editbookid", "You are editing the book of id "+bookid+"...");
		model.addAttribute("uploadUrl", "upload");
		NewBook bookinfor = new NewBook();
		bookinfor = bookRepository.findByID(bookid);
		//Display out for user
		return new ModelAndView("redirect:/{uploadUrl}", model);
	}
	@RequestMapping(value = "upload/editbook", method = RequestMethod.POST)
	public ModelAndView editBook(@Valid @RequestBody UploadInformationDTO dto, BindingResult validation, ModelMap  model )
	{
		long personid = -1;
		String messages="";
		if(bookService.uploadBook(personid, dto))
		{
			messages+="You have just uploaded successfully";
		}
		else
		{
			messages+="Sorry, there are some occured mistakes";
		}
		model.addAttribute("message", messages);
		model.addAttribute("uploadUrl", "upload");
		return new ModelAndView("redirect:/{uploadUrl}", model);
	}
}
