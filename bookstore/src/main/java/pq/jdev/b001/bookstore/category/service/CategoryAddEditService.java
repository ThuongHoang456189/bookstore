package pq.jdev.b001.bookstore.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.web.CategoryWeb;

@Service
public interface CategoryAddEditService {
	Category save(CategoryWeb categoryweb);
	List<Category> findAll();
	List<Category> findByName(String name);
	void delete(long id);
}