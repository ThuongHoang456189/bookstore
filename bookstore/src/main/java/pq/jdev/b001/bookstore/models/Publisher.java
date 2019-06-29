package pq.jdev.b001.bookstore.models;

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

	private long id_publisher;
	private String name_publisher;
	private Set<NewBook> book;
	
	public Publisher(String name_publisher) {
		this.name_publisher = name_publisher;
	}
	
	
	public Publisher() {
		this.id_publisher = -1;
		this.name_publisher = "";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId_publisher() {
		return id_publisher;
	}
	public void setId_publisher(long id_publisher) {
		this.id_publisher = id_publisher;
	}
	public String getName_publisher() {
		return name_publisher;
	}
	public void setName_publisher(String name_publisher) {
		this.name_publisher = name_publisher;
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
