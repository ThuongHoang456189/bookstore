package pq.jdev.b001.bookstore.publishers.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.publisher.models.Publishers;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<Publishers, Long> {
	public List<Publishers> findAll();
	public void deletePublisher(int publisherId);
	public Publishers find(int publisherID);
}
