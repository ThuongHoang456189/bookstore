package pq.jdev.b001.bookstore.books.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.model.Upload;
import pq.jdev.b001.bookstore.books.repository.BookRepository;
import pq.jdev.b001.bookstore.books.repository.UploadRepository;
import pq.jdev.b001.bookstore.books.web.dto.BookDTO;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.repository.CategoryRepository;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.UserService;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private UserService userService;

	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	private UploadPathService uploadPathService;

	@Autowired
	private ZipFileService zipFileService;

	@Autowired
	private ServletContext context;

	private FileInputStream stream;

	/**
	 * Method checkInput is used to check if user didn't miss any important
	 * information
	 */
	public boolean checkInput(UploadInformationDTO dto) {
		if (!dto.getTitle().equals("")) {
			if (dto.getPublisherId() >= 0) {
				for (MultipartFile file : dto.getFiles()) {
					if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/** Method save is used to insert a new book to database */
	public UploadInformationDTO save(UploadInformationDTO dto, Person person, List<String> categoriesId)
			throws Exception {
		try {
			Book book = new Book();
			Upload upload = new Upload();
			/** Handling book first */
			/** Set book.title */
			book.setTitle(dto.getTitle());
			/** Set book.price */
			book.setPrice(dto.getPrice());
			/** Set book.domain */
			book.setDomain(dto.getDomain());
			/** Set book.uploadedDate */
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			book.setUploadedDate(date);
			/** Set book.authors */
			book.setAuthors(dto.getAuthors());
			/** Set book.person */
			book.setPerson(person);
			/** Set book.publisher */
			Publishers dtoPublisher = publisherRepository.getOne(dto.getPublisherId());
			book.setPublisher(dtoPublisher);
			/** Set book.publishedYear */
			book.setPublishedYear(dto.getPublishedYear());
			/** Set book.description */
			book.setDescription(dto.getDescription());
			/** Save book to get book.id */
			Book dbBook = bookRepository.save(book);
			/** Upload book's cover and set book.picture */
			if (dbBook != null && !dto.getPictureFile().isEmpty()) {
				try {
					MultipartFile pictureFile = dto.getPictureFile();
					if (pictureFile != null & StringUtils.hasText(pictureFile.getOriginalFilename())) {
						String originalFileName = pictureFile.getOriginalFilename();
						String modifiedFileName = dbBook.getId() + "_" + FilenameUtils.getBaseName(originalFileName)
								+ "." + FilenameUtils.getExtension(originalFileName);
						File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "images/bookscover");
						System.out.println(storePictureFile.getAbsolutePath()+".......");
						if (storePictureFile != null) {
							try {
								FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						dbBook.setPicture(modifiedFileName);
						bookRepository.saveUpdatePicture(dbBook.getId(), modifiedFileName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			/** Upload book's files and handle upload */
			/** Set upload.uploadedDate */
			long millisUploadedDate = System.currentTimeMillis();
			java.sql.Date dateUploadedDate = new java.sql.Date(millisUploadedDate);
			System.out.println(dateUploadedDate);
			upload.setUploadedDate(dateUploadedDate);
			/** Set upload.book */
			upload.setBook(dbBook);
			/** Save upload to get upload.id */
			Upload dbUpload = uploadRepository.save(upload);
			/** Upload book's files */
			if (dto.getFiles() != null && dto.getFiles().size() > 0 && dbUpload != null) {
				String originalFileUploadName = "";
				try {
					String sourcePath = context.getRealPath("/");
					/** upload.originalFileName */
					for (MultipartFile file : dto.getFiles()) {
						if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
							originalFileUploadName = file.getOriginalFilename();
							break;
						}
					}
					/** upload.modifiedFilePath */
					String modifiedFilePath = "";
					/** Check to zip files or not when upload book's files */
					if (dto.getFiles().size() > 1) {
						for (MultipartFile file : dto.getFiles()) {
							String filename = file.getOriginalFilename();
							String modifiedFileName = FilenameUtils.getBaseName(filename) + "."
									+ FilenameUtils.getExtension(filename);
							File storeFile = uploadPathService.getFilePath(modifiedFileName,
									"uploads" + File.separator + dbUpload.getId());
							if (storeFile != null) {
								try {
									FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						modifiedFilePath = sourcePath + "uploads" + File.separator + dbUpload.getId() + ".zip";
						/** Zip all book's files */
						File dir = new File(sourcePath + "uploads" + File.separator + dbUpload.getId());
						zipFileService.zipDirectory(dir, modifiedFilePath);
						/** Delete temporary directory */
						FileUtils.deleteDirectory(dir);
						dbUpload.setModifiedFileName(dbUpload.getId() + ".zip");
					} else {
						modifiedFilePath = sourcePath + "uploads" + File.separator + dbUpload.getId() + "."
								+ FilenameUtils.getExtension(originalFileUploadName);
						for (MultipartFile file : dto.getFiles()) {
							String filename = file.getOriginalFilename();
							String modifiedFileName = dbUpload.getId() + "." + FilenameUtils.getExtension(filename);
							File storeFile = uploadPathService.getFilePath(modifiedFileName, "uploads");
							if (storeFile != null) {
								try {
									FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
									if (storeFile.isFile()) {
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						dbUpload.setModifiedFileName(dbUpload.getId() + "." + FilenameUtils.getExtension(originalFileUploadName));
					}
					/** Set upload.originalFileName */
					dbUpload.setOriginalFileName(originalFileUploadName);
					/** Set upload.modifiedFileName */
					dbUpload.setModifiedFileName(dbUpload.getId() + ".zip");
					/** Set upload.modifiedFilePath */
					dbUpload.setModifiedFilePath(sourcePath + "uploads" + File.separator + dbUpload.getId());
					/** Save upload */
					uploadRepository.save(dbUpload);
				} catch (Exception e) {
					e.printStackTrace();
				}
				/** Complete handling with upload */
			}
			/** Set book.categories */
			Set<Category> categories = new HashSet<Category>();
			Category t = new Category();
			for (String categoryStringId : categoriesId) {
				Long categoryId = Long.parseLong(categoryStringId);
				t = categoryRepository.getOne(categoryId);
				categories.add(t);
				t = new Category();
			}
			dbBook.setCategories(categories);
//			bookRepository.saveUpdateCategories(dbBook.getId(), categories);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** Complete handling with book */
		return dto;
	}

	public boolean checkRightInteraction(User user, Book book) throws Exception {
		Person currentUser = userService.findByUsername(user.getUsername());
		if (currentUser.getPower() == 2) {
			return true;
		} else if (currentUser.getPower() == 1) {
			try {
				Book bookFound = bookRepository.findByPersonIdandBookId(currentUser.getId(), book.getId());
				if (bookFound == null) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public UploadInformationDTO update(UploadInformationDTO dto, Person person, List<String> categoriesId,
			Book editBook) throws Exception {
		try {
			Long bookid = editBook.getId();
			Upload upload = new Upload();
			/** Update book.title */
			bookRepository.saveUpdateTitle(bookid, dto.getTitle());
			/** Update book.price */
			bookRepository.saveUpdatePrice(bookid, dto.getPrice());
			/** Update book.domain */
			bookRepository.saveUpdateDomain(bookid, dto.getDomain());
			/** Update book.uploadedDate */
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			System.out.println(date);
			bookRepository.saveUpdateUploadedDate(bookid, date);
			/** Update book.authors */
			bookRepository.saveUpdateAuthors(bookid, dto.getAuthors());
			/** Update book.person */
			bookRepository.saveUpdatePerson(bookid, person);
			/** Update book.publisher */
			Publishers dtoPublisher = publisherRepository.getOne(dto.getPublisherId());
			bookRepository.saveUpdatePublisher(bookid, dtoPublisher);
			/** Update book.publishedYear */
			bookRepository.saveUpdatePublishedYear(bookid, dto.getPublishedYear());
			/** Update book.description */
			bookRepository.saveUpdateDescription(bookid, dto.getDescription());
			/** Update book.picture */
			if (dto.getPictureFile() != null) {
				try {
					MultipartFile pictureFile = dto.getPictureFile();
					if (pictureFile != null & StringUtils.hasText(pictureFile.getOriginalFilename())) {
						String originalFileName = pictureFile.getOriginalFilename();
						String modifiedFileName = bookid + "_" + FilenameUtils.getBaseName(originalFileName) + "."
								+ FilenameUtils.getExtension(originalFileName);
						File storePictureFile = uploadPathService.getFilePath(modifiedFileName, "images/bookscover");
						if (storePictureFile != null) {
							try {
								FileUtils.writeByteArrayToFile(storePictureFile, pictureFile.getBytes());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						bookRepository.saveUpdatePicture(bookid, modifiedFileName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			/** Check if user upload files or not */
			if (checkInput(dto)) {
				/** Upload book's files and handle upload */
				/** Set upload.uploadedDate */
				long millisUploadedDate = System.currentTimeMillis();
				java.sql.Date dateUploadedDate = new java.sql.Date(millisUploadedDate);
				upload.setUploadedDate(dateUploadedDate);
				/** Set upload.book */
				upload.setBook(editBook);
				/** Save upload to get upload.id */
				Upload dbUpload = uploadRepository.save(upload);
				/** Upload book's files */
				if (dto.getFiles() != null && dto.getFiles().size() > 0 && dbUpload != null) {
					String originalFileUploadName = "";
					try {
						String sourcePath = context.getRealPath("/");
						/** upload.originalFileName */
						for (MultipartFile file : dto.getFiles()) {
							if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
								originalFileUploadName = file.getOriginalFilename();
								break;
							}
						}
						/** upload.modifiedFilePath */
						String modifiedFilePath = "";
						/** Check to zip files or not when upload book's files */
						if (dto.getFiles().size() > 1) {
							for (MultipartFile file : dto.getFiles()) {
								String filename = file.getOriginalFilename();
								String modifiedFileName = FilenameUtils.getBaseName(filename) + "."
										+ FilenameUtils.getExtension(filename);
								File storeFile = uploadPathService.getFilePath(modifiedFileName,
										"uploads" + File.separator + dbUpload.getId());
								if (storeFile != null) {
									try {
										FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							modifiedFilePath = sourcePath + "uploads" + File.separator + dbUpload.getId() + ".zip";
							/** Zip all book's files */
							File dir = new File(sourcePath + "uploads" + File.separator + dbUpload.getId());
							zipFileService.zipDirectory(dir, modifiedFilePath);
							/** Delete temporary directory */
							FileUtils.deleteDirectory(dir);
						} else {
							modifiedFilePath = sourcePath + "uploads" + File.separator + dbUpload.getId()
									+ FilenameUtils.getExtension(originalFileUploadName);
							for (MultipartFile file : dto.getFiles()) {
								String filename = file.getOriginalFilename();
								String modifiedFileName = dbUpload.getId() + "." + FilenameUtils.getExtension(filename);
								File storeFile = uploadPathService.getFilePath(modifiedFileName, "uploads");
								if (storeFile != null) {
									try {
										FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
										if (storeFile.isFile()) {
										}

									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
						/** Set upload.originalFileName */
						dbUpload.setOriginalFileName(originalFileUploadName);
						/** Set upload.modifiedFileName */
						dbUpload.setModifiedFileName(dbUpload.getId() + ".zip");
						/** Set upload.modifiedFilePath */
						dbUpload.setModifiedFilePath(sourcePath + "uploads" + File.separator + dbUpload.getId());
						/** Save upload */
						uploadRepository.save(dbUpload);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				/** Complete handling with upload */
			}
			/** Set book.categories */
			Set<Category> categories = new HashSet<Category>();
			Category t = new Category();
			for (String categoryStringId : categoriesId) {
				Long categoryId = Long.parseLong(categoryStringId);
				t = categoryRepository.getOne(categoryId);
				categories.add(t);
				t = new Category();
			}
			editBook.setCategories(categories);
//			bookRepository.saveUpdateCategories(editBook.getId(), categories);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public List<BookDTO> viewAllBooks() {
		List<Book> allBooks = bookRepository.findAll();
		List<BookDTO> books = new ArrayList<BookDTO>();
		BookDTO temp = new BookDTO();
		for (Book book : allBooks) {
			String stringCategories = "";
			for (Category category : book.getCategories()) {
				stringCategories = stringCategories + category.getName() + ", ";
			}
			temp.setCurrentBook(book);
			books.add(temp);
			temp = new BookDTO();
		}
		return books;
	}

	public Book findBookByID(Long id) {
		Book book = bookRepository.getOne(id);
		return book;
	}

	public List<Publishers> showAllPublishers() {
		List<Publishers> publishers = publisherRepository.findAll();
		return publishers;
	}

	public List<Category> showAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

	public List<SelectCategory> showAllCategoriesWithFlag(Book editBook) {
		List<Category> categories = categoryRepository.findAll();
		List<SelectCategory> selectCategories = new ArrayList<SelectCategory>();
		SelectCategory temp = new SelectCategory();
		for (int i = 0; i < categories.size(); i++) {
			for (Category o : editBook.getCategories()) {
				temp.setCategory(o);
				if (o.getId() == categories.get(i).getId()) {
					temp.setFlag(1);
				}
			}
			selectCategories.add(temp);
			temp = new SelectCategory();
		}
		return selectCategories;
	}
	
	public List<Date> listUploadedDateofBook(String id, String picture){
		Long idBook = Long.parseLong(id);
		List<Upload> uploads = uploadRepository.findUploadByIdBook(idBook);
		List<Date> uploadedDates = new ArrayList<Date>();
		for(Upload u : uploads)
		{
			uploadedDates.add(u.getUploadedDate());
		}
		Book bookFound = bookRepository.getOne(idBook);
		picture = context.getRealPath("images/bookscover/"+bookFound.getPicture());
		return uploadedDates;
	}

	public void downloadZipFiles(String bookid, Date uploadedDate, HttpServletResponse response) {
		try {
			Long idBook = Long.parseLong(bookid);
			System.out.println("aaaa");
//				Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(uploadedDate);
			System.out.println("bbbb");
			Upload uploadFound = uploadRepository.findUploadByIdBookandUploadedDate(idBook, uploadedDate);
			System.out.println("cccc");
			File fileFound = new File(uploadFound.getModifiedFilePath());
			System.out.println("dddd");
			if(uploadFound.getModifiedFilePath()!=null && fileFound.exists() &&uploadFound.getModifiedFileName().contains(".zip"))
			{
			System.out.println("AAAA");
			response.setContentType("zip");
			response.setHeader("Content-Disposition", "attachment"+ 
			                                    "filename=" + uploadFound.getModifiedFileName());
//			           System.out.println("CCCC");
			        byte[] buffer = new byte[122048];
//			        System.out.println(".....");
			        ZipInputStream zis = new ZipInputStream(new FileInputStream(uploadFound.getModifiedFilePath()));
//			        System.out.println("Thup");
			        ZipEntry zipEntry = zis.getNextEntry();
//			        System.out.println("Normal");
			        File destDir = new File(context.getRealPath("uploads/temp"));
			        System.out.println(destDir);
			        while (zipEntry != null) {
//			        	System.out.println("Looo");
			        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			   			System.out.println("Kjo");
			            ZipOutputStream zos = new ZipOutputStream(baos);
			            File newFile = newFile(destDir, zipEntry);
			            FileOutputStream fos = new FileOutputStream(newFile);
			            int len;
			            while ((len = zis.read(buffer)) > 0) {
			                fos.write(buffer, 0, len);
			            }
			            fos.close();
			            zipEntry = zis.getNextEntry();
//			            System.out.println("Dherf");
//			            System.out.println("Edf");
//			            System.out.println("dfja");
//			            while ((len = zis.read(buffer)) != -1) {
////			            	System.out.println("djfsja");
//			            	System.out.println("hdfhash");
//			            	zos.write(buffer, 0, len);
////			                System.out.println("I n");
//			            	System.out.println("Hejdjsj");
//			            }
//			            zipEntry = zis.getNextEntry();
//			            System.out.println("Hej");
			        }
//			        System.out.println("ndfsd");
//			        zis.closeEntry();
//			        zis.close();
////			        stream = new FileInputStream(new File(uploadFound.getModifiedFilePath()));
////		            System.out.println(uploadFound.getModifiedFilePath());
////		            System.out.println("DDDD");
//		            response.setContentLength(stream.available());
////		            System.out.println("EEEE");
//		            OutputStream os = response.getOutputStream();
////		            System.out.println("FFFF");
//		            os.close();
////		            System.out.println("GGGG");
//		            response.flushBuffer();
//		            System.out.println("HHHH");
			}
			} catch (Exception e) {
			System.out.println("Hello, I'm a bug");
			}finally {
			       if (stream != null) {
			           try {
			               stream.close();
			           } catch (IOException e) {
			               e.printStackTrace();
			               System.out.println("Stream fail");
			           }
			       }
			   }


	}
//	
//	private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
//        File destFile = new File(destinationDir, zipEntry.getName());
//         
//        String destDirPath = destinationDir.getCanonicalPath();
//        String destFilePath = destFile.getCanonicalPath();
//         
//        if (!destFilePath.startsWith(destDirPath + File.separator)) {
//            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
//        }
//         
//        return destFile;
//    }
	
	public void downloadFile(String id, Date uploadedDate, HttpServletResponse response) {
		try 
		{
			Long idBook = Long.parseLong(id);
			Upload uploadFound = uploadRepository.findUploadByIdBookandUploadedDate(idBook, uploadedDate);
			File fileFound = new File(uploadFound.getModifiedFilePath());
			if(uploadFound.getModifiedFilePath()!=null && fileFound.exists() && uploadFound.getModifiedFileName().contains(".zip"))
			{
				  byte[] buffer = new byte[122048];

				response.setContentType("application/zip");
		        response.setHeader("Content-Disposition", "attachment; filename="+uploadFound.getBook().getTitle()+".zip");
		        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		        // i dont have idea on what to give here in fileoutputstream
		        FileOutputStream fos = new FileOutputStream(new File(uploadFound.getModifiedFilePath()).getAbsoluteFile());
		        File contentFile = new File(context.getRealPath("uploads/temp"));
		        ZipEntry ze= new ZipEntry(contentFile.toString());
		        zos.putNextEntry(ze);
		        FileInputStream in = new FileInputStream(contentFile.toString());

		        int len;
		        while ((len = in.read(buffer)) > 0) {
		            zos.write(buffer, 0, len);
		        }

		        in.close();
		        zos.closeEntry();

		        //remember close it
		        zos.close();

		        System.out.println("Done");
		    }
		}catch (Exception e) {
			System.out.println("I don't want to see a bug");
			System.out.println("+++++++"+e.getMessage());
		}
	}
	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		        File destFile = new File(destinationDir, zipEntry.getName());
		         
		        String destDirPath = destinationDir.getCanonicalPath();
		        String destFilePath = destFile.getCanonicalPath();
		         
		        if (!destFilePath.startsWith(destDirPath + File.separator)) {
		            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		        }
		         
		        return destFile;
			}
	
}
