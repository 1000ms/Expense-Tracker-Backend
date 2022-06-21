package com.hu.expense.ExpenseTest;


import com.hu.expense.entity.Expense;
import com.hu.expense.repositories.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddExpenseTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void testAddExpense(){
        Expense temp = new Expense();
        temp.setUsername("Test");
        temp.setDate(Date.valueOf("2022-06-06"));
        temp.setAmount(1000);
        temp.setCategory("Test");
        temp.setLocation("Bangalore");
        temp.setTime("11:11");
        temp.setName("Test");
        temp.setDescription("Test");
        expenseRepository.save(temp);
        assertEquals(temp, expenseRepository.findByUsername("Test"));
        expenseRepository.deleteById(expenseRepository.findByUsername("Test").getId());
    }
}
