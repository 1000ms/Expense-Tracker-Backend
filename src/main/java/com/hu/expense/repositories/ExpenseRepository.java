package com.hu.expense.repositories;

import com.hu.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

    public List<Expense> findByUsernameOrderByDateDescTimeDesc(String username);
    Long countByUsername(String username);


    @Query("SELECT ROUND(sum(amount),2) from Expense WHERE username=:username")
    public Double sum(@Param("username") String username);

    @Query("SELECT DISTINCT(category) from Expense WHERE username=:username")
    public List<String> getCategories(@Param("username") String username);

    @Query("SELECT ROUND(sum(amount),2) from Expense WHERE username=:username AND category=:category")
    public Double totalByCategory(@Param("username") String username,@Param("category") String category);

    // For testing

    public Expense findByUsername(String username);

}
