package pq.jdev.b001.bookstore.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pq.jdev.b001.bookstore.users.model.Role;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByName(String name);
}
