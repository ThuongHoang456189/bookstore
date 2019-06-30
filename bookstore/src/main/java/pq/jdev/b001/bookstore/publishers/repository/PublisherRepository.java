package pq.jdev.b001.bookstore.publishers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import pq.jdev.b001.bookstore.models.Publishers;

@Repository
public interface PublisherRepository extends CrudRepository<Publishers, Long> {
	List<Publishers> findByCreateName(String createName);
	
}
