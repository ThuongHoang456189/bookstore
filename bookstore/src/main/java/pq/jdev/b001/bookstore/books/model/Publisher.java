package pq.jdev.b001.bookstore.books.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "publish")
public class Publisher {

	private long id;
	private String name;
	private Set<NewBook> book;
	
	public Publisher(String name) {
		this.name = name;
	}
	
	
	public Publisher() {
		this.id= -1;
		this.name= "";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId_publisher() {
		return id;
	}
	public void setId_publisher(long id_publisher) {
		this.id = id_publisher;
	}
	public String getName_publisher() {
		return name;
	}
	public void setName_publisher(String name_publisher) {
		this.name = name_publisher;
	}
	
	@OneToMany(mappedBy = "publish")
	@JoinColumn(name = "ID")
	public Set<NewBook> getBook() {
		return book;
	}

	public void setBook(Set<NewBook> book) {
		this.book = book;
	}
	
}
