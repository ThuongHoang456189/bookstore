package pq.jdev.b001.bookstore.books.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Upload;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long>, CrudRepository<Upload, Long>{
	public Upload save(Upload upload);
}
