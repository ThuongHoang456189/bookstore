//package pq.jdev.b001.bookstore.publishers.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import pq.jdev.b001.bookstore.models.Publishers;
//import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class MainController {
//	private final PublisherRepository publisherReponsitory;
//	
//    @Autowired
//    public MainController(PublisherRepository publisherReponsitory) {
//        this.publisherReponsitory = publisherReponsitory;
//    }
//	
//	@GetMapping("list")
//    	public String showUpdateForm(Model model) {
//        model.addAttribute("publishers", publisherReponsitory.findAll());
//        return "index";
//    }
//	
//    private static List<Publishers> publishers = new ArrayList<Publishers>();
// 
//    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
//    public String index(Model model) {
// 
//        return "index";
//    }
//    
//	@GetMapping("/publishersList")
//    public String viewPublishersList(Model model) {
// 
//        model.addAttribute("publishers", publishers);
// 
//        return "publishersList";
//    }
// 
//}
