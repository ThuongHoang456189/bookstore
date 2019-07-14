package pq.jdev.b001.bookstore.books.model;

import pq.jdev.b001.bookstore.publisher.models.Publishers;

public class SelectPublisher {
	
	private Publishers publisher;
	private int flag = 0;

	public SelectPublisher() {
	}
	
	public SelectPublisher(Publishers publisher, int flag) {
		this.publisher = publisher;
		this.flag = flag;
	}
	public Publishers getPublisher() {
		return publisher;
	}
	public void setPublisher(Publishers publisher) {
		this.publisher = publisher;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
