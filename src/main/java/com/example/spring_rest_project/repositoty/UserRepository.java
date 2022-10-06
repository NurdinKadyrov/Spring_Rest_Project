package com.example.spring_rest_project.repositoty;

import com.example.spring_rest_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


   Optional<User> findByEmail(String email);


}
