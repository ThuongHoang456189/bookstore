package pq.jdev.b001.bookstore.books.model;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book", catalog = "book_category_upload")
public class NewBook {
	
	private long id;
	
	private String title;
	
	private long price;
	
	private String domain;
	
	private String picture;
	
	private Date dateupload;
	
	private String authors;
	
	private long personid;
	
	@ManyToOne
	@JoinColumn(name = "PUBLISHID")
	private Publisher publisher;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "BOOKID"), inverseJoinColumns = @JoinColumn(name = "CATEGORYID"))
	private Set<Category>categories;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	@JoinColumn(name = "ID")
	private Set<Upload> uploads;
	
	private int year;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
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
	public long getPersonid() {
		return personid;
	}
	public void setPersonid(long personid) {
		this.personid = personid;
	}
	@OneToOne(fetch = FetchType.EAGER)
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
	public Set<Upload> getUploads() {
		return uploads;
	}
	public void setUploads(Set<Upload> uploads) {
		this.uploads = uploads;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
