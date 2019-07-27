package pq.jdev.b001.bookstore.publishers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.publisher.models.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;

@Service("publisherService")
public class PublisherServiceImpl implements PublisherService {
	@Autowired
	PublisherRepository publisherRepository;

	@Override
	public List<Publishers> findall() {
		// TODO Auto-generated method stub
		return (List<Publishers>) publisherRepository.findAll();
	}

	@Override
	public void deletePublisher(int publisherId) {
		// TODO Auto-generated method stub
		publisherRepository.deleteById((long) publisherId);

	}

	@Override
	public Publishers find(long publisherId) {
		// TODO Auto-generated method stub
		return publisherRepository.findById(publisherId).get();
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return publisherRepository.count();
	}

}
