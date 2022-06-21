package com.hu.expense.service;


import com.hu.expense.dto.ExpenseByCategory;
import com.hu.expense.dto.stats;
import com.hu.expense.entity.Expense;
import com.hu.expense.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ResponseEntity<?> addExpense(Expense expense){
        Expense temp = expenseRepository.save(expense);
        return new ResponseEntity<>(temp,HttpStatus.OK);
    }

    public ResponseEntity<?> getExpenses(String username){
        return new ResponseEntity<>(expenseRepository.findByUsernameOrderByDateDescTimeDesc(username),HttpStatus.OK);
    }

    public ResponseEntity<?> deleteExpense(int id){
        expenseRepository.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    public ResponseEntity<?> getStats(String username){
        long total = expenseRepository.countByUsername(username);
        Double result = expenseRepository.sum(username);
        double sum = 0;
        if(result != null) sum = (double)result;
        return new ResponseEntity<>(new stats(total,sum),HttpStatus.OK);
    }

    public ResponseEntity<?> getExpenseByCategory(String username){
        List<String> categories = expenseRepository.getCategories(username);
        List<ExpenseByCategory> expenseByCategories = new ArrayList<>();
        for( String var: categories){
            expenseByCategories.add(new ExpenseByCategory(var,expenseRepository.totalByCategory(username,var)));
        }
        return new ResponseEntity<>(expenseByCategories,HttpStatus.OK);
    }

    public ResponseEntity<?> addAllExpenses(String username, List<Expense> expenses){
        for(Expense e: expenses){
            e.setUsername((String)username);
        }
        expenseRepository.saveAll(expenses);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
