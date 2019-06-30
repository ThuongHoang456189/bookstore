package bookstore.Category.service;

import org.springframework.stereotype.Service;

import bookstore.Category.model.Category;

@Service
public interface CategoryAddEditService {
	Category save(Category category);
}
