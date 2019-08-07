package pq.jdev.b001.bookstore.Category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.Category.model.Category;

/*
 * CategoryRepository Class
 * 
 * Java 12
 * 
 * 17/08/2019
 * 
 * author @nphtu
 * 
 * */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CrudRepository<Category, Long>{
<<<<<<< HEAD
	List<Category> findByName(String name);
=======
	Category findByName(String name);
	
>>>>>>> bb08b965ee6f7211cf18f3d6ddf1c7567b666a8c
}
