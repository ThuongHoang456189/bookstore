package pq.jdev.b001.bookstore.books.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String home(Model model)
	{
		return "index";
	}
	
	@GetMapping("/upload/book/success")
	public String successfulNoticeCreated(Model model)
	{
		return "index";
	}
	
	@GetMapping("/upload/book/fail")
	public String failedNoticeCreated(Model model)
	{
		return "upload";
	}
}
