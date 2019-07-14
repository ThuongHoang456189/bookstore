package pq.jdev.b001.bookstore.publishers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;
import pq.jdev.b001.bookstore.publishers.service.PublisherService;
import pq.jdev.b001.bookstore.publishers.service.PublisherServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PublisherController {
	// private final PublisherRepository publisherReponsitory;

	/*
	 * @Autowired public PublisherController(PublisherRepository
	 * publisherReponsitory) { this.publisherReponsitory = publisherReponsitory; }
	 */

	/*
	 * @GetMapping("list") public String showUpdateForm(Model model) {
	 * model.addAttribute("publishers", publisherReponsitory.findAll()); return
	 * "index"; }
	 */

	// private static List<Publishers> publishers = new ArrayList<Publishers>();

	/*
	 * @RequestMapping(value = {"/index" }, method = RequestMethod.GET) public
	 * String index(Model model) {
	 * 
	 * return "index"; }
	 */
	@Autowired
	private PublisherService publisherService;

	@GetMapping("publishersList")
	public String viewPublishersList(Model model) {

		List<Publishers> publishers = new ArrayList<Publishers>();
		publishers = publisherService.findall();
		model.addAttribute("publishers", publishers);
		return "publishersList";
	}

}
