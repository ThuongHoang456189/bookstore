package pq.jdev.b001.bookstore.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import java.util.Collection;

@Entity
public class Publishers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	private String publisher ;
	private String createName ;
	private String createDate ;
	private String updateName ;
	private String updateDate ;
	
	public Publishers() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Publishers(Long id, String publisher, String createName, String createDate, String updateName,
			String updateDate) {
		super();
		this.id = id;
		this.publisher = publisher;
		this.createName = createName;
		this.createDate = createDate;
		this.updateName = updateName;
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "Publishers [id=" + id + ", publisher=" + publisher + ", createName=" + createName + ", createDate="
				+ createDate + ", updateName=" + updateName + ", updateDate=" + updateDate + "]";
	}
	
}