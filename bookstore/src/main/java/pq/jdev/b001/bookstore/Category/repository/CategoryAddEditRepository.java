package pq.jdev.b001.bookstore.Category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.Category.model.Category;
import pq.jdev.b001.bookstore.Category.web.CategoryWeb;


@Repository
public interface CategoryAddEditRepository extends JpaRepository<Category, Long>{
	Category save(Category category);
}
