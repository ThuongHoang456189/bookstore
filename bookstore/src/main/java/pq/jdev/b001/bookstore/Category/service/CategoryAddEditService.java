package pq.jdev.b001.bookstore.Category.service;

import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.Category.model.Category;

@Service
public interface CategoryAddEditService {
	Category save(Category category);
}
