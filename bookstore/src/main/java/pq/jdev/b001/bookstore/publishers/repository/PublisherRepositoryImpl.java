package pq.jdev.b001.bookstore.publishers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.publisher.models.Publishers;

@Repository
@Qualifier("PublisherRepository")
public class PublisherRepositoryImpl implements PublisherRepository{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public <S extends Publishers> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Publishers> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Publishers> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Publishers> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Publishers entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Publishers> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Publishers> findAll() {
		// TODO Auto-generated method stub
        List < Publishers > publisher = jdbcTemplate.query("SELECT * FROM publish", new BeanPropertyRowMapper(Publishers.class));
        return publisher;
	}

	@Override
	public void deletePublisher(int publisherId) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("DELETE from publish WHERE publisherId = ? ", publisherId);
        System.out.println("Publisher Deleted!!");
		
	}

	@Override
	public Publishers find(int publisherID) {
		// TODO Auto-generated method stub
		return null;
	}

}
