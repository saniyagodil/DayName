package com.saniya.namedayofweek.Repositories;


import com.saniya.namedayofweek.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
