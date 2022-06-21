package com.hu.expense.repositories;

import com.hu.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {




    //For testing
    public User findByUsername(String username);
}
