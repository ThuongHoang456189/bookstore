package pq.jdev.b001.bookstore.books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, JpaRepository<Book, Long> {

	@Query("select b from Book as b where b.person.id = ?1")
	public List<Book> findBooksByPersonId(Long personId);
	
	@Query("select bi from Book as bi where bi.id = ?1")
	public Book findByBookId(Long bookid);
}
