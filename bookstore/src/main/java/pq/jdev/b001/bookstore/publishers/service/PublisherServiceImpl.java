package pq.jdev.b001.bookstore.publishers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;
@Service("publisherService")
public class PublisherServiceImpl implements PublisherService{
	@Autowired
	PublisherRepository publisherRepository;
	
	
	@Override
	public List<Publishers> findall() {
		return (List<Publishers>) publisherRepository.findAll();
	}

	@Override
	public void deletePublisher(int publisherId) {
		publisherRepository.deleteById((long) publisherId);
	}

	@Override
	public Publishers find(long publisherId) {
		return publisherRepository.findById(publisherId).get();
	}

}
