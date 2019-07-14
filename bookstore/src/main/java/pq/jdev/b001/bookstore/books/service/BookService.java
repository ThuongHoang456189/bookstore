package pq.jdev.b001.bookstore.books.service;

import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.users.model.Person;

public interface BookService{
	
	//public boolean uploadBook(UploadInformationDTO uploadInformationDTO);
	
	public boolean checkRightView(Person person, Long referenceId);
	
	public UploadInformationDTO save(UploadInformationDTO dto, Person person);

}
