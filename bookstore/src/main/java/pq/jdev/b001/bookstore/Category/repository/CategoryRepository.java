package pq.jdev.b001.bookstore.Category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.Category.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long>{
	
}
