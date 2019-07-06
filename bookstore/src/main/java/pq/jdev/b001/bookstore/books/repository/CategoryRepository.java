package pq.jdev.b001.bookstore.books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
