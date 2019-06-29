package pq.jdev.b001.bookstore.books.web.dto;

import java.sql.Date;
import java.util.Set;
import javax.servlet.http.Part;
import javax.validation.constraints.NotEmpty;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.models.Category;
import pq.jdev.b001.bookstore.models.Publisher;


public class UploadInformationDTO {
	@NotEmpty
	private String title;
	
	@Nullable
	private long price;
	
	@Nullable
	private String domain;
	
	@Nullable
	private Part picture;

	private Date dateupload;
	
	@Nullable
	private String authors;
	
	@Nullable
	private Publisher publisher;
	
	@Nullable
	private Set<Category> categories;
	
	@NotEmpty
	private MultipartFile file;
	
	private String filename;
	
	@Nullable
	private int year;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Part getPicture() {
		return picture;
	}

	public void setPicture(Part picture) {
		this.picture = picture;
	}

	public Date getDateupload() {
		return dateupload;
	}

	public void setDateupload(Date dateupload) {
		this.dateupload = dateupload;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilenames(String filename) {
		this.filename = filename;
	}
	
}
