package bookstore.Category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bookstore.Category.model.Category;


@Repository
public interface CategoryAddEditRepository extends JpaRepository<Category, Long>{
	Category save(Category category);
}
