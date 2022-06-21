package com.hu.expense.controller;

import com.hu.expense.dto.ExpenseByCategory;
import com.hu.expense.dto.stats;
import com.hu.expense.entity.AuthenticationResponse;
import com.hu.expense.entity.Expense;
import com.hu.expense.service.ExpenseService;
import com.hu.expense.util.GetUsernameFromJwt;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiOperation(value = "Expense Controller", tags = "Expense Services")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private GetUsernameFromJwt getUsernameFromJwt;

    @PostMapping("/add-expense")
    @ApiOperation(value = "Add Expense", response = Expense.class, authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> addExpense(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse, @RequestBody Expense expense){
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        expense.setUsername(username);
        return expenseService.addExpense(expense);
    }

    @GetMapping("/get-all-expenses")
    @ApiOperation(value = "Get All Expenses", response = Expense.class, authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getExpenses(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse){
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        return expenseService.getExpenses(username);
    }

    @DeleteMapping("/delete-expense/{id}")
    @ApiOperation(value = "Delete Expense", response = String.class, authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> deleteExpense(@PathVariable("id") int id){
        return expenseService.deleteExpense(id);
    }


    @GetMapping("/stats")
    @ApiOperation(value = "Statistics", response = stats.class, authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getStats(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse){
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        return expenseService.getStats(username);
    }

    @GetMapping("/get-expense-by-category")
    @ApiOperation(value = "Get Expenses by Category", response = ExpenseByCategory.class, authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getExpenseByCategory(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse){
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        return expenseService.getExpenseByCategory(username);
    }

    @PostMapping("/add-all-expenses")
    @ApiOperation(value = "Add all Expenses", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> addAllExpenses(@RequestHeader(value="Authorization") AuthenticationResponse authenticationResponse, @RequestBody List<Expense> expenses) {
        String username = getUsernameFromJwt.getUsername(authenticationResponse);
        return expenseService.addAllExpenses(username, expenses);
    }

}
