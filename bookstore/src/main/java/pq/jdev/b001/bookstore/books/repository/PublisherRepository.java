package pq.jdev.b001.bookstore.books.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Publisher;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long>, CrudRepository<Publisher, Long>{
}
