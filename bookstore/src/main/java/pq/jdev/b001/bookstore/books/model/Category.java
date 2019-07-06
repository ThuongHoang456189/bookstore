package pq.jdev.b001.bookstore.books.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category", catalog = "book_category_upload")
public class Category {
	
	private long categoryid;
	
	@Column(name = "NAME")
	private String name;
	@ManyToMany(mappedBy = "categories")
	private Set<NewBook> book;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(long categoryid) {
		this.categoryid = categoryid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<NewBook> getBook() {
		return book;
	}
	public void setBook(Set<NewBook> book) {
		this.book = book;
	}
}
