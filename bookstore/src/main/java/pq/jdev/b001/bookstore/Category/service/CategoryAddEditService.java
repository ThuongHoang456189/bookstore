package pq.jdev.b001.bookstore.Category.service;

import org.springframework.stereotype.Service;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;

@Service
public interface CategoryAddEditService {
	Category save(CategoryWeb categoryweb);
}