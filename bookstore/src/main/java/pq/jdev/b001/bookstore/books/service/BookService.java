package pq.jdev.b001.bookstore.books.service;

import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;

public interface BookService{
	
	public boolean uploadBook(long personid, UploadInformationDTO uploadInformationDTO);
	
}
