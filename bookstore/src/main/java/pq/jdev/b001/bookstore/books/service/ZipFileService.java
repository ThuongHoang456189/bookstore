package pq.jdev.b001.bookstore.books.service;

import java.io.File;

public interface ZipFileService {

	void zipIt(String zipFile);

	void generateFileList(File node);
}
