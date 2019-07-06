package pq.jdev.b001.bookstore.publishers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.publisher.models.Publishers;

@Service
public interface PublisherService {
	public List<Publishers> findall();
	public void deletePublisher (int publisherId);
	public Publishers find(int publisherId);
}
