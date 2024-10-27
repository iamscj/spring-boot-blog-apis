package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    User getUserByEmail(@Param("email") String email);

    User findByEmail(String email);

    User findByName(String name);

//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<User> findUserByEmail(@Param("email") String email);
}
