package pq.jdev.b001.bookstore.books.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.models.Upload;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long>{
	public Upload save(Upload upload);
}
