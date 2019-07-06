package pq.jdev.b001.bookstore.books.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.books.model.Publisher;


@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
