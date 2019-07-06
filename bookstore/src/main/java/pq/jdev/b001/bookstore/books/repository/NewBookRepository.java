package pq.jdev.b001.bookstore.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.books.model.NewBook;

@Repository
public interface NewBookRepository extends JpaRepository<NewBook, Long> {
	public NewBook findByID(long id_book);
	public NewBook saveNewBook (UploadInformationDTO dto);
}
