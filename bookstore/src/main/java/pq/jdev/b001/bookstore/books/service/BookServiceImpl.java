package pq.jdev.b001.bookstore.books.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.books.repository.NewBookRepository;
import pq.jdev.b001.bookstore.books.repository.UploadRepository;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.books.model.NewBook;
import pq.jdev.b001.bookstore.books.model.Upload;

public class BookServiceImpl implements BookService{

	@Autowired
	private UploadRepository uploadRepository;
	
	@Autowired
	private NewBookRepository newBookRepository;
	
	@Override
	public boolean uploadBook(long personid, UploadInformationDTO uploadInformationDTO) {
		// TODO Auto-generated method stub
		if(personid!=-1)
		{
			try {
				NewBook book = new NewBook();
				Upload upload = new Upload();
				//Book_title
				book.setTitle(uploadInformationDTO.getTitle());
				//Book_price
				book.setPrice(uploadInformationDTO.getPrice());
				//Book_domain
				book.setDomain(uploadInformationDTO.getDomain());
				//Book_picture
				book.setPicture(uploadInformationDTO.getPicture().getName());
				//Book_dateupload + Upload_dateupload
				book.setDateupload(uploadInformationDTO.getDateupload());
				//Book_authors
				book.setAuthors(uploadInformationDTO.getAuthors());
				//Book_personid
				book.setPersonid(personid);
				//Book_publishid
				book.setPublisher(uploadInformationDTO.getPublisher());
				//Book_year
				book.setYear(uploadInformationDTO.getYear());
				//Upload_book
				upload.setBook(book);			
				//Upload_filename
				upload.setFilename(uploadInformationDTO.getFile().getName());
				//Upload_dateupdate
				upload.setDateupdate(uploadInformationDTO.getDateupload());
				//Assume Chang file name
				upload.setChangefilename("123");
				
				uploadRepository.save(upload);
				
				//Upload_changefilename
				File resourcesDirectory = new File("src/main/resources");
				String uploadResourcePath = resourcesDirectory.getAbsolutePath() + "\\";
				String uploadUploadPath = uploadResourcePath+"upload";
				//Upload_uploadnumber + extension from user file upload
				String fileName = uploadInformationDTO.getFile().getName();
				Long uploadnumber = upload.getId();
				String mimeType = "";
			    if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			        mimeType = mimeType + fileName.substring(fileName.lastIndexOf(".")+1);
				if(!mimeType.equals(""))
				{
					uploadUploadPath = uploadUploadPath + uploadnumber + "." + mimeType;;
				}
				else
				{
					uploadUploadPath = uploadUploadPath + uploadnumber;
				}
				upload.setChangefilename(uploadUploadPath);
				MultipartFile uploadFile = (MultipartFile) uploadInformationDTO.getFile();
				try 
				{
					byte[] bytes = uploadFile.getBytes();
					
					Path path = Paths.get(uploadUploadPath);
					
					Files.write(path, bytes);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newBookRepository.save(book);
				uploadRepository.save(upload);
				return true;
			}catch(Exception e)
			{
				System.out.println("Error in BookServieImpl_UploadBook");
				System.out.println(e.getMessage());
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
