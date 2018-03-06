package com.saniya.namedayofweek.Security;

import com.saniya.namedayofweek.Repositories.RoleRepository;
import com.saniya.namedayofweek.Repositories.UserRepository;
import com.saniya.namedayofweek.Role;
import com.saniya.namedayofweek.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading Data... ");

        roleRepository.save(new Role("USER"));

        Role userRole = roleRepository.findByRoleName("USER");

        User newUser = new User("User1", "upassword");
        newUser.addRole(userRole);
        userRepository.save(newUser);

    }

}