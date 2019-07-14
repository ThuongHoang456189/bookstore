package pq.jdev.b001.bookstore.books.web.dto;

import java.sql.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.model.SelectPublisher;
import pq.jdev.b001.bookstore.publisher.models.Publishers;




public class UploadInformationDTO {
	@NotEmpty
	private String title;
	
	@Nullable
	private long price;
	
	@Nullable
	private String domain;
	
	@Nullable
	private MultipartFile pictureFile;

	private Date uploadedDate;
	
	@Nullable
	private String authors;
	
	@Nullable
	private Publishers publisher;
	
	@Nullable
	private int publishedYear;
	
	@Nullable
	private List<Category> categories;
	
	@NotEmpty
	private List<MultipartFile> files;
	
	private List<SelectPublisher> selectPublishers;
	
	private List<SelectCategory> selectCategories;
	
	private String uploadMessage;

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

	public MultipartFile getPictureFile() {
		return pictureFile;
	}

	public void setPictureFile(MultipartFile pictureFile) {
		this.pictureFile = pictureFile;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Publishers getPublisher() {
		return publisher;
	}

	public void setPublisher(Publishers publisher) {
		this.publisher = publisher;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public List<SelectPublisher> getSelectPublishers() {
		return selectPublishers;
	}

	public void setSelectPublishers(List<SelectPublisher> selectPublishers) {
		this.selectPublishers = selectPublishers;
	}

	public List<SelectCategory> getSelectCategories() {
		return selectCategories;
	}

	public void setSelectCategories(List<SelectCategory> selectCategories) {
		this.selectCategories = selectCategories;
	}

	public String getUploadMessage() {
		return uploadMessage;
	}

	public void setUploadMessage(String uploadMessage) {
		this.uploadMessage = uploadMessage;
	}

}
