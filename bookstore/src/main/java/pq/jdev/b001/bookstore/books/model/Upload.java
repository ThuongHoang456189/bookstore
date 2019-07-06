package pq.jdev.b001.bookstore.books.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "upload", catalog = "book_category_upload")
public class Upload {
	private long id;
	
	@Nullable
	@ManyToOne
	@JoinColumn(name = "BOOKID", nullable = false)
	private NewBook book;
	private String filename;
	private String changefilename;
	private Date dateupdate;
	public long getId() {
		return id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public void setId(long id) {
		this.id = id;
	}
	public NewBook getBook() {
		return book;
	}
	public void setBook(NewBook book) {
		this.book = book;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getChangefilename() {
		return changefilename;
	}
	public void setChangefilename(String changefilename) {
		this.changefilename = changefilename;
	}
	public Date getDateupdate() {
		return dateupdate;
	}
	public void setDateupdate(Date dateupdate) {
		this.dateupdate = dateupdate;
	}
}
