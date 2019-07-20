package pq.jdev.b001.bookstore.books.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.books.repository.BookRepository;
import pq.jdev.b001.bookstore.books.repository.UploadRepository;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.model.Upload;

@Service
@Transactional
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UploadRepository uploadRepository;
	
	@Autowired
	private UploadPathService uploadPathService;
	
	@Autowired
	private ZipFileService zipFileService;
	
	@Autowired
	private ZipFileServiceImpl zipFileServiceImpl;
	
	@Autowired
	private ServletContext context;
	
//	@Autowired
//	private NewBookRepository newBookRepository;
	
//	public boolean uploadBook (UploadInformationDTO uploadInformationDTO) {
//		// TODO Auto-generated method stub
//		long personId = -1;
//		//get personId here
//		///////////////////
//		
//		if(personId != -1)
//		{
//			try {
//				Book book = new Book();
//				Upload upload = new Upload();
//				//Book_title
//				book.setTitle(uploadInformationDTO.getTitle());
//				//Book_price
//				book.setPrice(uploadInformationDTO.getPrice());
//				//Book_domain
//				book.setDomain(uploadInformationDTO.getDomain());
//				//Book_dateupload + Upload_dateupload
//				book.setUploadedDate(uploadInformationDTO.getDateupload());
//				//Book_authors
//				book.setAuthors(uploadInformationDTO.getAuthors());
//				//Book_personid
//				book.setPersonId(personId);
//				//Book_publishid
//				book.setPublisher(uploadInformationDTO.getPublisher());
//				//Book_year
//				book.setPublishedYear(uploadInformationDTO.getYear());
//				
//				
//				//Book_picture
//				book.setPicture(uploadInformationDTO.getPicture().getName());
//				
//				//Upload_book
//				upload.setBook(book);			
//				//Upload_filename
//				upload.setFilename(uploadInformationDTO.getFile().getName());
//				//Upload_dateupdate
//				upload.setDateupdate(uploadInformationDTO.getDateupload());
//				//Assume Chang file name
//				upload.setChangefilename("123");
//				
//				uploadRepository.save(upload);
//				
//				//Upload_changefilename
//				File resourcesDirectory = new File("src/main/resources");
//				String uploadResourcePath = resourcesDirectory.getAbsolutePath() + "\\";
//				String uploadUploadPath = uploadResourcePath+"upload";
//				//Upload_uploadnumber + extension from user file upload
//				String fileName = uploadInformationDTO.getFile().getName();
//				Long uploadnumber = upload.getId();
//				String mimeType = "";
//			    if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
//			        mimeType = mimeType + fileName.substring(fileName.lastIndexOf(".")+1);
//				if(!mimeType.equals(""))
//				{
//					uploadUploadPath = uploadUploadPath + uploadnumber + "." + mimeType;;
//				}
//				else
//				{
//					uploadUploadPath = uploadUploadPath + uploadnumber;
//				}
//				upload.setChangefilename(uploadUploadPath);
//				MultipartFile uploadFile = (MultipartFile) uploadInformationDTO.getFile();
//				try 
//				{
//					byte[] bytes = uploadFile.getBytes();
//					
//					Path path = Paths.get(uploadUploadPath);
//					
//					Files.write(path, bytes);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				newBookRepository.save(book);
//				uploadRepository.save(upload);
//				return true;
//			}catch(Exception e)
//			{
//				System.out.println("Error in BookServieImpl_UploadBook");
//				System.out.println(e.getMessage());
//				return false;
//			}
//		}
//		else
//		{
//			return false;
//		}
//	}

	public UploadInformationDTO save(UploadInformationDTO dto, Person person) {
		long personId = -1;
		//get personId here
		///////////////////
		
		if(personId != -1)
		{
			try {
				Book book = new Book();
				Upload upload = new Upload();
				//Book_title
				book.setTitle(dto.getTitle());
				//Book_price
				book.setPrice(dto.getPrice());
				//Book_domain
				book.setDomain(dto.getDomain());
				//Book_uploadedDate
				book.setUploadedDate(dto.getUploadedDate());
				//Book_authors
				book.setAuthors(dto.getAuthors());
				//Book_personId
				book.setPerson(person);
				//Book_publishid
				book.setPublisher(dto.getPublisher());;
				//Book_publishedYear
				book.setPublishedYear(dto.getPublishedYear());
				
				Book dbBook = bookRepository.save(book);
				
				//Book_picture
				if(dbBook != null && !dto.getPictureFile().isEmpty())
				{
					MultipartFile pictureFile = dto.getPictureFile();
					if(pictureFile != null &StringUtils.hasText(pictureFile.getOriginalFilename()))
					{
					String originalFileName = pictureFile.getOriginalFilename();
					//System.out.println(filename);
					String modifiedFileName = dbBook.getId() + "_" + FilenameUtils.getBaseName(originalFileName) + "." + FilenameUtils.getExtension(originalFileName);
					//System.out.println(modifiedFileName);
					File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "images");
					if(storePictureFile != null) {
					try {
							FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
						}catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
						}
					}
					dbBook.setPicture(modifiedFileName);
					}
				}
				
				dbBook = bookRepository.save(dbBook);
				
				//Upload_uploadedDate
				upload.setUploadedDate(dto.getUploadedDate());
				//Upload_book
				upload.setBook(dbBook);
				
				Upload dbUpload = uploadRepository.save(upload);
				
				//Upload_files
				
				if(dbUpload != null && dto.getFiles() != null && dto.getFiles().size()>0)
				{
					String originalFileZipName = null;
					String modifiedFileZipName = null;
					
					List<String> originalFileNames = new ArrayList<String>();
					String sourcePath = context.getRealPath("/");
					
					for(MultipartFile file: dto.getFiles()) {
						if(file != null && StringUtils.hasText(file.getOriginalFilename())) {
							String fileName = file.getOriginalFilename();
							originalFileNames.add(fileName);
							//System.out.println(fileName);
							//String modifiedFileName = dbUpload.getId() + "."+FilenameUtils.getExtension(fileName);
							//System.out.println(modifiedFileName);
							//File storeFile = uploadPathService.getFilePath(modifiedFileName, "uploads");
//							if(storeFile != null) {
//								try {
//									FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
//								}catch (Exception e) {
//									// TODO: handle exception
//									e.printStackTrace();
//								}
//							}
						}
						
						
					}
					
					//Zipfile
					File sourceFile = uploadPathService.getFilePath("bookzip", upload.getId()+"");
					String modifiedFilePath = sourcePath + "uploads" + File.separator + dbUpload.getId() + ".zip";
					zipFileServiceImpl = new ZipFileServiceImpl();
					zipFileServiceImpl.setOriginalFileNames(originalFileNames);
					zipFileServiceImpl.setSourcePath(sourcePath);
					zipFileService.generateFileList(sourceFile);
					zipFileService.zipIt(modifiedFilePath);
					
					//Upload_originalFileName
					dbUpload.setOriginalFileName(originalFileZipName);
					//Upload_uploadedFileName
					dbUpload.setModifiedFileName(dbUpload.getId() + ".zip");
					//Upload_modifiedFileName
					dbUpload.setModifiedFilePath(modifiedFilePath);
					
					uploadRepository.save(dbUpload);
				}
				else
				{
					uploadRepository.delete(dbUpload);
					bookRepository.delete(dbBook);
					System.out.println("Cannot upload Book's files");
					dto.setUploadMessage("There were some errors while uploading files. Please check your work again.");
					return null;
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return dto;
		
	}

	@Override
	public boolean checkRightView(Person person, Long referenceId) {
		// TODO Auto-generated method stub
		return false;
	}

}
