package com.saniya.namedayofweek.Repositories;

import com.saniya.namedayofweek.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
