package pq.jdev.b001.bookstore.books.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.web.dto.BookDTO;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.users.model.Person;

public interface BookService {

	public boolean checkInput(UploadInformationDTO dto);

	public UploadInformationDTO save(UploadInformationDTO dto, Person person, List<String> categoriesId)
			throws Exception;

	public UploadInformationDTO update(UploadInformationDTO dto, Person person, List<String> categoriesId,
			Book editBook) throws Exception;

	public boolean checkRightInteraction(User user, Book book) throws Exception;

	public List<BookDTO> viewAllBooks();

	public Book findBookByID(Long id);

	public List<Publishers> showAllPublishers();

	public List<Category> showAllCategories();

	public List<SelectCategory> showAllCategoriesWithFlag(Book editBook);
	
}
